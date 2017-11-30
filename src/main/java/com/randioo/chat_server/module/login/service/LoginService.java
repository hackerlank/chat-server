package com.randioo.chat_server.module.login.service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.randioo_server_base.module.login.LoginInfo;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface LoginService extends ObserveBaseServiceInterface {
    void connect(Object session, LoginInfo loginInfo);

    void disconnected(Role role);

    Role getRoleByAccount(String account);
}
