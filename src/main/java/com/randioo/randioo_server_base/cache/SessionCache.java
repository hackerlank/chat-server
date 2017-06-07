package com.randioo.randioo_server_base.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

public class SessionCache {
	private static ConcurrentMap<String, IoSession> sessionMap = new ConcurrentHashMap<>();

	public static IoSession getSessionByAccount(String id) {
		return sessionMap.get(id);
	}

	public static void addSession(String account, IoSession session) {
		sessionMap.put(account, session);
	}

	/**
	 * 获取所有session
	 * 
	 * @return
	 */
	public static Collection<IoSession> getAllSession() {
		return sessionMap.values();
	}

	public static void removeSessionById(String account) {
		sessionMap.remove(account);
	}
}
