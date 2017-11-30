package com.randioo.chat_server.entity.po;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.randioo.chat_server.protocol.ClientMessage.CS;
import com.randioo.chat_server.protocol.Heart.CSHeart;
import com.randioo.chat_server.protocol.Heart.HeartRequest;
import com.randioo.chat_server.protocol.Heart.HeartResponse;
import com.randioo.chat_server.protocol.Heart.SCHeart;
import com.randioo.chat_server.protocol.ServerMessage.SC;

public class ProtoHeartFactory implements KeepAliveMessageFactory {
    private CS heartRequest = CS.newBuilder().setHeartRequest(HeartRequest.newBuilder()).build();
    private CS csHeart = CS.newBuilder().setCSHeart(CSHeart.newBuilder()).build();
    private SC heartResponse = SC.newBuilder().setHeartResponse(HeartResponse.newBuilder()).build();
    private SC scHeart = SC.newBuilder().setSCHeart(SCHeart.newBuilder()).build();

    @Override
    public Object getRequest(IoSession arg0) {
        return scHeart;
    }

    @Override
    public Object getResponse(IoSession arg0, Object arg1) {
        return heartResponse;
    }

    @Override
    public boolean isRequest(IoSession arg0, Object arg1) {
        boolean isRequest = heartRequest.equals(arg1);
        return isRequest;
    }

    @Override
    public boolean isResponse(IoSession arg0, Object arg1) {
        boolean isResponse = csHeart.equals(arg1);
        return isResponse;
    }
}