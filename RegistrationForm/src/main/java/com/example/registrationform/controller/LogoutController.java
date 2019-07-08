package com.example.registrationform.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.listener.LoggedUser;
import com.example.registrationform.model.ActiveUserStore;
import com.example.registrationform.model.Person;

@Controller
public class LogoutController {
	@Autowired
	DatabaseInfo databaseInfo;

	/*
	 * logout button is clicked then clear the session and redirect the user to
	 * login home page
	 */

	@RequestMapping(value = "/clicklogoutbutton", method = RequestMethod.POST)
	public String clickLogout(Model model, HttpServletRequest request, HttpServletResponse response,
			@CookieValue(value = "loggedUser", defaultValue = "") String username) {

		/* remove the user cart data */
		databaseInfo.logOutDelete(username);
		/* remove the user from the session and from the logged users list */
		HttpSession session = request.getSession();
		session.removeAttribute(username);
		session.invalidate();

		/*
		 * then we need it to remove the cached pages so if we click the back button it
		 * won't enter the previous page
		 */

//		response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
//		response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
//		response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
//		response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

		/* then remove it from the cookie */

		return "homeBeforLogging";
	}

}
