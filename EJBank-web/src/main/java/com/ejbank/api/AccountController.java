package com.ejbank.api;

import javax.ejb.EJB;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ejbank.payloads.account.AccountDetailPayload;
import com.ejbank.sessions.account.AccountSessionLocal;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped

public class AccountController {

    @EJB
    private AccountSessionLocal accountService ;

    @GET
    @Path("/{account_id}/{user_id}")
    public AccountDetailPayload getAccountDetail(@PathParam("account_id") int account_id, @PathParam("user_id") int user_id) {
        return accountService.getAccountDetail(account_id, user_id);
    }
}


