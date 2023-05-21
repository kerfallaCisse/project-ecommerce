package domain.service;

import com.stripe.exception.StripeException;
import domain.model.*;

public interface PaymentService {
    String createCheckoutSession(Basket basket) throws StripeException;
}
