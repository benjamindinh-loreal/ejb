package com.ejbank.payloads.account;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountAttachedListPayload implements Serializable {

    private ArrayList<AccountAttachedPayload> accounts ;

    public AccountAttachedListPayload(ArrayList<AccountAttachedPayload> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<AccountAttachedPayload> getAccounts() {
        return accounts;
    }

}
