package com.ejbank.entities.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Table(name="ejbank_advisor")
@PrimaryKeyJoinColumn(name="id")
public class AdvisorEntity extends UserEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}