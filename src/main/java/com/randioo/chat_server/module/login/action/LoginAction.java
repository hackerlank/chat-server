package com.randioo.chat_server.module.login.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.randioo.chat_server.module.login.service.LoginService;
import com.randioo.chat_server.protocol.Login.LoginRequest;
import com.randioo.randioo_server_base.annotation.PTAnnotation;
import com.randioo.randioo_server_base.module.login.LoginInfo;
import com.randioo.randioo_server_base.template.IActionSupport;

@Controller
@PTAnnotation(LoginRequest.class)
public class LoginAction implements IActionSupport {

    @Autowired
    private LoginService loginService;

    @Override
    public void execute(Object data, Object session) {
        LoginRequest request = (LoginRequest) data;

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAccount(request.getAccount());
        loginInfo.setMacAddress(request.getMacAddress());

        loginService.connect(session, loginInfo);
    }

}
