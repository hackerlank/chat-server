package com.randioo.randioo_server_base.utils;

import org.apache.mina.core.session.IoSession;

import com.randioo.chat_server.cache.local.SessionCache;

public class SessionUtils {
	public static void sc(IoSession session, Object message) {
		if (session != null) {
			session.write(message);
		}
	}

	public static void sc(String account, Object message) {
		IoSession session = SessionCache.getSessionByAccount(account);
		sc(session, message);
	}
}
