package domain.service;

import com.stripe.exception.StripeException;
import domain.model.*;
import javax.json.JsonObject;


public interface PaymentService {
    JsonObject createCheckoutSession(Basket basket) throws StripeException;
}
