package api.rest;

import com.stripe.exception.StripeException;
import domain.model.*;
import domain.service.PaymentService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/payment")
public class PaymentRestService {
    
    @Inject
    PaymentService payment;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String checkoutSession(Basket basket) throws StripeException{
        String session = payment.createCheckoutSession(basket);
        return session;
    }
}
