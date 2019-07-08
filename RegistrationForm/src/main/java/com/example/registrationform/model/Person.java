package com.example.registrationform.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class Person {

	@Size(min = 1, max = 200)
	@NotNull
	private String name;
	@NotNull
	@Size(min = 1, max = 200)
	private String fname;
	@NotNull
	@Size(min = 1, max = 200)
	private String lname;
	@NotNull
	@Size(min = 6, max = 200)
	private String password;
	@NotNull
	@Size(min = 5, max = 200)
	@NotBlank
	@NotEmpty
	@Pattern(regexp=".+@.+\\.[A-Za-z]+")
	private String email;
	@NotNull
	@Size(min = 4, max = 15)
	@Pattern(regexp="[0-9]+")
	private String phoneNumber;
	@NotNull
	@Size(min = 4, max = 200)
	private String shippingAddress;
	private int isManager;

	public Person() {
		super();
	}

	public Person(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public Person(String name, String fname, String lname, String password, String email, String phoneNumber,
			String shippingAddress, int isManager) {
		super();
		this.name = name;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.shippingAddress = shippingAddress;
		this.isManager = isManager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getIsManager() {
		return isManager;
	}

	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}

}
