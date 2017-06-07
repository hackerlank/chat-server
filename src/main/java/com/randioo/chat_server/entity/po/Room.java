package com.randioo.chat_server.entity.po;

import java.util.HashSet;
import java.util.Set;

public class Room {
	private String roomId;
	private Set<String> accountSet = new HashSet<>();

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setAccountSet(Set<String> accountSet) {
		this.accountSet = accountSet;
	}

	public Set<String> getAccountSet() {
		return accountSet;
	}
}
