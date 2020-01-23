package com.ejbank.payloads.account;

public class AccountDetailPayload {

    private final String owner ;
    private final String advisor ;
    private final float rate ;
    private final float interest ;
    private final float amount ;
    private final String error ;

    public AccountDetailPayload(String owner, String advisor, float rate, float interest, float amount, String error) {
        this.owner = owner;
        this.advisor = advisor;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
        this.error = error;
    }

    public AccountDetailPayload(String error) {
        this.error = error;
        this.owner = "";
        this.advisor = "";
        this.rate = 0;
        this.interest = 0;
        this.amount = 0;
    }

    public String getOwner() {
        return owner;
    }

    public String getAdvisor() {
        return advisor;
    }

    public float getRate() {
        return rate;
    }

    public float getInterest() {
        return interest;
    }

    public float getAmount() {
        return amount;
    }
}
