package com.randioo.chat_server.module.close.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randioo.chat_server.entity.bo.Role;
import com.randioo.chat_server.module.login.service.LoginService;
import com.randioo.randioo_server_base.annotation.BaseServiceAnnotation;
import com.randioo.randioo_server_base.service.ObserveBaseService;

@BaseServiceAnnotation("closeService")
@Service("closeService")
public class CloseServiceImpl extends ObserveBaseService implements CloseService {

    @Autowired
    private LoginService loginService;

    @Override
    public void asynManipulate(Role role) {
        role.logger.info("manipulate");
        loginService.disconnected(role);
    }

}
