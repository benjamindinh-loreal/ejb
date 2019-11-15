package com.ejbank.test;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.entities.UserEntity;

@Stateless
@LocalBean
public class TestBean implements TestBeanLocal {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em ;
	
    @Override
    public String test() {
        UserEntity user = em.find(UserEntity.class, 2) ;
    	return user.getLastname();
    }
}
