package domain.service;

import com.stripe.Stripe;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PriceSearchResult;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceSearchParams;
import com.stripe.param.checkout.SessionCreateParams;
import domain.model.*;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;





@ApplicationScoped
public class PaymentServiceImpl implements PaymentService{

    @ConfigProperty(name = "stripe.api.key") 
    String apiKey;

    public PaymentServiceImpl(){}
    
    @Override
    public  JsonObject createCheckoutSession(Basket basket) throws StripeException{
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (ProductBasket product : basket.getProducts()) {
            int quantity = product.getQuantity();
            String prod_id = product.getProdId();

            try{
                Product.retrieve(prod_id);
            }catch(InvalidRequestException e) {
                System.out.println("An InvalidRequestException has occurred : " + e.getMessage());
                return null;
            }

            if (prod_id == null || quantity <= 0 || (Integer)quantity == null ) {
                return null;
            }

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(Long.valueOf(quantity))
                    .setPrice(getPriceId(prod_id))
                    .build();
            lineItems.add(lineItem);
        }
        
        try {
            SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            // .setSuccessUrl("http://localhost:8080/success.html")
            // .setCancelUrl("http://localhost:8080/cancel.html")
            .setSuccessUrl("https://pinfo3.unige.ch/success.html")
            .setCancelUrl("https://pinfo3.unige.ch/cancel.html")
            .setCurrency("chf")
            .addAllLineItem(lineItems)
            .build();

            Session session = Session.create(params);

            System.out.print(session);
            Double total_amount = (double) session.getAmountTotal()/100;
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("amount",total_amount);
            jsonObjectBuilder.add("url",session.getUrl());
    
            return jsonObjectBuilder.build();

        } catch (InvalidRequestException e) {
            System.out.println("An InvalidRequestException has occurred : " + e.getMessage());
            return null;

        } catch (StripeException e) {
            System.out.println("A Stripe exception has occurred : " + e.getMessage());
            return null;
        }

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

        } catch (InvalidRequestException e) {
            System.out.println("An InvalidRequestException has occurred: " + e.getMessage());
            return null;

        } catch (StripeException e) {
            System.out.println("A Stripe exception has occurred  : " + e.getMessage());
            return null;
        }
    }
}
