package com.example.registrationform.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {

	@NotNull
	private int isbn;
	@Size(min = 1, max = 200)
	@NotNull
	private String title;
	private int publicationYear;
	@Size(min = 1, max = 200)
	@NotNull
	private String category;
	@NotNull
	private double sellingPrice;
	@NotNull
	public String publisherName;
	@NotNull
	public int quantity;

	public Book() {
		super();
	}

	public Book(int isbn, String title, int publicationYear, String category, double sellingPrice,
			String publisherName, int quantity) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.publicationYear = publicationYear;
		this.category = category;
		this.sellingPrice = sellingPrice;
		this.publisherName = publisherName;
		this.quantity = quantity;
	}

	public Book(String title, int publicationYear, String category, double sellingPrice, String publisherName,
			int quantity) {
		super();
		this.title = title;
		this.publicationYear = publicationYear;
		this.category = category;
		this.sellingPrice = sellingPrice;
		this.publisherName = publisherName;
		this.quantity = quantity;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
