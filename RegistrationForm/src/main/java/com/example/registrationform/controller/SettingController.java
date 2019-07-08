package com.example.registrationform.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.HashedPassword;
import com.example.registrationform.model.Person;

@Controller
public class SettingController {
	@Autowired
	DatabaseInfo databaseInfo;
	private HashedPassword hashedPassword = new HashedPassword();

	@RequestMapping(value = "/viewUserSettingsPage")
	public ModelAndView settings(HttpSession session) {
		return new ModelAndView("userSettings", "p", (Person) session.getAttribute("p"));
	}

	@RequestMapping(value = "/savebuttonisclicked", method = RequestMethod.POST)
	public ModelAndView clickSaveButton(@ModelAttribute("p") Person newPerson, HttpSession session) {
		Person oldPerson = (Person) session.getAttribute("p");
		newPerson = compare(newPerson, oldPerson);
		databaseInfo.updateUserInfo(newPerson, oldPerson.getName());
		return new ModelAndView("redirect:/index");
	}
	

	public Person compare(Person newPerson, Person oldPerson) {
		if (newPerson.getName() == "")
			newPerson.setName(oldPerson.getName());
		if (newPerson.getFname() == "")
			newPerson.setFname(oldPerson.getFname());
		if (newPerson.getLname() == "")
			newPerson.setLname(oldPerson.getLname());
		if (newPerson.getPassword() == "")
			newPerson.setPassword(oldPerson.getPassword());
		else {
			/* the user changed his password so encrypt it before updating the database */
			hashedPassword.setPassword(newPerson.getPassword());
			newPerson.setPassword(hashedPassword.getPassword());
		}
		if (newPerson.getEmail() == "")
			newPerson.setEmail(oldPerson.getEmail());
		if (newPerson.getPhoneNumber() == "")
			newPerson.setPhoneNumber(oldPerson.getPhoneNumber());
		if (newPerson.getShippingAddress() == "")
			newPerson.setShippingAddress(oldPerson.getShippingAddress());
		if (newPerson.getIsManager() == 0)
			newPerson.setIsManager(oldPerson.getIsManager());

		return newPerson;
	}

}
