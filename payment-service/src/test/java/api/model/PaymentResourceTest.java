package api.model;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import api.rest.PaymentRestService;
import domain.model.Basket;
import domain.model.ProductBasket;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@QuarkusTest
@TestHTTPEndpoint(PaymentRestService.class)
@Tag("integration")
public class PaymentResourceTest {

    @Test
    public void testBasket() {

        List<ProductBasket> productBaskets = new ArrayList<>();

        ProductBasket productBasket1 = new ProductBasket();
        productBasket1.setProdId("ABC123");
        productBasket1.setQuantity(2);

        ProductBasket productBasket2 = new ProductBasket();
        productBasket2.setProdId("DEF456");
        productBasket2.setQuantity(3);
        productBaskets.add(productBasket1);
        productBaskets.add(productBasket2);

        Basket basket = new Basket();
        basket.setProducts(productBaskets);

        Assertions.assertEquals(productBaskets, basket.getProducts());
    }

    @Test
    public void testProductBasket() {
        ProductBasket productBasket = new ProductBasket();
        productBasket.setProdId("ABC123");
        productBasket.setQuantity(2);

        Assertions.assertEquals("ABC123", productBasket.getProdId());
        Assertions.assertEquals(2, productBasket.getQuantity());
    }
    
    @Test
    public void testCheckoutWithoutProdId() {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("prodId","");
        jsonObjectBuilder.add("quantity",0);
        
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(jsonObjectBuilder);
        jsonObjectBuilder.add("products",jsonArrayBuilder);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonObjectBuilder.build())
            .when()
            .post("payment")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testCheckoutBadProdId() {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("prodId","chdgchmsgdfdhasfd");
        jsonObjectBuilder.add("quantity",1);
        
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(jsonObjectBuilder);
        jsonObjectBuilder.add("products",jsonArrayBuilder);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonObjectBuilder.build())
            .when()
            .post("payment")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testCheckoutQuantityZero() {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("prodId","prod_NvFbGlQ0kp8mVU");
        jsonObjectBuilder.add("quantity", 0);
        
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(jsonObjectBuilder);
        jsonObjectBuilder.add("products",jsonArrayBuilder);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonObjectBuilder.build())
            .when()
            .post("payment")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }


    @Test
    public void testCheckoutQuantityNeg() {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("prodId","prod_NvFbGlQ0kp8mVU");
        jsonObjectBuilder.add("quantity", -1);
        
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(jsonObjectBuilder);
        jsonObjectBuilder.add("products",jsonArrayBuilder);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonObjectBuilder.build())
            .when()
            .post("payment")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testCheckoutQunnantityNeg() {

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("prodId","prod_NvFbGlQ0kp8mVU");
        jsonObjectBuilder.add("quantity", -1);
        
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        jsonArrayBuilder.add(jsonObjectBuilder);
        jsonObjectBuilder.add("products",jsonArrayBuilder);

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonObjectBuilder.build())
            .when()
            .post("payment")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

//     @Test
//     void testCheckoutSession() throws StripeException {

//         PaymentService paymentServiceMock = Mockito.mock(PaymentService.class);

//         PaymentRestService paymentRestService = new PaymentRestService();
        
//         Basket basket = new Basket();
//         JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
//         jsonObjectBuilder.add("amount",260.0);
//         jsonObjectBuilder.add("url", "https://checkout.stripe.com/c/pay/cs_test_b1AQ7alb3RtHZkq8A5aTkjOXbBeg2C7sMP71hPk5ALrUX832VBKujYi1Xe#fidkdWxOYHwnPyd1blpxYHZxWjA0S0NGXERBYm1JRFc8aklIX3VOQmMzalR2UVM2U2ZhN2JINkNASGREPHdJUjV0ajJxZ1VHcz0yUVRVSTA1S0tLN0F2VjJcQURwdXZHUHJpX3V0cVdmQTJTNTVrQ113YzA1MicpJ2N3amhWYHdzYHcnP3F3cGApJ2lkfGpwcVF8dWAnPydocGlxbFpscWBoJyknYGtkZ2lgVWlkZmBtamlhYHd2Jz9xd3BgeCUl");

//         JsonObject sessionJson = jsonObjectBuilder.build();

//         Mockito.when(paymentServiceMock.createCheckoutSession(any(Basket.class))).thenReturn(sessionJson);

//         Response response = paymentRestService.checkoutSession(basket);

//         assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//     }

//     @Test
//     void testCheckoutSession_NullSession() throws StripeException {

//         PaymentService paymentServiceMock = Mockito.mock(PaymentService.class);

//         PaymentRestService paymentRestService = new PaymentRestService();

//         Mockito.when(paymentServiceMock.createCheckoutSession(any(Basket.class))).thenReturn(null);

//         Response response = paymentRestService.checkoutSession(new Basket());

//         assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//     }

    // @Test
    // void testUpdateAmount() {
    //     JsonObject requestJson = Json.createObjectBuilder()
    //             .add("amount", 100)
    //             .build();

    //     PaymentRestService paymentRestService = new PaymentRestService();

    //     Response response = paymentRestService.updateAmount(requestJson);

    //     assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    // }
        
    @Test
    void testUpdateAmount_InvalidAmount() {
        JsonObject requestJson = Json.createObjectBuilder()
                .add("amount", -50)
                .build();


                
        PaymentRestService paymentRestService = new PaymentRestService();

        Response response = paymentRestService.updateAmount(requestJson);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    // @Test
    // public void testCreateProfit() {
    //     // Create a new Profit object
    //     Profit profit = new Profit();
    //     profit.setAmount(100.0);
    //     profit.setCreated_at(LocalDate.now());

    //     // Persist the Profit object to the database
    //     profit.persist();

    //     // Retrieve the persisted Profit object by its ID
    //     Profit persistedProfit = Profit.findById(profit.getId());

    //     // Assert that the retrieved Profit object matches the original one
    //     assertEquals(profit.getAmount(), persistedProfit.getAmount());
    //     assertEquals(profit.getCreated_at(), persistedProfit.getCreated_at());
    // }


    // @Test
    // public void testPersistenceAndRetrieval() {
    //     // Create a new Profit object
    //     Profit profit = new Profit();
    //     profit.setAmount(100.0);
    //     profit.setCreated_at(LocalDate.now());

    //     // Persist the Profit object to the database
    //     profit.persist();

    //     // Retrieve the persisted Profit object by its ID
    //     Profit retrievedProfit = Profit.findById(profit.id);

    //     // Assert that the retrieved Profit object is not null
    //     assertNotNull(retrievedProfit);

    //     // Assert that the retrieved Profit object has the same amount and created_at values
    //     assertEquals(profit.getAmount(), retrievedProfit.getAmount());
    //     assertEquals(profit.getCreated_at(), retrievedProfit.getCreated_at());

    //     // Assert that the profit object is persistent
    //     assertTrue(profit.isPersistent());
    //}
}