package com.ejbank.api;

import javax.ejb.EJB;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.payloads.account.AccountListPayload;
import com.ejbank.sessions.account.AccountSessionLocal;
import com.ejbank.sessions.user.UserSessionLocal;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountController {
	
	@EJB
	private AccountSessionLocal accountService ;
	@EJB
	private UserSessionLocal userService ;
	
	@GET
    @Path("/{customer_id}")
    public AccountListPayload get(@PathParam("customer_id") int customer_id) {
		AccountListPayload account = accountService.getByCustomerId(customer_id) ;
        return account ;
    }
	
	@GET
    @Path("/all/{customer_id}")
    public String getAllAccounts(@PathParam("customer_id") int customer_id) {
		return userService.getUser(customer_id);
		
		/*AccountListPayload account = accountService.getByCustomerId(customer_id) ;
        return account ;*/
    }
	
}
