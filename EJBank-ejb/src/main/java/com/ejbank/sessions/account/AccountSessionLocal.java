package com.ejbank.sessions.account;

import javax.ejb.Local;


import com.ejbank.payloads.account.*;

@Local
public interface AccountSessionLocal {
	public AccountPayload get(int id) ;

	public AccountListPayload getByCustomerId(int customer_id);

	public AccountListPayload getAllAccountsUser(int customer_id);

	public AccountAttachedListPayload getAccountAttached(int customer_id) ;

    public AccountDetailPayload getAccountDetail(int account_id, int user_id);
}
