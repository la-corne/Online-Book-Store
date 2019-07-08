package com.example.registrationform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.model.Book;

@Controller
public class SearchController {
	private Book book;

	@Autowired
	DatabaseInfo databaseInfo;

	/* this method is used to open products page */
	@RequestMapping(value = "/productSingle")
	public ModelAndView openproductSinglePage() {
		return new ModelAndView("productSingle", "book", book);
	}

	/*
	 * this method is used on searching for a book
	 */
	@RequestMapping(value = "/searchForBook", method = RequestMethod.POST)
	public ModelAndView searchForBook(@RequestParam("userInput") String userInput) {
		/* if the userInput is empty or null */

		List<Book> searchResult = databaseInfo.findSearchResults(userInput);

		ModelAndView modelAndView = new ModelAndView("products");
		modelAndView.addObject("userInput", userInput);
		modelAndView.addObject("searchResults", searchResult);
		return modelAndView;
	}

	/*
	 * this method is called when the user choose a book to view from his search
	 * results
	 */
	@RequestMapping(value = "/viewProductData/{isbn}")
	public ModelAndView openProductSingle(@PathVariable int isbn) {
		Book book = databaseInfo.findBookByISBN(isbn);
		this.book = book;
		ModelAndView modelAndView = new ModelAndView("redirect:/productSingle");
		modelAndView.addObject("book", book);
		return modelAndView;
	}
}
