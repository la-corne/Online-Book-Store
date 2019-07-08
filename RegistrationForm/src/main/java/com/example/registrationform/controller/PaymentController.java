package com.example.registrationform.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.model.Book;
import com.example.registrationform.model.BooksSold;
import com.example.registrationform.model.Person;

//belal

@Controller
public class PaymentController {
	@Autowired
	DatabaseInfo databaseInfo;

	@RequestMapping(value = "/addBook/{isbn}")
	public ModelAndView add(@PathVariable int isbn, @ModelAttribute("b") BooksSold b, HttpSession session) {
		int quan = b.getQuantity();
		Person user = (Person) session.getAttribute("p");
		String username = user.getName();
		databaseInfo.addToCart(isbn, quan, username);
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/getCard")
	public ModelAndView viewCards(HttpSession session) {

		Person user = (Person) session.getAttribute("p");
		String username = user.getName();
		List<BooksSold> list = databaseInfo.view(username);
		int total = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Book> bookList = databaseInfo.vieweElementFromBook(list.get(i).getISBN());
			total += list.get(i).getQuantity() * bookList.get(0).getSellingPrice();
			list.get(i).setPrice(bookList.get(0).getSellingPrice());
			list.get(i).setTitle(bookList.get(0).getTitle());
		}
		ModelAndView model = new ModelAndView("viewCard");
		model.addObject("list", list);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/deleteFromCard/{isbn}")
	public ModelAndView deleteElement(@PathVariable int isbn, HttpSession session) {

		Person user = (Person) session.getAttribute("p");
		String username = user.getName();
		databaseInfo.deleteCard(isbn, username);
		List<BooksSold> list = databaseInfo.view(username);
		ModelAndView model = new ModelAndView("viewCard");
		int total = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Book> bookList = databaseInfo.vieweElementFromBook(list.get(i).getISBN());
			total += list.get(i).getQuantity() * bookList.get(0).getSellingPrice();
			list.get(i).setPrice(bookList.get(0).getSellingPrice());
			list.get(i).setTitle(bookList.get(0).getTitle());

		}
		model.addObject("list", list);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/next")
	public ModelAndView next() {
		return new ModelAndView("visa");
	}

	@RequestMapping(value = "/buybuy")
	public ModelAndView buy(HttpSession session) {
		try {

			Person user = (Person) session.getAttribute("p");
			String username = user.getName();
			databaseInfo.buy(username);
		} catch (Error e) {
			if (e.getCause() == null) {
				return new ModelAndView("visa", "negativeStock", "There is no enough books in the stock");
			}
			return new ModelAndView("visa", "negativeStock", "please fill the text fields");
		}
		return new ModelAndView("index");
	}

}
