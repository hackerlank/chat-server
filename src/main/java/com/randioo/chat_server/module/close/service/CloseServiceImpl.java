package com.randioo.chat_server.module.close.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.module.login.service.LoginService;
import com.randioo.randioo_server_base.db.GameDB;
import com.randioo.randioo_server_base.service.BaseService;

@Service("closeService")
public class CloseServiceImpl extends BaseService implements CloseService {

	@Autowired
	private GameDB gameDB;

	@Autowired
	private LoginService loginService;

	@Override
	public void asynManipulate(Role role) {
		logger.info("[account:" + role.getAccount() + "] manipulate");
		loginService.disconnected(role);
	}

}
