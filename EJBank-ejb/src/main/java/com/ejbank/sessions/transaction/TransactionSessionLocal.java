package com.ejbank.sessions.transaction;

import javax.ejb.Local;

@Local
public interface TransactionSessionLocal {
	public int getUserTransactions(int customer_id) ;
}
