package com.randioo.chat_server.module.chat.service;

import org.apache.mina.core.session.IoSession;

import com.randioo.chat_server.protocol.Entity.ChatData;
import com.randioo.randioo_server_base.service.ObserveBaseServiceInterface;

public interface ChatService extends ObserveBaseServiceInterface {

	public void addRoom(IoSession session, String gameId);

	public void quitRoom(IoSession session);

	public void send(IoSession session, ChatData chatData);
}
