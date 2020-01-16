package com.ejbank.entities.user;

import com.ejbank.entities.account.AccountEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ejbank_customer")
@DiscriminatorValue("customer")
public class CustomerEntity extends UserEntity {

	@OneToMany
	@JoinColumn(name = "customer_id")
	private List<AccountEntity> accounts = new ArrayList<>() ;

	private static final long serialVersionUID = 1L;
	
	@Column(name="advisor_id") private int advisor_id ;

	public int getAdvisor_id() {
		return advisor_id;
	}

	public List<AccountEntity> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountEntity> accounts) {
		this.accounts = accounts;
	}
}