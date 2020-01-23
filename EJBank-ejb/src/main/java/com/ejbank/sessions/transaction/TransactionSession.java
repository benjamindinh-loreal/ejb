package com.ejbank.sessions.transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ejbank.entities.account.AccountEntity;
import com.ejbank.entities.transaction.TransactionEntity;
import com.ejbank.entities.user.AdvisorEntity;
import com.ejbank.entities.user.CustomerEntity;
import com.ejbank.entities.user.UserEntity;
import com.ejbank.payloads.transaction.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	public TransactionsAccountPayload getTransactions(int account_id, int offset, int user_id) {

		AccountEntity account = em.find(AccountEntity.class, account_id) ;
		UserEntity user = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", user_id).getSingleResult();

		ArrayList<TransactionPayload> transactions = new ArrayList<>() ;

		int total_transaction = 0 ;
		int offset_counter = offset ;
		int offset_max = offset + 10 ;

		for (TransactionEntity transaction : account.getTransactions()) {

			AccountEntity account_dest = em.find(AccountEntity.class, transaction.getAccount_id_to()) ;
			UserEntity user_dest = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", account_dest.getCustomerId()).getSingleResult();

			String parsed_applied = transaction.getApplied() == 1 ? "APPLYED" : "TO_APPROVE" ;

			if(offset_counter < offset_max){
				transactions.add( new TransactionPayload(
						transaction.getId(),
						transaction.getDate(),
						account.getType().getName(),
						account_dest.getType().getName(),
						user_dest.getFirstname() + " " + user_dest.getLastname(),
						transaction.getAmount(),
						user.getFirstname() + " " + user.getLastname(),
						transaction.getComment(),
						parsed_applied
				)) ;
			}

			total_transaction++ ;
		}

		return new TransactionsAccountPayload(total_transaction, transactions) ;

	}

	@Override
	public TransactionValidatePayload validateTransaction(int transaction_id, boolean boolean_decision, int user_id) {


		UserEntity user = em.createNamedQuery("UserEntity.findById", UserEntity.class).setParameter("id", user_id).getSingleResult();
		TransactionEntity transaction = em.createNamedQuery("TransactionEntity.getTransactionById", TransactionEntity.class).setParameter("id", transaction_id).getSingleResult() ;

		if (user instanceof CustomerEntity){
			return new TransactionValidatePayload(false, "Transaction non validée","Vous n'êtes pas un conseiller") ;
		}

		if(boolean_decision){
			AccountEntity src = em.createNamedQuery("AccountEntity.getById", AccountEntity.class).setParameter("id", transaction.getAccount_id_from()).getSingleResult() ;
			AccountEntity dest = em.createNamedQuery("AccountEntity.getById", AccountEntity.class).setParameter("id", transaction.getAccount_id_to()).getSingleResult() ;

			Float before = src.getBalance() - transaction.getAmount() ;
			Float after = dest.getBalance() + transaction.getAmount() ;

			if(before < 0) {
				return new TransactionValidatePayload(false, "Transaction non validée", "Vous ne disposez pas d'un solde suffisant...") ;
			}

			src.setBalance(before);
			dest.setBalance(after);

		}

		transaction.setApplied(1);

		return new TransactionValidatePayload(true,"Transaction validée",null) ;
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
