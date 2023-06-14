package api.model;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import api.rest.PaymentRestService;
import domain.model.Basket;
import domain.model.ProductBasket;
import domain.model.Profit;
import domain.service.PaymentServiceImpl;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.equalTo;


@QuarkusTest
@TestHTTPEndpoint(PaymentRestService.class)
@Tag("integration")
public class PaymentResourceTest {

/* Test for ProductBasket Model */
    @Test
    void testProductBasket() {
        ProductBasket productBasket = new ProductBasket();
        productBasket.setProdId("ABC123");
        productBasket.setQuantity(2);

        Assertions.assertEquals("ABC123", productBasket.getProdId());
        Assertions.assertEquals(2, productBasket.getQuantity());
    }

    @Test
    void testEqualsProductBasket() {
        // Create two ProductBasket objects with the same values
        ProductBasket basket1 = new ProductBasket();
        basket1.setProdId("ABC123");
        basket1.setQuantity(2);

        ProductBasket basket2 = new ProductBasket();
        basket2.setProdId("ABC123");
        basket2.setQuantity(2);

        // Assert that the two objects are equal
        assertEquals(basket1, basket2);
    }

    @Test
    void testNotEqualsProductBasket() {
        // Create two ProductBasket objects with the same values
        ProductBasket basket1 = new ProductBasket();
        basket1.setProdId("ABC123");
        basket1.setQuantity(2);

        ProductBasket basket2 = new ProductBasket();
        basket2.setProdId("ABC123");
        basket2.setQuantity(3);

        assertNotEquals(basket1, basket2);
    }   

    @Test
    void testToStringProductBasket() {
        ProductBasket basket = new ProductBasket();
        basket.setProdId("ABC123");
        basket.setQuantity(2);

        String expectedToString = "ProductBasket(prodId=ABC123, quantity=2)";

        assertEquals(expectedToString, basket.toString());
    }

    @Test
    void testHashCodeProductBasket() {
        ProductBasket basket1 = new ProductBasket();
        basket1.setProdId("ABC123");
        basket1.setQuantity(2);

        ProductBasket basket2 = new ProductBasket();
        basket2.setProdId("ABC123");
        basket2.setQuantity(2);

        assertEquals(basket1.hashCode(), basket2.hashCode());
    }


/* Test for Basket Model */
    @Test
    void testBasket() {

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
    void testEqualsBasket() {
        // Create two Basket objects with the same values
        Basket basket1 = new Basket();
        List<ProductBasket> products1 = new ArrayList<>();
        ProductBasket product1 = new ProductBasket();
        product1.setProdId("ABC123");
        product1.setQuantity(2);
        products1.add(product1);
        basket1.setProducts(products1);

        Basket basket2 = new Basket();
        List<ProductBasket> products2 = new ArrayList<>();
        ProductBasket product2 = new ProductBasket();
        product2.setProdId("ABC123");
        product2.setQuantity(2);
        products2.add(product2);
        basket2.setProducts(products2);

        // Assert that the two objects are equal
        assertEquals(basket1, basket2);
    }

    @Test
    void testToStringBasket() {
        // Create a Basket object
        Basket basket = new Basket();
        List<ProductBasket> products = new ArrayList<>();
        ProductBasket product = new ProductBasket();
        product.setProdId("ABC123");
        product.setQuantity(2);
        products.add(product);
        basket.setProducts(products);

        // Get the string representation of the object
        String expectedToString = "Basket(products=[ProductBasket(prodId=ABC123, quantity=2)])";

        // Assert that the toString() method returns the expected string
        assertEquals(expectedToString, basket.toString());
    }

    @Test
    void testHashCodeBasket() {
        // Create two Basket objects with the same values
        Basket basket1 = new Basket();
        List<ProductBasket> products1 = new ArrayList<>();
        ProductBasket product1 = new ProductBasket();
        product1.setProdId("ABC123");
        product1.setQuantity(2);
        products1.add(product1);
        basket1.setProducts(products1);

        Basket basket2 = new Basket();
        List<ProductBasket> products2 = new ArrayList<>();
        ProductBasket product2 = new ProductBasket();
        product2.setProdId("ABC123");
        product2.setQuantity(2);
        products2.add(product2);
        basket2.setProducts(products2);

        // Assert that the hash codes are equal
        assertEquals(basket1.hashCode(), basket2.hashCode());
    }


/* Test for Profit Model */
    @Test
    void testEqualsProfit() {
        Profit profit1 = new Profit();
        profit1.setAmount(100.0);
        profit1.setCreated_at(LocalDate.now());

        Profit profit2 = new Profit();
        profit2.setAmount(100.0);
        profit2.setCreated_at(LocalDate.now());

        assertEquals(profit1,profit2);
    }

    @Test
    void testNotEqualsProfit() {

        Profit profit1 = new Profit();
        profit1.setAmount(100.0);
        profit1.setCreated_at(LocalDate.now());

        Profit profit2 = new Profit();
        profit2.setAmount(200.0);
        profit2.setCreated_at(LocalDate.now());

        assertNotEquals(profit1,profit2);
    }

    @Test
    void testToStringProfit() {
        Profit profit = new Profit();
        profit.setAmount(100.0);
        profit.setCreated_at(LocalDate.now());

        String expectedString = "Profit(amount=100.0, created_at=" + LocalDate.now() + ")";
        
        assertEquals(expectedString, profit.toString());
    }

    @Test
    void testHashCodeProfit() {
        Profit profit1 = new Profit();
        profit1.setAmount(100.0);
        profit1.setCreated_at(LocalDate.of(2023, 6, 14));

        Profit profit2 = new Profit();
        profit2.setAmount(100.0);
        profit2.setCreated_at(LocalDate.of(2023, 6, 14));

        assertEquals(profit1.hashCode(), profit2.hashCode());
    }
        
    @Test
    void testUpdateAmount_InvalidAmount() {
        JsonObject requestJson = Json.createObjectBuilder()
                .add("amount", -50)
                .build();

        PaymentRestService paymentRestService = new PaymentRestService();

        Response response = paymentRestService.updateAmount(requestJson);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

/* Test PaymentServiceImpl */


    @Test
    void testPaymentServiceImpl(){
        PaymentServiceImpl paymentService = new PaymentServiceImpl();        
        assertNotNull(paymentService);
    }

    // @ConfigProperty(name = "stripe.api.key") 
    // String apiKey;
    
    // @Test
    // void testCreateCheckoutSession() throws StripeException {

    //     PaymentServiceImpl paymentService = new PaymentServiceImpl();

    //     ProductBasket product = new ProductBasket();
    //     product.setProdId("prod_O1FSw1MDp2WNIT");
    //     product.setQuantity(2);

    //     List<ProductBasket> productList = new ArrayList<>();
    //     productList.add(product);
        
    //     Basket basket = new Basket();
    //     basket.setProducts(productList);

    //     JsonObject result = paymentService.createCheckoutSession(basket);
        
    //     assertNotNull(result);
    // }

/* Test PaymentRestService */
    @Test
    @Transactional
    void testUpdateAmount_ValidAmount() {
        JsonObject requestJson = Json.createObjectBuilder()
                .add("amount", 100)
                .build();

        PaymentRestService paymentRestService = new PaymentRestService();

        Response response = paymentRestService.updateAmount(requestJson);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    @Transactional
    void testUpdateAmount_UnvalidAmount() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("amount", -150)
                .build();
        
        PaymentRestService paymentRestService = new PaymentRestService();

        Response response = paymentRestService.updateAmount(jsonObject);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    @Transactional
    void testCreateProfit() {

        Profit profit = new Profit();
        profit.setAmount(100.0);
        profit.setCreated_at(LocalDate.now());


        profit.persist();
        Profit persistedProfit = Profit.findById(profit.id);

        assertTrue(profit.isPersistent());
        assertNotNull(persistedProfit);
        assertEquals(profit.getAmount(), persistedProfit.getAmount());
        assertEquals(profit.getCreated_at(), persistedProfit.getCreated_at());
    }
    
/* Test path */
    @Test
    void testCheckoutWithoutProdId() {

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
    void testCheckoutBadProdId() {

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
    void testCheckoutQuantityZero() {

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
    void testCheckoutQuantityNeg() {

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

    // @Test
    // void testCheckoutSession() throws StripeException {

    //     PaymentService paymentServiceMock = Mockito.mock(PaymentService.class);

    //     PaymentRestService paymentRestService = new PaymentRestService();
        
    //     Basket basket = new Basket();
    //     JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    //     jsonObjectBuilder.add("amount",260.0);
    //     jsonObjectBuilder.add("url", "https://checkout.stripe.com/c/pay/cs_test_b1AQ7alb3RtHZkq8A5aTkjOXbBeg2C7sMP71hPk5ALrUX832VBKujYi1Xe#fidkdWxOYHwnPyd1blpxYHZxWjA0S0NGXERBYm1JRFc8aklIX3VOQmMzalR2UVM2U2ZhN2JINkNASGREPHdJUjV0ajJxZ1VHcz0yUVRVSTA1S0tLN0F2VjJcQURwdXZHUHJpX3V0cVdmQTJTNTVrQ113YzA1MicpJ2N3amhWYHdzYHcnP3F3cGApJ2lkfGpwcVF8dWAnPydocGlxbFpscWBoJyknYGtkZ2lgVWlkZmBtamlhYHd2Jz9xd3BgeCUl");

    //     JsonObject sessionJson = jsonObjectBuilder.build();

    //     Mockito.when(paymentServiceMock.createCheckoutSession(any(Basket.class))).thenReturn(sessionJson);

    //     Response response = paymentRestService.checkoutSession(basket);

    //     assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    // }

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


    // @Test
    // void testPersistenceAndRetrieval() {
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
    //     
    //}
}