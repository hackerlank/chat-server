package com.randioo.chat_server.module.close.service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface CloseService extends ObserveBaseServiceInterface{
	public void asynManipulate(Role role);
}
