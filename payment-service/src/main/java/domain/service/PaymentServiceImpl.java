package domain.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceSearchResult;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceSearchParams;
import com.stripe.param.checkout.SessionCreateParams;
import domain.model.Basket;
import domain.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;



@ApplicationScoped
public class PaymentServiceImpl implements PaymentService{

    @ConfigProperty(name = "stripe.api.key") 
    String apiKey;

    public PaymentServiceImpl(){}
    
    @Override
    public String createCheckoutSession(Basket basket) throws StripeException{
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (Product product : basket.getProducts()) {
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(Long.valueOf(product.getQuantity()))
                    .setPrice(getPriceId(product.getProdId()))
                    .build();
            lineItems.add(lineItem);
        }
    
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setSuccessUrl("http://localhost:8080/success.html")
                .setCancelUrl("http://localhost:8080/cancel.html")
                .setCurrency(basket.getCurrent())
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }

    private String getPriceId(String productId) {        
        try {

            PriceSearchParams params =
            PriceSearchParams
                .builder()
                .setQuery("product:'"+ productId +"' AND active:'true' ")
                .build();

            PriceSearchResult priceSearchResult = Price.search(params);
            String priceId = priceSearchResult.getData().get(0).getId();
            
            return priceId;

        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
