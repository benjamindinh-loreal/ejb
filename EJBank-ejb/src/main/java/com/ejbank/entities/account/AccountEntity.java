package com.ejbank.entities.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ejbank_account")
@NamedQueries({
	@NamedQuery(name="AccountEntity.findByUserId", query="SELECT c FROM AccountEntity c where c.customer_id = :customer_id"),
	@NamedQuery(name="AccountEntity.getTransactionsAccounts", query="SELECT c FROM AccountEntity c where c.customer_id = :customer_id"),
})
public class AccountEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	@Column(name="id") 
	@Id
	private int id ;
	@Column(name="customer_id") private int customer_id ;
	@Column(name="balance") private float balance ;
	
	@ManyToOne
	@JoinColumn( name="account_type_id", referencedColumnName="id" )
    private AccountTypeEntity type;

	
    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }
	
	public int getCustomerId() {
		return customer_id;
	}

	public float getBalance() {
		return balance;
	}
	
	public AccountTypeEntity getType() {
		return type ;
	}
	
}
