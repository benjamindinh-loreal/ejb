package com.ejbank.payloads.account;

import java.io.Serializable;

public class AccountPayload implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int id ;
	private final String type ;
	private final float balance ;
	
	public AccountPayload(int id, String type, float balance) {
		this.id = id;
		this.type = type;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public float getAmount() {
		return balance;
	}

	
	
}
