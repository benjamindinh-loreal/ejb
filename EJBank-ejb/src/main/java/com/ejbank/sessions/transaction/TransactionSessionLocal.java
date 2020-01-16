package com.ejbank.sessions.transaction;

import com.ejbank.payloads.transaction.PreviewTransactionPayload;
import com.ejbank.payloads.transaction.SendTransactionPayload;

import javax.ejb.Local;
import java.math.BigDecimal;

@Local
public interface TransactionSessionLocal {
	public int getUserTransactions(int customer_id) ;

	public PreviewTransactionPayload getPreview(Integer source, Integer destination, BigDecimal amount, Integer author) ;

	public SendTransactionPayload sendTransaction(Integer source, Integer destination, BigDecimal amount, String comment, Integer author) ;
}
