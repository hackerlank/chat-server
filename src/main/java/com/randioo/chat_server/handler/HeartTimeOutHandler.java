package com.randioo.chat_server.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.springframework.stereotype.Component;

@Component
public class HeartTimeOutHandler implements KeepAliveRequestTimeoutHandler {

    @Override
    public void keepAliveRequestTimedOut(KeepAliveFilter arg0, IoSession arg1) throws Exception {

    }

}
