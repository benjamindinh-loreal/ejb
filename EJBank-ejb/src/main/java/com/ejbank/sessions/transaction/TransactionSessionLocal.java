package com.ejbank.sessions.transaction;

import com.ejbank.payloads.transaction.PreviewTransactionPayload;
import com.ejbank.payloads.transaction.SendTransactionPayload;
import com.ejbank.payloads.transaction.TransactionValidatePayload;
import com.ejbank.payloads.transaction.TransactionsAccountPayload;

import javax.ejb.Local;
import java.math.BigDecimal;

@Local
public interface TransactionSessionLocal {
	public int getUserTransactions(int customer_id) ;

	public PreviewTransactionPayload getPreview(Integer source, Integer destination, BigDecimal amount, Integer author) ;

	public SendTransactionPayload sendTransaction(Integer source, Integer destination, BigDecimal amount, String comment, Integer author) ;

    public TransactionsAccountPayload getTransactions(int account_id, int offset, int user_id);

    public TransactionValidatePayload validateTransaction(int transaction_id, boolean boolean_decision, int user_id);
}
