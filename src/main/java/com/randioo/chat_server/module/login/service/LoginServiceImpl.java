package com.randioo.chat_server.module.login.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.module.chat.service.ChatService;
import com.randioo.chat_server.module.close.service.CloseService;
import com.randioo.chat_server.module.login.LoginConstant;
import com.randioo.chat_server.protocol.Error.ErrorCode;
import com.randioo.chat_server.protocol.Login.LoginResponse;
import com.randioo.chat_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.annotation.BaseServiceAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.db.IdClassCreator;
import com.randioo.randioo_server_base.entity.RoleInterface;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.log.LoggerProxy;
import com.randioo.randioo_server_base.module.login.LoginHandler;
import com.randioo.randioo_server_base.module.login.LoginInfo;
import com.randioo.randioo_server_base.module.login.LoginModelService;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.template.Ref;
import com.randioo.randioo_server_base.utils.SessionUtils;
import com.randioo.randioo_server_base.utils.StringUtils;

@BaseServiceAnnotation(order = 0, value = "loginService")
@Service("loginService")
public class LoginServiceImpl extends ObserveBaseService implements LoginService {

    private static Logger roleRootLogger = LoggerFactory.getLogger(Role.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private LoginModelService loginModelService;

    @Autowired
    private IdClassCreator idClassCreator;

    @Autowired
    private CloseService closeService;

    @Override
    public void init() {
        idClassCreator.initId(Role.class, 0);
    }

    @Override
    public void initService() {

        // loginModelService.setLoginHandler(new LoginHandler() {
        //
        // @Override
        // public Facility saveFacility(Facility facility) {
        // return null;
        // }
        //
        // @Override
        // public void noticeOtherPlaceLogin(Facility oldFacility) {
        //
        // }
        //
        // @Override
        // public void loginRoleModuleDataInit(RoleInterface roleInterface) {
        //
        // }
        //
        // @Override
        // public RoleInterface getRoleInterfaceFromDBById(int roleId) {
        // return null;
        // }
        //
        // @Override
        // public RoleInterface getRoleInterfaceFromDBByAccount(String account)
        // {
        // return null;
        // }
        //
        // @Override
        // public Facility getFacilityFromDB(int roleId, String macAddress) {
        // return null;
        // }
        //
        // @Override
        // public boolean createRoleCheckAccount(LoginInfo info, Ref<Integer>
        // errorCode) {
        // return true;
        // }
        //
        // @Override
        // public RoleInterface createRole(LoginInfo loginInfo) {
        // Role role = new Role();
        // role.setRoleId(idClassCreator.getId(Role.class));
        // role.setAccount(loginInfo.getAccount());
        // role.setName(loginInfo.getAccount());
        // return role;
        // }
        // });

        loginModelService.setLoginHandler(new LoginHandler() {

            @Override
            public boolean createRoleCheckAccount(LoginInfo info, Ref<Integer> errorCode) {
                // 账号姓名不可为空
                if (StringUtils.isNullOrEmpty(info.getAccount())) {
                    errorCode.set(LoginConstant.CREATE_ROLE_NAME_SENSITIVE);
                    return false;
                }

                return true;
            }

            @Override
            public RoleInterface createRole(LoginInfo loginInfo) {
                Role role = new Role();
                role.setRoleId(idClassCreator.getId(Role.class));
                role.setAccount(loginInfo.getAccount());
                role.setName(loginInfo.getAccount());

                role.logger = LoggerProxy.proxyByName(roleRootLogger,
                        MessageFormat.format("[account:{0}]", role.getAccount()));

                return role;
            }

            @Override
            public RoleInterface getRoleInterfaceFromDBById(int roleId) {
                return null;
            }

            @Override
            public RoleInterface getRoleInterfaceFromDBByAccount(String account) {
                return null;
            }

            @Override
            public void loginRoleModuleDataInit(RoleInterface roleInterface) {
                // 将数据库中的数据放入缓存中
                Role role = (Role) roleInterface;
                role.logger = LoggerProxy.proxyByName(roleRootLogger,
                        MessageFormat.format("[account:{0}]", role.getAccount()));
            }

            @Override
            public void noticeOtherPlaceLogin(Object session) {

            }

            @Override
            public void closeCallback(Object session) {
                Role role = (Role) RoleCache.getRoleBySession(session);
                try {
                    closeService.asynManipulate(role);
                } catch (Exception e) {
                    if (role != null && role.logger != null) {
                        role.logger.error("sessionClosed error:", e);
                    }
                    logger.error("", e);
                }
            }
        });
    }

    @Override
    public void connect(Object session, LoginInfo loginInfo) {
        Ref<Integer> errorCode = new Ref<>();

        loginModelService.getRoleData(loginInfo, errorCode, session);

        SessionUtils.sc(session, SC.newBuilder().setLoginResponse(LoginResponse.newBuilder()).build());
    }

    @Override
    public void disconnected(Role role) {
        String account = role.getAccount();
        chatService.quitAllRooms(role);
        Lock lock = CacheLockUtil.getLock(String.class, role.getAccount());
        try {
            lock.lock();
            RoleCache.getRoleMap().remove(account);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            if (role != null) {
                if (role.logger != null) {
                    role.logger.error("{}", sw);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Role getRoleByAccount(String account) {
        return (Role) loginModelService.getRoleInterfaceByAccount(account);
    }

}
