package com.randioo.chat_server.module.chat.service;

import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.randioo.randioo_server_base.service.BaseServiceInterface;

public interface ChatService extends BaseServiceInterface {

	public void connect(List<String> keyList, IoSession session);
}
