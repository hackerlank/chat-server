package com.randioo.chat_server.module.chat.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.module.chat.service.ChatService;
import com.randioo.chat_server.protocol.Chat.ChatSendRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.cache.RoleCache;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(ChatSendRequest.class)
public class ChatSendAction implements IActionSupport {

    @Autowired
    private ChatService chatService;

    @Override
    public void execute(Object data, Object session) {
        ChatSendRequest request = (ChatSendRequest) data;
        Role role = (Role) RoleCache.getRoleBySession(session);
        chatService.send(session, role, request.getRoomId(), request.getChatData());
    }
}
