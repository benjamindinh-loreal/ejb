package com.ejbank.payloads.transaction;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.io.Serializable;

@TransactionManagement(TransactionManagementType.BEAN)
public class SendTransactionPayload implements Serializable {

    private Boolean result ;
    private String message ;

    public SendTransactionPayload() {
        super() ;
    }

    public SendTransactionPayload(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
