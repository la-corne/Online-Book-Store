package com.example.registrationform.listener;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.stereotype.Component;

import com.example.registrationform.model.ActiveUserStore;
import com.example.registrationform.model.Person;

@Component
public class LoggedUser implements HttpSessionBindingListener {

	private ActiveUserStore activeUserStore;
	private String userName;

	public LoggedUser(String userName) {
		this.activeUserStore = ActiveUserStore.getInstance();
		this.userName = userName;
	}
	
	

	public LoggedUser() {
		super();
		this.activeUserStore = ActiveUserStore.getInstance();
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ActiveUserStore getActiveUserStore() {
		return activeUserStore;
	}

	public void setActiveUserStore(ActiveUserStore activeUserStore) {
		this.activeUserStore = activeUserStore;
	}

	/*
	 * this method is called when the user logs in to add it in list users (this
	 * list contains the users who are logged in the system)
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		LoggedUser user = (LoggedUser) event.getValue();
		if (!activeUserStore.isContainUser(user.getUserName())) {
			activeUserStore.addUser(user.getUserName());
		}

		for (int i = 0; i < activeUserStore.getUsers().size(); i++) {
			System.out.println("we logged in with ");
			System.out.println(activeUserStore.getUsers().get(i));
		}
	}

	/*
	 * this method is called when the user logs out to remove that user form the
	 * users list
	 */
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		LoggedUser user = (LoggedUser) event.getValue();
		if (activeUserStore.isContainUser(user.getUserName())) {
			activeUserStore.removeUser(user.getUserName());
		}

		for (int i = 0; i < activeUserStore.getUsers().size(); i++) {
			System.out.println("we logged out this are the remains ");
			System.out.println(activeUserStore.getUsers().get(i));
		}
	}

}
