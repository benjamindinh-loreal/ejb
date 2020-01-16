package com.ejbank.payloads.transaction;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreviewTransactionPayload implements Serializable {

    private Boolean result ;
    private Float before ;
    private Float after ;
    private String message ;
    private String error ;

    public PreviewTransactionPayload() {
        super() ;
    }

    public PreviewTransactionPayload(Boolean result, Float before, Float after, String message, String error) {
        this.result = result;
        this.before = before;
        this.after = after;
        this.message = message;
        this.error = error;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Float getBefore() {
        return before;
    }

    public void setBefore(Float before) {
        this.before = before;
    }

    public Float getAfter() {
        return after;
    }

    public void setAfter(Float after) {
        this.after = after;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
