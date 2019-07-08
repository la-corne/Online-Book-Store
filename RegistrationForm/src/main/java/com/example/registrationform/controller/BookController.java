package com.example.registrationform.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.model.Book;
import com.example.registrationform.model.Person;

@Controller
public class BookController {
	@Autowired
	DatabaseInfo databaseInfo;

	/* this method is used to open bookInfo page */
	@RequestMapping(value = "/addnewbook")
	public ModelAndView openAddNewBookPage() {
		return new ModelAndView("bookInfo", "book", new Book());
	}

	/*
	 * this method is used when we click on add book button in bookInfo page to
	 * insert a new book in the database
	 */
	@RequestMapping(value = "/addbookbuttonisclicked", method = RequestMethod.POST)
	public ModelAndView clickAddNewBookButton(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
		/* insert book data into database */

		/* check if it is ok to insert */
		/*
		 * check on the existence of the publisher name and the errors note : if the
		 * insert is succeeded it returns false
		 */
		if (bindingResult.hasErrors() || !(databaseInfo.insertBookInToDatabase(book))) {/* cann't insert */
			return new ModelAndView("bookInfo", "invalidpublishername", "publisher name doesn't exist");
		}

		return new ModelAndView("redirect:/index");
	}

	/* this method is used to open book modifications page */
	@RequestMapping(value = "/bookmodifications")
	public ModelAndView openBookModificationsPage() {
		return new ModelAndView("bookModifications");
	}

	/* this function opens the modify page for a certain book */
	@RequestMapping(value = "/viewBookModificationPage/{isbn}")
	public ModelAndView settings(HttpSession session, @PathVariable int isbn) {
		Book book = databaseInfo.findBookByISBN(isbn);
		session.setAttribute("book", book);
		return new ModelAndView("redirect:/bookmodifications", "book", book);
	}

	/* this function is used to modify the books data */
	@RequestMapping(value = "/modifyBook", method = RequestMethod.POST)
	public ModelAndView openBookDataToModify(@ModelAttribute("book") Book newBook, HttpSession session) {
		Book oldBook = (Book) session.getAttribute("book");

		/* check if the new isbn is already exists */
		if (newBook.getIsbn() != oldBook.getIsbn()) {
			/* means that the manager changes the old isbn to a new one */
			Book temp = databaseInfo.findBookByISBN(newBook.getIsbn());
			if (temp.getCategory() != null) {
				/* this means that the manager should choose another isbn */
				return new ModelAndView("bookModifications", "invalidBookIsbn", "ISBN is already exists");

			}
		}

		if (databaseInfo.updateBookInfo(newBook, oldBook.getIsbn()) == -1) {
			/* can't insert the new publisher name doesn't exist */
			return new ModelAndView("bookModifications", "invalidpublishername", "publisher name doesn't exist");
		}
		return new ModelAndView("redirect:/index");
	}

//	public Book compare(Book newBook, Book oldBook) {
//		if (newBook.getIsbn() == 0)
//			newBook.setIsbn(oldBook.getIsbn());
//		if (newBook.getTitle() == "")
//			newBook.setTitle(oldBook.getTitle());
//		if (newBook.getPublicationYear() == 0)
//			newBook.setPublicationYear(oldBook.getPublicationYear());
//		if (newBook.getCategory() == "")
//			newBook.setCategory(oldBook.getCategory());
//		if (newBook.getSellingPrice() == 0)
//			newBook.setSellingPrice(oldBook.getSellingPrice());
//		if (newBook.getPublisherName() == "")
//			newBook.setPublisherName(oldBook.getPublisherName());
//		if (newBook.getQuantity() == 0)
//			newBook.setQuantity(oldBook.getQuantity());
//		return newBook;
//	}

}
