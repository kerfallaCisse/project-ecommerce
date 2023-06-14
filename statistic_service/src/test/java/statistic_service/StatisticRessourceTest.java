package statistic_service;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

import org.eclipse.microprofile.config.ConfigProvider;
import org.json.JSONObject;

import jakarta.ws.rs.core.Response;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;

@QuarkusTest
@Tag("components")
@TestHTTPEndpoint(StatisticRessource.class)
@TestProfile(StatisticTestProfile.class)
@TestMethodOrder(OrderAnnotation.class)
public class StatisticRessourceTest {

        String testEmail = ConfigProvider.getConfig().getValue("test.email.one", String.class);
        String cloudinary_image = "http://res.cloudinary.com/dqvvvce88/image/upload/wz1dbmyo22ohwuug3nbi";
        

        @Test
        void testNewUsersStatsWeek() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("users/last_week")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void testNewUsersStatLastMonth() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("users/last_month")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void testNewUsersStatLastThreeMonth() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("users/last_three_month")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void testNewUsersStatLastYear() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("users/last_year")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        // Test for colors

        @Test
        void testColorsStatsWeek() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("colors/last_week")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void testColorsStatLastMonth() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("colors/last_month")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void testColorsStatLastThreeMonth() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("colors/last_three_month")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void testColorsStatLastYear() {
                given()
                                .contentType(MediaType.APPLICATION_JSON)
                                .get("colors/last_year")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        // It's the same for the other

        // Posting new order

        @Test
        void testAddOrder() {

                String requestBody = "{"
                                + "\"pocket\": \"black\","
                                + "\"bag\": \"black\","
                                + "\"quantity\": 10"
                                + "}";

                given()
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(requestBody)
                                .when()
                                .post("colors/")
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
        @Order(1)
        @Test
        void testAddToCart() {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", testEmail);
                jsonObject.put("modelType", "smallModel");
                jsonObject.put("bagColor", "black");
                jsonObject.put("pocketColor", "black");
                jsonObject.put("image", cloudinary_image);
                jsonObject.put("logo", 0);
                jsonObject.put("quantity", 3);

                given()
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObject.toString())
                                .when()
                                .post("cart/add")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());

        }

        @Test
        void testAddToCartWrong() {

                given()
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new JSONObject().toString())
                                .when()
                                .post("cart/add")
                                .then()
                                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
        }

        @Order(2)
        @Test
        void testGetUserCart() {
                given()
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("email", testEmail)
                                .when()
                                .get("cart")
                                .then()
                                .body("[0].modelType", equalTo("smallModel"))
                                .statusCode(Response.Status.OK.getStatusCode());

        }

        @Test
        void testDeliveryConfirmation() {

                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("email", testEmail);
                jsonObjectBuilder.add("firstName", "john");
                jsonObjectBuilder.add("lastName", "doe");
                jsonObjectBuilder.add("address", "Belle vue");
                jsonObjectBuilder.add("zipCode", "1213");
                jsonObjectBuilder.add("town", "Geneva");
                jsonObjectBuilder.add("country", "Switzerland");
                jsonObjectBuilder.add("phoneNumber", "+41 80 800 80 80");

                given()
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jsonObjectBuilder.build().toString())
                                .when()
                                .post("cart/confirmation")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());

        }

        @Order(3)
        @Test
        void testDeleteUserSpecificBasket() {
                given()
                                .queryParam("image", cloudinary_image)
                                .when()
                                .delete("cart")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        @Order(4)
        @Transactional
        void testUpdateUserBasketQuantity() {
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("image", cloudinary_image);

                var statistic_ressource = new StatisticRessource();
                Response resp = statistic_ressource.updateUserBasketQuantity(jsonObjectBuilder.build());

                assertEquals(resp.getStatus(), 404);

        }

        @Transactional
        @Order(5)
        @Test
        void testDeleteUserBasketAfterPayment() {
                given()
                                .queryParam("email", testEmail)
                                .when()
                                .delete("cart/payment")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        // Test profit stats implementation

        @Test
        void getProfitStatLastWeek() {
                given()
                                .when()
                                .get("profit/last_week")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void getProfitStatMonth() {
                given()
                                .when()
                                .get("profit/last_month")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void getProfitStatLastThreeMonths() {
                given()
                                .when()
                                .get("profit/last_three_month")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        @Test
        void getProfitStatLastYear() {
                given()
                                .when()
                                .get("profit/last_year")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());
        }

        /// Test Orderstats

        @Test
        void getBagsPurchased() {
                given()
                                .when()
                                .get("colors/last_week")
                                .then()
                                .statusCode(Response.Status.OK.getStatusCode());

        }

}
