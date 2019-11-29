package com.ejbank.sessions.transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.entities.transaction.TransactionEntity;

@Stateless
@LocalBean
public class TransactionSession implements TransactionSessionLocal {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em ;
	
	@Override
	public int getUserTransactions(int customer_id) {
		return em.createNamedQuery("TransactionEntity.getCustomerCountTransactions", TransactionEntity.class).setParameter("author", customer_id).getResultList().size() ;
	}
	
}
