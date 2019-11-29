package com.ejbank.sessions.account;

import javax.ejb.Local;


import com.ejbank.payloads.account.AccountListPayload;
import com.ejbank.payloads.account.AccountPayload;

@Local
public interface AccountSessionLocal {
	public AccountPayload get(int id) ;

	public AccountListPayload getByCustomerId(int customer_id);
}
