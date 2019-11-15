package com.ejbank.payloads;

public class UserPayload {
	private final String lastname ;
	private final String firstname ;
	
	public UserPayload(String lastname, String firstname) {
		super();
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
