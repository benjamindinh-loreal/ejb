package com.ejbank.POST;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreviewTransactionPost implements Serializable {

    private Integer source ;
    private Integer destination ;
    private BigDecimal amount ;
    private Integer author ;

    public PreviewTransactionPost() {
        super() ;
    }

    public PreviewTransactionPost(Integer source, Integer destination, BigDecimal amount, Integer author) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
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

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

}
