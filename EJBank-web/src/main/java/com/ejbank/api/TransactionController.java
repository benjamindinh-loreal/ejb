package com.ejbank.api;

import javax.ejb.EJB;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.ejbank.POST.PreviewTransactionPost;
import com.ejbank.POST.SendTransactionPost;
import com.ejbank.POST.ValidateTransactionPost;
import com.ejbank.payloads.transaction.PreviewTransactionPayload;
import com.ejbank.payloads.transaction.SendTransactionPayload;
import com.ejbank.payloads.transaction.TransactionValidatePayload;
import com.ejbank.payloads.transaction.TransactionsAccountPayload;
import com.ejbank.sessions.account.AccountSessionLocal;
import com.ejbank.sessions.transaction.TransactionSessionLocal;
import com.ejbank.sessions.user.UserSessionLocal;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransactionController {
	
	@EJB
	private TransactionSessionLocal transactionService ;

	@EJB
	private AccountSessionLocal accountService ;

	@EJB
	private UserSessionLocal userService ;

	@GET
    @Path("/validation/notification/{customer_id}")
    public int get(@PathParam("customer_id") int customer_id) {
		return transactionService.getUserTransactions(customer_id) ;
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/preview")
	public PreviewTransactionPayload preview(PreviewTransactionPost ptp) {
		return transactionService.getPreview(ptp.getSource(), ptp.getDestination(), ptp.getAmount(), ptp.getAuthor()) ;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public SendTransactionPayload preview(SendTransactionPost stp) {
		return transactionService.sendTransaction(stp.getSource(),stp.getDestination(),stp.getAmount(),stp.getComment(),stp.getAuthor()) ;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/validation")
	public TransactionValidatePayload preview(ValidateTransactionPost vtp) {
		return transactionService.validateTransaction(vtp.getTransaction(), vtp.getApprove() ,vtp.getAuthor()) ;
	}

	@GET
	@Path("/list/{account_id}/{offset}/{user_id}")
	public TransactionsAccountPayload getTransactionList(@PathParam("account_id") int account_id, @PathParam("offset") int offset, @PathParam("user_id") int user_id) {
		return transactionService.getTransactions(account_id, offset, user_id) ;
	}

}
