package com.ejbank.payloads.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ejbank.entities.account.AccountEntity;

public class AccountListPayload implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<AccountPayload> accounts = new ArrayList<AccountPayload>() ;
	private String error ;
	
	public AccountListPayload() {} ;
	
	public AccountListPayload(List<AccountEntity> accountsEntities, String error) {
		for(AccountEntity ae : accountsEntities) {
			this.accounts.add(new AccountPayload(ae.getId(),ae.getType().getName(),ae.getBalance())) ;
		}
		
		this.error = error ;
	}

	public ArrayList<AccountPayload> getAccounts() {
		return accounts;
	}

	public String getError() {
		return error;
	}
}
