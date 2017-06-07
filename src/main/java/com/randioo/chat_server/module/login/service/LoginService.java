package com.randioo.chat_server.module.login.service;

import org.apache.mina.core.session.IoSession;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface LoginService extends ObserveBaseServiceInterface {
	void connect(IoSession session, String account);

	void disconnected(Role role);
}
