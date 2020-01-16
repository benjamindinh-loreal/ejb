package com.ejbank.sessions.user;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.entities.account.AccountEntity;
import com.ejbank.entities.user.AdvisorEntity;
import com.ejbank.entities.user.CustomerEntity;
import com.ejbank.entities.user.UserEntity;
import com.ejbank.payloads.account.AccountListPayload;
import com.ejbank.payloads.user.UserPayload;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public AccountListPayload getAllAccountsUser(int id) {
		UserEntity user = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", id).getSingleResult() ;
		
		if(user instanceof AdvisorEntity) {
			List<CustomerEntity> customers = ((AdvisorEntity) user).getCustomers() ;

			List<AccountEntity> accounts = new ArrayList<>() ;

			for (CustomerEntity customer : customers) {
				for (AccountEntity account : customer.getAccounts()) {
					accounts.add(account) ;
				}
			}

			return new AccountListPayload(accounts, "") ;
		} else if(user instanceof CustomerEntity) {
			return new AccountListPayload(((CustomerEntity) user).getAccounts(), "") ;
		}
		
		return null ;
	}
	
	
}
