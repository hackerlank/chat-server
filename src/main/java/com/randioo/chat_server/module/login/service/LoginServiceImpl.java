package com.randioo.chat_server.module.login.service;

import java.util.concurrent.locks.Lock;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.module.chat.service.ChatService;
import com.randioo.chat_server.protocol.Error.ErrorCode;
import com.randioo.chat_server.protocol.Login.LoginResponse;
import com.randioo.chat_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.cache.SessionCache;
import com.randioo.randioo_server_base.entity.RoleInterface;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.module.login.LoginModelConstant;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("loginService")
public class LoginServiceImpl extends ObserveBaseService implements LoginService {

	@Autowired
	private ChatService chatService;

	@Override
	public void connect(IoSession ioSession, String account) {
		Lock lock = CacheLockUtil.getLock(String.class, account);

		try {
			lock.lock();
			// 获得玩家对象
			RoleInterface roleInterface = RoleCache.getRoleByAccount(account);
			if (roleInterface == null) {
				roleInterface = this.createRoleInterface(account);
			}

			IoSession oldSession = SessionCache.getSessionByAccount(account);

			if (oldSession != null) {
				oldSession.setAttribute(LoginModelConstant.ROLE_ID, null);
				oldSession.close(true);
			}

			// session绑定ID
			ioSession.setAttribute(LoginModelConstant.ROLE_ID, account);
			// session放入缓存
			SessionCache.addSession(account, ioSession);

			// 将数据库中的数据放入缓存中
			RoleCache.putRoleCache(roleInterface);
			ioSession.write(SC.newBuilder().setLoginResponse(LoginResponse.newBuilder()).build());
		} catch (Exception e) {
			logger.error("login error ", e);
			ioSession.write(SC.newBuilder()
					.setLoginResponse(LoginResponse.newBuilder().setErrorCode(ErrorCode.CONNECT_ERROR.getNumber()))
					.build());
		} finally {
			lock.unlock();
		}

	}

	private RoleInterface createRoleInterface(String account) {
		Role role = new Role();
		role.setAccount(account);
		return role;
	}

	@Override
	public void disconnected(Role role) {
		String account = role.getAccount();
		chatService.quitAllRooms(role);
		Lock lock = CacheLockUtil.getLock(String.class, role.getAccount());
		try {
			lock.lock();
			RoleCache.getRoleMap().remove(account);
			SessionCache.removeSessionById(account);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}
