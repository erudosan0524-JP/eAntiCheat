package com.github.jp.erudo.eanticheat.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UserManager {

	private List<User> users;

	public UserManager() {
		//リストの同期化
		users = Collections.synchronizedList(new ArrayList<>());
	}

	public User getUser(UUID uuid) {
		for(User user : users) {
			if(user.getPlayer().getUniqueId() == uuid || user.getPlayer().getUniqueId().equals(uuid)) {
				return user;
			}
		}
		return null;
	}

	public void addUser(User user) {
		if(!users.contains(user)) {
			users.add(user);
		}
	}

	public void removeUser(User user) {
		if(users.contains(user)) {
			users.remove(user);
		}
	}

}
