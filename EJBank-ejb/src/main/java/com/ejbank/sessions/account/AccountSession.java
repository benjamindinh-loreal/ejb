package com.ejbank.sessions.account;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.ejbank.entities.account.AccountEntity;
import com.ejbank.entities.transaction.TransactionEntity;
import com.ejbank.entities.user.AdvisorEntity;
import com.ejbank.entities.user.CustomerEntity;
import com.ejbank.entities.user.UserEntity;
import com.ejbank.payloads.account.*;

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

	@Override
	public AccountAttachedListPayload getAccountAttached(int advisor_id) {
		UserEntity user = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", advisor_id).getSingleResult() ;

		if(user instanceof AdvisorEntity) {

			ArrayList<AccountAttachedPayload> accounts = new ArrayList<>() ;

			List<CustomerEntity> customers = ((AdvisorEntity) user).getCustomers() ;

			for (CustomerEntity customer : customers) {
				for (AccountEntity account : customer.getAccounts()) {

					UserEntity userAccount = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", account.getCustomerId()).getSingleResult() ;
					ArrayList<TransactionEntity> transactions = new ArrayList<>() ;

					for (TransactionEntity transaction : account.getTransactions()) {
						if(transaction.getApplied() == 0) {
							transactions.add(transaction) ;
						}
					}

					accounts.add(
							new AccountAttachedPayload(
									account.getId(),
									userAccount.getFirstname() + " " + userAccount.getLastname(),
									account.getType().getName(),
									account.getBalance(),transactions.size()
							)
					) ;




				}
			}

			return new AccountAttachedListPayload(accounts) ;

		}

		return new AccountAttachedListPayload(new ArrayList<AccountAttachedPayload>()) ;
	}

	@Override
	public AccountDetailPayload getAccountDetail(int account_id, int user_id) {

		AccountEntity account = em.find(AccountEntity.class, account_id) ;

		CustomerEntity user = (CustomerEntity) em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", user_id).getSingleResult();

		AdvisorEntity advisor = (AdvisorEntity) em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", user.getAdvisor_id()).getSingleResult();

		if(account.getCustomerId() != user_id) {
			return new AccountDetailPayload("Le compte n'appartient pas à cet utilisateur") ;
		}

		float interest = account.getBalance() * (account.getType().getRate() / 100) ;

		return new AccountDetailPayload(
				user.getFirstname() + " " + user.getLastname(),
				advisor.getFirstname() + " " + advisor.getLastname(),
				account.getType().getRate(),
				interest,
				account.getBalance(),
				null
		) ;

	}


}
