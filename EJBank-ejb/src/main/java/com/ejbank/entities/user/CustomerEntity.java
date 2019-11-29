package com.ejbank.entities.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Table(name="ejbank_customer")
@PrimaryKeyJoinColumn(name="id")
public class CustomerEntity extends UserEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="advisor_id") private int advisor_id ;

	public int getAdvisor_id() {
		return advisor_id;
	}	
}