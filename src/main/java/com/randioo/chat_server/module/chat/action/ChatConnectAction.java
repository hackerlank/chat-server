package com.randioo.chat_server.module.chat.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.randioo.chat_server.module.chat.service.ChatService;
import com.randioo.randioo_server_base.template.IActionSupport;

public class ChatConnectAction implements IActionSupport{

	@Autowired
	private ChatService chatService;
	@Override
	public void execute(Object data, IoSession session) {
		// TODO Auto-generated method stub
		
	}

}
