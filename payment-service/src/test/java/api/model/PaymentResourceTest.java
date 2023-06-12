package api.model;

import api.rest.PaymentRestService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PaymentRestService.class)
@Tag("integration")
public class PaymentResourceTest {
    
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

    // @Test
    // public void testCheckoutWithoutQuantity() {

    //     JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    //     jsonObjectBuilder.add("prodId","prod_NvFbGlQ0kp8mVU");
        
    //     JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
    //     jsonArrayBuilder.add(jsonObjectBuilder);
    //     jsonObjectBuilder.add("products",jsonArrayBuilder);

    //     given()
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .accept(MediaType.APPLICATION_JSON)
    //         .body(jsonObjectBuilder.build())
    //         .when()
    //         .post("payment")
    //         .then()
    //         .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    // }

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

}