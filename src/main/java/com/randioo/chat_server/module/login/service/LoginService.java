package com.randioo.chat_server.module.login.service;

import org.apache.mina.core.session.IoSession;

import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface LoginService extends ObserveBaseServiceInterface {
	public void login(IoSession session, String account);
}
