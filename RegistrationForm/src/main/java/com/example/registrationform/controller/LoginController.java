package com.example.registrationform.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.listener.LoggedUser;
import com.example.registrationform.model.ActiveUserStore;
import com.example.registrationform.model.Person;

@Controller
public class LoginController {
	@Autowired
	DatabaseInfo databaseInfo;

	/* this method is used to open login page */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView openLoginPage() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("loggedUser", new LoggedUser());
		model.addObject("command", new Person());
		return model;
	}

	/*
	 * this method is used when the user clicks on the login button that is in the
	 * login page to enter to his home page
	 */
	@RequestMapping(value = "/loginbuttonisclicked", method = RequestMethod.POST)
	public ModelAndView clickLoginButton(@Valid @ModelAttribute("p") Person p, BindingResult bindingResult,
			HttpSession session, HttpServletResponse response) {

		/* user name and email doesn't exist */
		if (!databaseInfo.isUserNameAndPasswordExists(p.getName(), p.getPassword())) {
			return new ModelAndView("login", "invalidusernameandpassword", "Invalid Username / Password");
		}

		/*
		 * successful login so add the user in the session (server side) and in the
		 * ActiveUserStore list
		 */

		LoggedUser loggedUser = new LoggedUser(p.getName());
		session.setAttribute(loggedUser.getUserName(), loggedUser);

		/* add the value also to the cookie to be stored in the client side */
		Cookie cookie = new Cookie("loggedUser", loggedUser.getUserName());
		response.addCookie(cookie);

		/* add the logged person to the global session */
		Person person = databaseInfo.findUserByName(p.getName());
		session.setAttribute("p", person);

		ModelAndView model = new ModelAndView("redirect:/index");
		model.addObject("p", person);
		model.addObject("username", p.getName());
		model.addObject("loggedUser", loggedUser);
		return model;
	}

}
