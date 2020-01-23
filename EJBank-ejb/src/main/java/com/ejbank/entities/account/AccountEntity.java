package com.ejbank.entities.account;

import com.ejbank.entities.transaction.TransactionEntity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account")
@NamedQueries({
        @NamedQuery(name = "AccountEntity.findByUserId", query = "SELECT c FROM AccountEntity c where c.customer_id = :customer_id"),
        @NamedQuery(name = "AccountEntity.getTransactionsAccounts", query = "SELECT c FROM AccountEntity c where c.customer_id = :customer_id"),
        @NamedQuery(name = "AccountEntity.getAdvisorAccount", query = "SELECT c FROM AccountEntity c where c.customer_id = :customer_id"),
        @NamedQuery(name = "AccountEntity.getById", query = "SELECT c FROM AccountEntity c where c.id = :id"),
})
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    private int id;
    @Column(name = "customer_id")
    private int customer_id;
    @Column(name = "balance")
    private float balance;

    @ManyToOne
    @JoinColumn(name = "account_type_id", referencedColumnName = "id")
    private AccountTypeEntity type;

    @OneToMany
    @JoinColumn(name = "account_id_to", referencedColumnName = "id")
    private List<TransactionEntity> transactions;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public float getBalance() {
        return balance;
    }

    public AccountTypeEntity getType() {
        return type;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
