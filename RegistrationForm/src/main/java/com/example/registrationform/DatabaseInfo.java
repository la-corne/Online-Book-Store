package com.example.registrationform;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.example.registrationform.model.Book;
import com.example.registrationform.model.BooksSold;
import com.example.registrationform.model.Order;
import com.example.registrationform.model.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatabaseInfo {
	private JdbcTemplate template;
	private HashedPassword hashedPassword = new HashedPassword();

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	/*
	 * insert a new user to the database after taking its information from the
	 * registration page
	 */
	public Boolean insertUserInToDatabase(final Person user) {
		String insertedData = "INSERT INTO USER (NAME,FNAME,LNAME,PASSWORD,EMAIL,PHONENUMBER,SHIPPINGADDRESS,ISMANAGER) VALUES (?,?,?,?,?,?,?,?)";
		hashedPassword.setPassword(user.getPassword());
		return template.execute(insertedData, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				// ps.setInt(1, user.getId());
				ps.setString(1, user.getName());
				ps.setString(2, user.getFname());
				ps.setString(3, user.getLname());
				ps.setString(4, hashedPassword.getPassword());
				ps.setString(5, user.getEmail());
				ps.setString(6, user.getPhoneNumber());
				ps.setString(7, user.getShippingAddress());
				ps.setInt(8, user.getIsManager());
				return ps.execute();
			}
		});
	}

	/*
	 * insert a new book in database after taking its information form the bookInfo
	 * page
	 */
	public Boolean insertBookInToDatabase(final Book book) {
		try {
			String insertedData = "INSERT INTO BOOK (ISBN,TITLE,PUBLICATIONYEAR,SELLINGPRICE,CATEGORY,PUBLISHER_NAME,QUANTITY) VALUES ('"
					+ book.getIsbn() + "','" + book.getTitle() + "','" + book.getPublicationYear() + "','"
					+ book.getSellingPrice() + "','" + book.getCategory() + "','" + book.getPublisherName() + "','"
					+ book.getQuantity() + "')";
			template.update(insertedData);
			return true;
		} catch (Error err) {/* the book isn't inserted because the publisher name doesn't exist */
			return false;
		}

	}

	/* this method is used to select a book based on its isbn */
	public Book findBookByISBN(int isbn) {
		Book b;
		try {
			String selectedBook = "SELECT * FROM BOOK USE INDEX (ISBN_UNIQUE) WHERE ISBN = '" + isbn + "'";
			return b = template.queryForObject(selectedBook, new RowMapper<Book>() {
				@Override
				public Book mapRow(ResultSet rs, int rownumber) throws SQLException {
					Book book = new Book();

					book.setIsbn(rs.getInt(1));
					book.setTitle(rs.getString(2));
					book.setPublicationYear(rs.getInt(3));
					book.setSellingPrice(rs.getDouble(4));
					book.setCategory(rs.getString(5));
					book.setPublisherName(rs.getString(6));
					book.setQuantity(rs.getInt(7));
					return book;
				}
			});
		} catch (Exception e) {
			return new Book();
		}
	}

	/* this method is used to select a user based on its user name */
	public Person findUserByName(String userName) {
		Person p;
		String selectedUser = "SELECT * FROM USER USE INDEX (PRIMARY) WHERE NAME = '" + userName + "'";
		return p = template.queryForObject(selectedUser, new RowMapper<Person>() {
			@Override
			public Person mapRow(ResultSet rs, int rownumber) throws SQLException {
				Person user = new Person();

				user.setName(rs.getString(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setPhoneNumber(rs.getString(6));
				user.setShippingAddress(rs.getString(7));
				user.setIsManager(rs.getInt(8));
				return user;
			}
		});
	}

	/*
	 * this method is used within the registration stage to allow the user to has a
	 * unique user name by checking if the user name that the user enters is already
	 * exists then the site will refuse it and ask him to enter a new one
	 */
	public boolean isUserNameExists(String name) {
		Person user;
		String select = "SELECT * FROM USER WHERE NAME=\'" + name + "\'";
		try {
			user = template.queryForObject(select, new RowMapper<Person>() {
				@Override
				public Person mapRow(ResultSet rs, int rownumber) throws SQLException {
					Person e = new Person();
					e.setFname(rs.getString(2));
					return e;
				}
			});
		} catch (Exception e) {
			return false;
		}
		if (user.getFname() != "" && user.getFname() != null)/* means that this username is used before */
			return true;
		return false;

	}

	/*
	 * this method is used within the login stage to make sure that the provided
	 * user name and password exists in the database
	 */
	public boolean isUserNameAndPasswordExists(String name, String password) {
		Person user;
		hashedPassword.setPassword(password);
		String select = "SELECT * FROM USER WHERE NAME=\'" + name + "\' AND PASSWORD=\'" + hashedPassword.getPassword()
				+ "\'";
		try {
			user = template.queryForObject(select, new RowMapper<Person>() {
				@Override
				public Person mapRow(ResultSet rs, int rownumber) throws SQLException {
					Person e = new Person();
					e.setFname(rs.getString(2));
					return e;
				}
			});
		} catch (Exception e) {
			return false;
		}
		if (user.getFname() != "" && user.getFname() != null)/* means that this username is exists */
			return true;
		return false;

	}

	/*
	 * this method is used to return the search results in that online store the
	 * user can search with the book title , category , isbn ,publisher name or
	 * author name
	 */
	public List<Book> findSearchResults(String userInput) {
		String readData = "SELECT * FROM BOOK USE INDEX (ISBN_UNIQUE) WHERE ISBN = '" + userInput + "' UNION "
				+ "SELECT * FROM BOOK USE INDEX (TITLE_INDEX) WHERE TITLE = '" + userInput + "' UNION "
				+ "SELECT * FROM BOOK USE INDEX (CATEGORY_INDEX) WHERE CATEGORY = '" + userInput + "' UNION "
				+ "SELECT * FROM BOOK USE INDEX (PUBLISHER_INDEX) WHERE PUBLISHER_NAME = '" + userInput + "' UNION "
				+ "SELECT * FROM BOOK USE INDEX (ISBN_UNIQUE) WHERE ISBN IN (SELECT ISBN FROM AUTHOR USE INDEX (AUTHOR_INDEX) WHERE AUTHOR_NAME = '"
				+ userInput + "')";

		return template.query(readData, new RowMapper<Book>() {
			@Override
			public Book mapRow(ResultSet rs, int rownum) {
				Book book = new Book();
				try {
					book.setIsbn(rs.getInt(1));
					book.setTitle(rs.getString(2));
					book.setPublicationYear(rs.getInt(3));
					book.setSellingPrice(rs.getDouble(4));
					book.setCategory(rs.getString(5));
					book.setPublisherName(rs.getString(6));
					book.setQuantity(rs.getInt(7));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					return new Book();
				}
				return book;
			}
		});
	}

	public int updateUserInfo(Person user, String username) {

		String data = "UPDATE USER SET " + "NAME = " + "'" + user.getName() + "',FNAME = " + "'" + user.getFname() + "'"
				+ ",LNAME = " + "'" + user.getLname() + "'" + " ,PASSWORD = " + "'" + user.getPassword() + "'"
				+ ",EMAIL = " + "'" + user.getEmail() + "'" + " ,PHONENUMBER = " + "'" + user.getPhoneNumber() + "'"
				+ " ,SHIPPINGADDRESS = " + "'" + user.getShippingAddress() + "'" + " ,ISMANAGER = " + "'"
				+ user.getIsManager() + "'" + " WHERE NAME = " + "'" + username + "'";
		return template.update(data);
	}

	public int updateBookInfo(Book book, int isbn) {
		String data = "";
		try {
			data = "UPDATE BOOK SET " + "ISBN = " + "'" + book.getIsbn() + "',TITLE = " + "'" + book.getTitle() + "'"
					+ ",PUBLICATIONYEAR = " + "'" + book.getPublicationYear() + "'" + " ,SELLINGPRICE = " + "'"
					+ book.getSellingPrice() + "'" + ",CATEGORY = " + "'" + book.getCategory() + "'"
					+ " ,publisher_name = " + "'" + book.getPublisherName() + "'" + " ,quantity = " + "'"
					+ book.getQuantity() + "'" + " WHERE ISBN = " + "'" + isbn + "'";

		} catch (Error err) { /* can't update because the new publisher name doesn't exist */
			return -1;
		}
		return template.update(data);
	}

	/* this method is used to select all orders */
	public List<Order> selectOrders() {
		try {
			String orders = "SELECT * FROM ORDERS";
			return template.query(orders, new RowMapper<Order>() {
				@Override
				public Order mapRow(ResultSet rs, int rownumber) throws SQLException {
					Order order = new Order();
					order.setOrderId(rs.getInt(1));
					order.setIsbn(rs.getInt(2));
					order.setQuantity(rs.getInt(3));
					order.setPublisherName(rs.getString(4));
					return order;
				}
			});
		} catch (Exception e) {
			return new LinkedList();
		}

	}

	public int deleteOrder(int orderId) {
		String data = "DELETE FROM ORDERS WHERE ID = " + "'" + orderId + "'";
		return template.update(data);
	}

	// belal part

	private String uname = "";

	public JdbcTemplate getTemplate() {
		return template;
	}

	public List<BooksSold> view(String uname) {
		String read = "select * from books_sold where uname=\'" + uname + "\'";
		return template.query(read, new RowMapper<BooksSold>() {

			@Override
			public BooksSold mapRow(ResultSet rs, int rowNum) throws SQLException {
				BooksSold booksSold = new BooksSold();
				booksSold.setUName(rs.getString(1));
				booksSold.setISBN(rs.getInt(2));
				booksSold.setSellingDate(rs.getDate(3));
				booksSold.setSellingTime(rs.getTime(4));
				booksSold.setQuantity(rs.getInt(5));
				return booksSold;
			}

		});
	}

	public Integer viewAmount(int isbn, String uname) {
		String read = "select * from books_sold where uname=\'" + uname + "\' and isbn =" + isbn;
		System.out.println(read);
		return template.queryForObject(read, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(5);
			}

		});
	}

	public List<Book> vieweElementFromBook(int isbn) {
		String read = "select * from book where isbn =" + isbn;
		return template.query(read, new RowMapper<Book>() {

			@Override
			public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
				Book book = new Book();
				book.setIsbn(rs.getInt(1));
				book.setTitle(rs.getString(2));
				book.setPublicationYear(rs.getInt(3));
				book.setSellingPrice(rs.getDouble(4));
				book.setCategory(rs.getString(5));
				book.setPublisherName(rs.getString(6));
				book.setQuantity(rs.getInt(7));
				return book;
			}

		});
	}

	public void setUname(String name) {
		uname = name;
	}

	public void addToCart(int isbn, int amount, String uname) {
		if (isBookExists(isbn, uname)) {
			Calendar cal = Calendar.getInstance();
			Date calDate = cal.getTime();
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String date = dateFormat.format(calDate);
			String time = timeFormat.format(calDate);
			String insert = "INSERT INTO books_sold(uname, ISBN, SellingDate, SellingTime, amount) VALUES (" + "\'"
					+ uname + "\'" + "," + isbn + "," + "\'" + date + "\'" + "," + "\'" + time + "\'" + "," + amount
					+ ")";
			template.update(insert);
		} else {
			int oldAmount = viewAmount(isbn, uname);
			int newAmount = oldAmount + amount;
			System.out.println(newAmount);
			String update = "UPDATE books_sold SET amount= " + newAmount + " WHERE isbn = " + isbn + " and uname = "
					+ "\'" + uname + "\'";
			template.update(update);
		}

	}

	public void buy(String uname) {
		List<BooksSold> list = view(uname);
		for (int i = 0; i < list.size(); i++) {
			BooksSold book = list.get(i);
			int isbn = book.getISBN();
			int quantity = book.getQuantity();
			List<Book> books = vieweElementFromBook(isbn);
			int newStock = books.get(0).getQuantity() - quantity;
			String update = "UPDATE book SET quantity=" + newStock + " WHERE isbn = " + isbn;
			template.update(update);
			deleteCard(isbn, uname);
		}
	}

	public void deleteCard(int isbn, String uname) {
		String delete = "DELETE FROM books_sold WHERE ISBN = " + isbn + " AND uname=" + "\'" + uname + "\'";
		template.update(delete);
	}

	public void logOutDelete(String uname) {
		String delete = "DELETE FROM books_sold WHERE uname=" + "\'" + uname + "\'";
		template.update(delete);
	}

	public boolean isBookExists(int isbn, String uname) {
		BooksSold book;
		String select = "SELECT * FROM books_sold WHERE isbn= " + isbn + " AND uname = \'" + uname + "\'";
		try {
			book = template.queryForObject(select, new RowMapper<BooksSold>() {
				@Override
				public BooksSold mapRow(ResultSet rs, int rownumber) throws SQLException {
					BooksSold e = new BooksSold();
					e.setUName(rs.getString(1));
					e.setISBN(rs.getInt(2));
					return e;
				}
			});
		} catch (Exception e) {
			return true;
		}
		System.out.println(book.getUName());
		System.out.println(uname);
		System.out.println(book.getISBN());
		System.out.println(isbn);

		if (uname.equals(book.getUName()) && isbn == book.getISBN()) {
			return false;
		} else {
			return true;
		}

	}
}
