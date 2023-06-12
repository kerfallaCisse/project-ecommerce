package statistic_service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import jakarta.ws.rs.core.Response;

@QuarkusTest
@Tag("components")
@TestHTTPEndpoint(StatisticRessource.class)
public class StatisticRessourceTest {

    // Test for new user

    @Test
    void testNewUsersStatsWeek() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("users/last_week")
                .then()
                //.body("SATURDAY", equalTo(1))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testNewUsersStatLastMonth() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("users/last_month")
                .then()
                //.body("week2", equalTo(1))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testNewUsersStatLastThreeMonth() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("users/last_three_month")
                .then()
                //.body("week10", equalTo(1))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testNewUsersStatLastYear() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("users/last_year")
                .then()
                //.body("month12", equalTo(3))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    // Test for colors

    @Test
    void testColorsStatsWeek() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("colors/last_week")
                .then()
                //.body("red", equalTo(4))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testColorsStatLastMonth() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("colors/last_month")
                .then()
                //.body("red", equalTo(21))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testColorsStatLastThreeMonth() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("colors/last_three_month")
                .then()
                //.body("blue", equalTo(26))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void testColorsStatLastYear() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("colors/last_year")
                .then()
                //.body("black", equalTo(14))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    // It's the same for the other

    // Postinf new order

    @Test
    void testAddOrder() {

        String requestBody = "{"
                + "\"pocket\": \"black\","
                + "\"bag\": \"black\","
                + "\"quantity\": 10"
                + "}";

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("colors")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    void testAddOrderWrong() {

        String requestBody = "{"
                + "\"pocket\": \"xxx\","
                + "\"bag\": \"xxx\","
                + "\"quantity\": 10"
                + "}";

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post("colors")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    // Test to add abandoned basket in the db
    @Test
    void testAbandonedBasket() {

        String requestBody = "{"
                + "\"modelType\": \"largeModel\""
                + "}";

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post("abandoned_basket")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

    }

    @Test
    void testAbandonedBasketWrong() {

        String requestBody = "{"
                + "\"modelType\": \"xxxx\""
                + "}";

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .when()
                .post("abandoned_basket")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    // Cart testing

//     @Test
//     void testAddToCart() {

//         String requestBody = "{"
//                 + "\"email\": \"kerfciss@gmail.com\","
//                 + "\"modelType\": \"smallModel\","
//                 + "\"bagColor\": \"black\","
//                 + "\"pocketColor\": \"black\","
//                 + "\"image\": \"http://res.cloudinary.com/dqvvvce88/image/upload/wz1dbmyo22ohwuug3nbi\","
//                 + "\"logo\": \"0\","
//                 + "\"quantity\": 3"
//                 + "}";

//         given()
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(requestBody)
//                 .when()
//                 .post("cart/add")
//                 .then()
//                 .statusCode(Response.Status.CREATED.getStatusCode());

//     }

//     @Test
//     void testAddToCartWrong() {
//         String requestBody = "{"
//                 + "\"email\": \"kerfciss@gmail.com\","
//                 + "\"modelType\": \"juuju\","
//                 + "\"bagColor\": \"black\","
//                 + "\"pocketColor\": \"ppp\","
//                 + "\"image\": \"http://res.cloudinary.com/dqvvvce88/image/upload/wz1dbmyo22ohwuug3nbi\","
//                 + "\"logo\": \"0\","
//                 + "\"quantity\": 3"
//                 + "}";

//         given()
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(requestBody)
//                 .when()
//                 .post("cart/add")
//                 .then()
//                 .statusCode(Response.Status.CREATED.getStatusCode());
//     }

  //  @Test
  //  void testGetUserCart() {
   //     given()
   //             .accept(MediaType.APPLICATION_JSON)
   //             .contentType(MediaType.APPLICATION_JSON)
   //             .queryParam("email", "john@gmail.com")
   //             .when()
   //             .get("cart")
    //            .then()
                //.body("[0].modelType", equalTo("smallModel"))
     //           .statusCode(Response.Status.OK.getStatusCode());

    //}

//     @Test
//     void testDeliveryConfirmation() {
//         String requestBody = "{"
//                 + "\"email\": \"kerfciss@gmail.com\","
//                 + "\"firstName\": \"Cissé\","
//                 + "\"lastName\": \"Kerfalla\","
//                 + "\"address\": \"Belle vue\","
//                 + "\"zipCode\": \"1213\","
//                 + "\"town\": \"Geneva\","
//                 + "\"country\": \"Switzerland\","
//                 + "\"phoneNumber\": \"+41 80 800 80 80\""
//                 + "}";

//         given()
//                 .accept(MediaType.APPLICATION_JSON)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .body(requestBody)
//                 .when()
//                 .post("cart/confirmation")
//                 .then()
//                 .statusCode(Response.Status.OK.getStatusCode());

//     }

}
