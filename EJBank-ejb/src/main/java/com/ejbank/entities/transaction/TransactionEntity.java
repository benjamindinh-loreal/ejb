package com.ejbank.entities.transaction;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ejbank_transaction")
@NamedQueries({
	@NamedQuery(name="TransactionEntity.getCustomerCountTransactions", query="SELECT c FROM TransactionEntity c where c.author = :author AND c.applied = 0"),
})
public class TransactionEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	@Column(name="id") 
	@Id
	private int id ;
	@Column(name="account_id_from") private int account_id_from ;
	@Column(name="account_id_to") private int account_id_to ;
	@Column(name="author") private int author ;
	@Column(name="amount") private float amount ;
	@Column(name="comment") private String comment ;
	@Column(name="applied") private int applied ;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

	public int getAccount_id_from() {
		return account_id_from;
	}

	public void setAccount_id_from(int account_id_from) {
		this.account_id_from = account_id_from;
	}

	public int getAccount_id_to() {
		return account_id_to;
	}

	public void setAccount_id_to(int account_id_to) {
		this.account_id_to = account_id_to;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getApplied() {
		return applied;
	}

	public void setApplied(int applied) {
		this.applied = applied;
	}
		
}
