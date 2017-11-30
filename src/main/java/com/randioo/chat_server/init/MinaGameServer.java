package com.randioo.chat_server.init;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.randioo.randioo_server_base.GlobleConstant;
import com.randioo.randioo_server_base.config.GlobleMap;
import com.randioo.randioo_server_base.init.GameServer;
import com.randioo.randioo_server_base.scheduler.SchedulerManager;
import com.randioo.randioo_server_base.service.ServiceManager;

public class MinaGameServer implements GameServer {
    private final static Logger logger = LoggerFactory.getLogger(MinaGameServer.class);

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private SchedulerManager schedulerManager;

    @Autowired
    private IoAcceptor ioAcceptor;

    public void start() {
        serviceManager.initServices();
        logger.info("init Services");

        schedulerManager.start();
        logger.info("scheduler start");

        InetSocketAddress inetSocketAddress = new InetSocketAddress(GlobleMap.Int(GlobleConstant.ARGS_PORT));
        try {
            ioAcceptor.bind(inetSocketAddress);
            logger.info("WANSERVER : START SERVER SUCCESS -> socket port:{}", inetSocketAddress.getPort());
        } catch (IOException e) {
            logger.error("", e);
        }
        logger.info("socket start");

        GlobleMap.putParam(GlobleConstant.ARGS_LOGIN, true);
    }

}
