package com.ejbank.payloads.user;

public class UserPayload {
	private final String lastname ;
	private final String firstname ;
	
	public UserPayload(String lastname, String firstname) {
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}
	
}
