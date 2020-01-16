package com.ejbank.POST;

import java.io.Serializable;
import java.math.BigDecimal;

public class SendTransactionPost implements Serializable {

    private Integer source;
    private Integer destination;
    private BigDecimal amount;
    private String comment;
    private Integer author;

    public SendTransactionPost() {
        super();
    }

    public SendTransactionPost(Integer source, Integer destination, BigDecimal amount, String comment, Integer author) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.comment = comment;
        this.author = author;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }
}
