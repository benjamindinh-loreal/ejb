package com.ejbank.sessions.transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.entities.account.AccountEntity;
import com.ejbank.entities.transaction.TransactionEntity;
import com.ejbank.entities.user.AdvisorEntity;
import com.ejbank.entities.user.CustomerEntity;
import com.ejbank.entities.user.UserEntity;
import com.ejbank.payloads.transaction.PreviewTransactionPayload;
import com.ejbank.payloads.transaction.SendTransactionPayload;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigDecimal;
import java.util.Objects;

@Stateless
@LocalBean
public class TransactionSession implements TransactionSessionLocal {
	
	@PersistenceContext(unitName = "EJBankPU")
	private EntityManager em ;

	@Override
	public int getUserTransactions(int customer_id) {
		return em.createNamedQuery("TransactionEntity.getCustomerCountTransactions", TransactionEntity.class).setParameter("author", customer_id).getResultList().size() ;
	}

	@Override
	public PreviewTransactionPayload getPreview(Integer source, Integer destination, BigDecimal amount, Integer author) {

		String message = "" ;
		String error = null ;
		Boolean result = true ;

		// A LA FIN : CHECK SI ADVISOR = 2 COMPTES PERSONNES DIFFERENTE OK SINON CUSTOMER PEUT PAS FAIRE TRANSACTION ENTRE DEUX COMPTE PERSONNE !=
		//UserEntity user = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id_customer", author).getSingleResult() ;

		AccountEntity src = em.createNamedQuery("AccountEntity.getById", AccountEntity.class).setParameter("id", source).getSingleResult() ;
		AccountEntity dest = em.createNamedQuery("AccountEntity.getById", AccountEntity.class).setParameter("id", destination).getSingleResult() ;

		Float before = src.getBalance() - amount.floatValue() ;
		Float after = dest.getBalance() + amount.floatValue() ;

		if(before < 0) {
			error = "Vous ne disposez pas d'un solde suffisant..." ;
			result = false ;
		}

		if(src.getId() == dest.getId()){
			error = "Vous ne pouvez pas faire de virement du même compte sur le même compte" ;
			result = false ;
		}

		return new PreviewTransactionPayload(result, before, after, message, error) ;
	}

	@Override
	public SendTransactionPayload sendTransaction(Integer source, Integer destination, BigDecimal amount, String comment, Integer author) {

		AccountEntity src = em.createNamedQuery("AccountEntity.getById", AccountEntity.class).setParameter("id", source).getSingleResult() ;
		AccountEntity dest = em.createNamedQuery("AccountEntity.getById", AccountEntity.class).setParameter("id", destination).getSingleResult() ;

		Float before = src.getBalance() - amount.floatValue() ;
		Float after = dest.getBalance() + amount.floatValue() ;

		if(before < 0) {
			return new SendTransactionPayload(false, "Vous ne disposez pas d'un solde suffisant...") ;
		}

		if(src.getId() == dest.getId()){
			return new SendTransactionPayload(false, "Vous ne pouvez pas faire de virement du même compte sur le même compte") ;
		}

		UserEntity user = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", author).getSingleResult() ;

		if(amount.floatValue() <= 1000.00) {
			src.setBalance(before);
			dest.setBalance(after);

			TransactionEntity transaction = new TransactionEntity() ;
			transaction.setAccount_id_from(source);
			transaction.setAccount_id_to(destination);
			transaction.setAuthor(author);
			transaction.setComment(comment);
			transaction.setAmount(amount.floatValue());
			transaction.setApplied(1);

			em.persist(transaction);
			em.flush();

			return new SendTransactionPayload(true, "Virement effectué avec succès !!") ;

		}	else if (amount.floatValue() > 1000.00) {

			if (user instanceof CustomerEntity) {
				TransactionEntity transaction = new TransactionEntity() ;
				transaction.setAccount_id_from(source);
				transaction.setAccount_id_to(destination);
				transaction.setAuthor(author);
				transaction.setComment(comment);
				transaction.setAmount(amount.floatValue());
				transaction.setApplied(0);

				em.persist(transaction);
				em.flush();

				return new SendTransactionPayload(true, "Attente de validation du conseillé") ;
			} else if (user instanceof AdvisorEntity) {
				src.setBalance(before);
				dest.setBalance(after);

				TransactionEntity transaction = new TransactionEntity() ;
				transaction.setAccount_id_from(source);
				transaction.setAccount_id_to(destination);
				transaction.setAuthor(author);
				transaction.setComment(comment);
				transaction.setAmount(amount.floatValue());
				transaction.setApplied(1);

				em.persist(transaction);
				em.flush();

				return new SendTransactionPayload(true, "Virement effectué avec succès !!") ;
			}
		}


		return new SendTransactionPayload(false, "Erreur inatendue") ;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TransactionSession that = (TransactionSession) o;
		return Objects.equals(em, that.em);
	}

	@Override
	public int hashCode() {
		return Objects.hash(em);
	}
}
