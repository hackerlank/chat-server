package com.randioo.chat_server.module.chat.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.protobuf.GeneratedMessage;
import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.module.chat.service.ChatService;
import com.randioo.chat_server.protocol.Chat.ChatQuitRoomRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;
import com.randioo.randioo_server_base.utils.SessionUtils;

@Controller
@PTAnnotation(ChatQuitRoomRequest.class)
public class ChatQuitRoomAction implements IActionSupport {

	@Autowired
	private ChatService chatService;

	@Override
	public void execute(Object data, IoSession session) {
		ChatQuitRoomRequest request = (ChatQuitRoomRequest) data;
		Role role = (Role) RoleCache.getRoleBySession(session);
		GeneratedMessage message = chatService.quitRoom(role, request.getRoomId());
		SessionUtils.sc(session, message);
	}
}
