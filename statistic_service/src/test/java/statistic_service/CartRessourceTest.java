package statistic_service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import jakarta.ws.rs.core.Response;

@QuarkusTest
@Tag("components")
@TestHTTPEndpoint(CartRessource.class)
public class CartRessourceTest {
    @Test
    void testAddToCart() {
        
        String requestBody = "{"
        + "\"email\": \"kerfciss@gmail.com\","
        + "\"modelType\": \"smallModel\","
        + "\"bagColor\": \"black\","
        + "\"pocketColor\": \"black\","
        + "\"image\": \"http://res.cloudinary.com/dqvvvce88/image/upload/wz1dbmyo22ohwuug3nbi\","
        + "\"logo\": \"0\","
        + "\"quantity\": 3"
        + "}";

        given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .when()
            .post("add")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());

    }

    @Test
    void testAddToCartWrong() {
        String requestBody = "{"
        + "\"email\": \"kerfciss@gmail.com\","
        + "\"modelType\": \"juuju\","
        + "\"bagColor\": \"black\","
        + "\"pocketColor\": \"ppp\","
        + "\"image\": \"http://res.cloudinary.com/dqvvvce88/image/upload/wz1dbmyo22ohwuug3nbi\","
        + "\"logo\": \"0\","
        + "\"quantity\": 3"
        + "}";

        given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .when()
            .post("add")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    void testGetUserCart() {
        given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .queryParam("email", "john@gmail.com")
            .when()
            .get()
            .then()
            .body("[0].modelType",equalTo("smallModel"))
            .statusCode(Response.Status.OK.getStatusCode());

    }

    @Test
    void testDeliveryConfirmation() {
        String requestBody = "{"
        + "\"email\": \"kerfciss@gmail.com\","
        + "\"firstName\": \"Cissé\","
        + "\"lastName\": \"Kerfalla\","
        + "\"address\": \"Belle vue\","
        + "\"zipCode\": \"1213\","
        + "\"town\": \"Geneva\","
        + "\"country\": \"Switzerland\","
        + "\"phoneNumber\": \"+41 80 800 80 80\""
        + "}";

        given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .when()
            .post("confirmation")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());

    }

}
