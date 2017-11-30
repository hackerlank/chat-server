package com.randioo.chat_server.entity.bo;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import com.randioo.randioo_server_base.entity.DefaultRole;

public class Role extends DefaultRole {
    public Logger logger;
    private Set<String> roomIdSet = new HashSet<>();

    public Set<String> getRoomIdSet() {
        return roomIdSet;
    }
}
