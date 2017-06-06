package com.randioo.chat_server.module.login.service;

import java.util.concurrent.locks.Lock;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.cache.local.SessionCache;
import com.randioo.chat_server.protocol.Error.ErrorCode;
import com.randioo.chat_server.protocol.Login.LoginResponse;
import com.randioo.chat_server.protocol.ServerMessage.SC;
import com.randioo.randioo_server_base.lock.CacheLockUtil;
import com.randioo.randioo_server_base.service.ObserveBaseService;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Service("loginService")
public class LoginServiceImpl extends ObserveBaseService implements LoginService {

	@Override
	public void login(IoSession ioSession, String account) {
		Lock lock = CacheLockUtil.getLock(String.class, account);
		try {
			lock.lock();

			IoSession oldSession = SessionCache.getSessionByAccount(account);
			if (oldSession != null) {
				oldSession.setAttribute("account", null);
				oldSession.close(true);
			}

			// session绑定ID
			ioSession.setAttribute("account", account);
			// session放入缓存
			SessionCache.addSession(account, ioSession);

			SessionUtils.sc(account, SC.newBuilder().setLoginResponse(LoginResponse.newBuilder().build()));
			return;
		} catch (Exception e) {
			logger.error("login", e);
		} finally {
			lock.unlock();
		}
		SessionUtils.sc(account, SC.newBuilder().setLoginResponse(
				LoginResponse.newBuilder().setErrorCode(ErrorCode.CONNECT_ERROR.getNumber()).build()));

	}

}
