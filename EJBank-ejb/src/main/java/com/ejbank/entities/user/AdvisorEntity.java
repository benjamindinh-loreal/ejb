package com.ejbank.entities.user;

import com.ejbank.entities.account.AccountEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ejbank_advisor")
@DiscriminatorValue("advisor")
public class AdvisorEntity extends UserEntity {


	@OneToMany
	@JoinColumn(name = "advisor_id")
	private List<CustomerEntity> customers = new ArrayList<>() ;

	private static final long serialVersionUID = 1L;

	public List<CustomerEntity> getCustomers() {
		return customers;
	}
}