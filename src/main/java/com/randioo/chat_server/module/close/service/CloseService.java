package com.randioo.chat_server.module.close.service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.randioo_server_base.service.BaseServiceInterface;

public interface CloseService extends BaseServiceInterface {
	public void asynManipulate(Role role);

	public void roleDataCache2DB(Role role, boolean mustSave);
}