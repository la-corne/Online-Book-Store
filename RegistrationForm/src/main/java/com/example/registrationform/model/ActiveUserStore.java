package com.example.registrationform.model;

import java.util.ArrayList;
import java.util.List;

public class ActiveUserStore {
	private List<String> users;
	private static ActiveUserStore instance;

	private ActiveUserStore() {
		this.users = new ArrayList<String>();
	}

	public static ActiveUserStore getInstance() {
		if (instance == null) {
			instance = new ActiveUserStore();
		}
		return instance;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public void addUser(String userName) {
		this.users.add(userName);
	}

	public void removeUser(String userName) {
		this.users.remove(userName);
	}

	public Boolean isContainUser(String userName) {
		return this.users.contains(userName);
	}

}
