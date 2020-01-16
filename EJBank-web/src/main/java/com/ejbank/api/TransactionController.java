package com.ejbank.api;

import javax.ejb.EJB;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.ejbank.POST.PreviewTransactionPost;
import com.ejbank.POST.SendTransactionPost;
import com.ejbank.payloads.transaction.PreviewTransactionPayload;
import com.ejbank.payloads.transaction.SendTransactionPayload;
import com.ejbank.sessions.transaction.TransactionSessionLocal;

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


}
