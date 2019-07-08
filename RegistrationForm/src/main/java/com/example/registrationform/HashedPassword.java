package com.example.registrationform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashedPassword {
	
	private String password;

	
	
	public String getPassword() {
		try {
			return hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
		}
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		/*getInstance() method takes the name of the one-way hash function algorithm to use.*/
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(password.getBytes());
		byte[] b = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b1 : b) {
			sb.append(Integer.toHexString(b1 & 0xff).toString());
		}
		return sb.toString();
	}

}
