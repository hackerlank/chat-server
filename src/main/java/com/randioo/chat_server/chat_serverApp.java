package com.randioo.chat_server;

import com.randioo.randioo_server_base.config.GlobleArgsLoader;
import com.randioo.randioo_server_base.config.GlobleXmlLoader;
import com.randioo.randioo_server_base.init.GameServer;
import com.randioo.randioo_server_base.log.LogSystem;
import com.randioo.randioo_server_base.utils.SpringContext;

/**
 * Hello world!
 *
 */
public class chat_serverApp {
    public static void main(String[] args) {
        GlobleXmlLoader.init("./server.xml");
        GlobleArgsLoader.init(args);

        LogSystem.init(chat_serverApp.class);

        SpringContext.initSpringCtx("classpath:ApplicationContext.xml");

        ((GameServer) SpringContext.getBean(GameServer.class)).start();
    }

}
