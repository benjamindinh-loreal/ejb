package com.ejbank.api;

import javax.ejb.EJB;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.payloads.account.AccountListPayload;
import com.ejbank.payloads.account.AccountPayload;
import com.ejbank.payloads.user.UserPayload;
import com.ejbank.sessions.account.AccountSessionLocal;
import com.ejbank.sessions.transaction.TransactionSessionLocal;
import com.ejbank.sessions.user.UserSessionLocal;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransactionController {
	
	@EJB
	private TransactionSessionLocal transactionService ;
	
	@GET
    @Path("/validation/notification/{customer_id}")
    public int get(@PathParam("customer_id") int customer_id) {
		return transactionService.getUserTransactions(customer_id) ;
    }	
}
