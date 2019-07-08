package com.example.registrationform.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.model.Person;

@Controller
public class RegisterationController {

	@Autowired
	DatabaseInfo databaseInfo;

	/* this method is used to open the registration page */
	@RequestMapping(value = "/viewregisrationpage", method = RequestMethod.GET)
	public ModelAndView register() {
		return new ModelAndView("register", "command", new Person());
	}

	/*
	 * this method is used to store the registered data in database and to redirect
	 * the user to the login page
	 */
	@RequestMapping(value = "/clickregistrationbutton", method = RequestMethod.POST)
	public ModelAndView submitRegistration(@Valid @ModelAttribute("p") Person p, BindingResult bindingResult) {

		/* check on the uniqueness of the user name and the errors */
		if (bindingResult.hasErrors() || (databaseInfo.isUserNameExists(p.getName()))) {
			return new ModelAndView("register", "invalidregistrationusername", "User name is already exists");
		}

		/* insert */
		boolean insert = databaseInfo.insertUserInToDatabase(p);
		System.out.println(insert);
		return new ModelAndView("redirect:/login");
	}

}
