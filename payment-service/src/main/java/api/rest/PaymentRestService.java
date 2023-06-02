package api.rest;

import com.stripe.exception.StripeException;
import domain.model.*;
import domain.service.PaymentService;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/payment")
public class PaymentRestService {

    @Inject
    PaymentService payment;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkoutSession(Basket basket) throws StripeException {
        JsonObject session = payment.createCheckoutSession(basket);
        if (session == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        //return Response.ok(session).build();
        return Response.ok(session).build();
    }
    
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update_amount")
    public Response updateAmount(JsonObject jsonObject) {

        Double amount = Double.parseDouble(jsonObject.getJsonNumber("amount").toString());
        LocalDate currentDate = LocalDate.now();
        Profit profit = Profit.findById(1L);
        Double initial_amount = profit.getAmount();

        if (amount <= 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        profit.setAmount(amount + initial_amount);
        profit.setCreated_at(currentDate);
        profit.persist();

        if (profit.isPersistent())
            return Response.status(Response.Status.OK).build();

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
