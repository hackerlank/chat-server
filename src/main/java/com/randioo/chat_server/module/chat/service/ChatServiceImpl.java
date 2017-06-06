package com.randioo.chat_server.module.chat.service;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.protocol.Entity.ChatData;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@Service("chatService")
public class ChatServiceImpl extends ObserveBaseService implements ChatService {

	@Override
	public void addRoom(IoSession session, String gameId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void quitRoom(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(IoSession session, ChatData chatData) {
		// TODO Auto-generated method stub

	}

}
