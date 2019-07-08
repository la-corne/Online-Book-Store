package com.example.registrationform.model;
//belal
import java.sql.Date;
import java.sql.Time;

public class BooksSold {

	private String UName;
	private int ISBN;
	private Date sellingDate;
	private Time sellingTime;
	private double price;
	private int quantity;
	private String title;

	public BooksSold() {
		super();
	}

	public BooksSold(String uname, int iSBN, Date sellingDate, Time sellingTime, double price, int quantity,
			String title) {
		super();
		UName = uname;
		ISBN = iSBN;
		this.sellingDate = sellingDate;
		this.sellingTime = sellingTime;
		this.price = price;
		this.quantity = quantity;
		this.title = title;
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String uname) {
		UName = uname;
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int i) {
		ISBN = i;
	}

	public Date getSellingDate() {
		return sellingDate;
	}

	public void setSellingDate(Date date) {
		this.sellingDate = date;
	}

	public Time getSellingTime() {
		return sellingTime;
	}

	public void setSellingTime(Time time) {
		this.sellingTime = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
