package com.example.registrationform.model;

public class Order {
	private int orderId;
	private int isbn;
	private int quantity;
	private String publisherName;

	public Order() {
		super();

	}

	public Order(int orderId, int isbn, int quantity, String publisherName) {
		super();
		this.orderId = orderId;
		this.isbn = isbn;
		this.quantity = quantity;
		this.publisherName = publisherName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

}
