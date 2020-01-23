package com.ejbank.payloads.transaction;

import java.util.ArrayList;

public class TransactionsAccountPayload {
    private final int total ;
    private final ArrayList<TransactionPayload> transactions ;

    public TransactionsAccountPayload(int total, ArrayList<TransactionPayload> transactions) {
        this.total = total;
        this.transactions = transactions;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<TransactionPayload> getTransactions() {
        return transactions;
    }
}
