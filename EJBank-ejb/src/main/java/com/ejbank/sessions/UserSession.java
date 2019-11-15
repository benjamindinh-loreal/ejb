package com.ejbank.sessions;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.entities.UserEntity;
import com.ejbank.payloads.UserPayload;

@Stateless
@LocalBean
public class UserSession implements UserSessionLocal {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em ;
	
	@Override
	public UserPayload get(int id) {
		UserEntity user = em.find(UserEntity.class, id) ;
	
    	return new UserPayload(user.getFirstname(), user.getLastname());
	}
	
	@Override
	public UserPayload getByLastName(String lastname) {
		UserEntity user = em.createNamedQuery("UserEntity.findByLastname", UserEntity.class).setParameter("lastname", lastname).getSingleResult() ;
	
    	return new UserPayload(user.getFirstname(), user.getLastname());
	}
	
	
}
