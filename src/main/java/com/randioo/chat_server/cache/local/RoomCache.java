package com.randioo.chat_server.cache.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.randioo.chat_server.entity.po.Room;

public class RoomCache {
	private static Map<String, Room> roomMap = new ConcurrentHashMap<>();

	public static Map<String, Room> getRoomMap() {
		return roomMap;
	}
}
