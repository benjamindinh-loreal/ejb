package com.ejbank.sessions.account;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.ejbank.entities.account.AccountEntity;
import com.ejbank.payloads.account.AccountListPayload;
import com.ejbank.payloads.account.AccountPayload;

@Stateless
@LocalBean
public class AccountSession implements AccountSessionLocal {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em ;
	
	@Override
	public AccountPayload get(int id) {
		AccountEntity account = em.find(AccountEntity.class, id) ;
	
    	return new AccountPayload(account.getId(), account.getType().getName(), account.getBalance());
	}
	
	@Override
	public AccountListPayload getByCustomerId(int customer_id) {
		List<AccountEntity> account = em.createNamedQuery("AccountEntity.findByUserId", AccountEntity.class).setParameter("customer_id", customer_id).getResultList() ;
		
		return new AccountListPayload(account, "");
	}
	
	
}
