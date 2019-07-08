package com.example.registrationform.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.model.ActiveUserStore;
import com.example.registrationform.model.Person;

/**
 * Handles requests for the application home page.
 */
//@SessionAttributes("name")
@Controller
public class HomeController {
	@Autowired
	DatabaseInfo databaseInfo;
	@Autowired
	ActiveUserStore activeUserStore;

	/* this the is launcher page */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView personForm() {
		return new ModelAndView("homeBeforLogging");
	}

	/* this method is used to open shop page */
	@RequestMapping(value = "/shop")
	public ModelAndView openShopPage() {
		return new ModelAndView("shop");
	}

	/* this method is used to open about page */
	@RequestMapping(value = "/about")
	public ModelAndView openAboutPage() {
		return new ModelAndView("about");
	}

	/* this method is used to open faq page */
	@RequestMapping(value = "/faq")
	public ModelAndView openFAQPage() {
		return new ModelAndView("faq");
	}

	/* this method is used to open index page (home page) */
	@RequestMapping(value = "/index")
	public ModelAndView openIndexPage() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/homeBeforeLogging")
	public ModelAndView openHomeBeforeLoggingPage() {
		return new ModelAndView("homeBeforLogging");
	}

	/*
	 * this method is used when we click on the add book button in the home page
	 */
	@RequestMapping(value = "/clickAddBookbutton")
	public ModelAndView clickAddBook() {
		return new ModelAndView("redirect:/addnewbook");
	}

	/* this method is used to open products page */
	@RequestMapping(value = "/products")
	public ModelAndView openProductsPage() {
		return new ModelAndView("products");
	}

}
