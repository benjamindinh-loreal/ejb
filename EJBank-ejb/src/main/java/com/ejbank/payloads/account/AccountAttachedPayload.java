package com.ejbank.payloads.account;

import java.io.Serializable;

public class AccountAttachedPayload implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int id ;
    private final String user ;
    private final String type ;
    private final float amount ;
    private final int validation ;

    public AccountAttachedPayload(int id, String user, String type, float amount, int validation) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.validation = validation;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }

    public int getValidation() {
        return validation;
    }
}

