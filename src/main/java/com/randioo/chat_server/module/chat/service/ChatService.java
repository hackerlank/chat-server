package com.randioo.chat_server.module.chat.service;

import com.google.protobuf.GeneratedMessage;
import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.protocol.Entity.ChatData;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface ChatService extends ObserveBaseServiceInterface {

    public GeneratedMessage addRoom(Role role, String roomId);

    public void quitAllRooms(Role role);

    GeneratedMessage quitRoom(Role role, String roomId);

    void send(Object session, Role role, String roomId, ChatData chatData);
}
