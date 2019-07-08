package com.example.registrationform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.registrationform.DatabaseInfo;
import com.example.registrationform.model.Order;

@Controller
public class OrdersController {

	@Autowired
	DatabaseInfo databaseInfo;

	/* this method is used to open orders page for managers */
	@RequestMapping(value = "/orders")
	public ModelAndView openOrdersPage() {
		List<Order> orders = databaseInfo.selectOrders();

		return new ModelAndView("orders", "orderslist", orders);
	}

	@RequestMapping(value = "/deleteorder/{orderId}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int orderId) {
		databaseInfo.deleteOrder(orderId);
		return new ModelAndView("redirect:/orders");
	}
}
