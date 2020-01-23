package com.ejbank.api;

import javax.ejb.EJB;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.payloads.account.AccountAttachedListPayload;
import com.ejbank.payloads.account.AccountListPayload;
import com.ejbank.sessions.account.AccountSessionLocal;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountsController {
	
	@EJB
	private AccountSessionLocal accountService ;

	@GET
    @Path("/{customer_id}")
    public AccountListPayload get(@PathParam("customer_id") int customer_id) {
		AccountListPayload account = accountService.getByCustomerId(customer_id) ;
        return account ;
    }
	
	@GET
    @Path("/all/{customer_id}")
    public AccountListPayload getAllAccounts(@PathParam("customer_id") int customer_id) {
		return accountService.getAllAccountsUser(customer_id);
    }

	@GET
	@Path("/attached/{advisor_id}")
	public AccountAttachedListPayload getAllAccountsAttached(@PathParam("advisor_id") int advisor_id) {
		return accountService.getAccountAttached(advisor_id);
	}
}
