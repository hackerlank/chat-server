package com.randioo.randioo_server_base.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

import com.randioo.randioo_server_base.entity.RoleInterface;
import com.randioo.randioo_server_base.module.login.LoginModelConstant;

public class RoleCache {
	/** 所有玩家缓存信息 */
	private static ConcurrentMap<String, RoleInterface> roleAccountMap = new ConcurrentHashMap<>();

	public static RoleInterface getRoleBySession(IoSession ioSession) {
		try {
			String account = (String) ioSession.getAttribute(LoginModelConstant.ROLE_ID);
			if (account == null)
				return null;
			RoleInterface role = roleAccountMap.get(account);
			if (role == null)
				return null;
			return role;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更具账号获取角色缓存
	 * 
	 * @param name
	 * @return
	 */
	public static RoleInterface getRoleByAccount(String account) {
		return roleAccountMap.get(account);
	}

	public static synchronized void putNewRole(RoleInterface role) {
		roleAccountMap.put(role.getAccount(), role);
	}

	public static void putRoleCache(RoleInterface role) {
		if (!roleAccountMap.containsKey(role.getAccount())) {
			roleAccountMap.put(role.getAccount(), role);
		}
	}

	public static ConcurrentMap<String, RoleInterface> getRoleMap() {
		return roleAccountMap;
	}

}
