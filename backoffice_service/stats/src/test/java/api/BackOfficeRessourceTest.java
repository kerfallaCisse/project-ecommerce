package api;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.hamcrest.CoreMatchers.equalTo;
import io.quarkus.test.junit.QuarkusTest;


import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BackOfficeRessourceTest {
    
    @Test
    @Order(1)
    void getNumberOfUserConnected() {
        given()
            .when()
            .get("/stats/connected")
            .then()
            .body(equalTo(30))
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(2)
    void getNumbOfAccountsCreated() {
        given()
        .when()
        .get("/stats/user_account")
        .then()
        .body(equalTo(100))
        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(3)
    void getNumberOfUserWhoMadeAnOrder() {
        given()
        .when()
        .get("/stats/user_commands")
        .then()
        .body(equalTo(50))
        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(4)
    void getNumberOfAbandonedOrders() {
        given()
        .when()
        .get("/stats/abandoned_orders")
        .then()
        .body(equalTo(10))
        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(5)
    void getColorsStats() {
        given()
        .when()
        .get("/stats/colors_stats")
        .then()
        .body("size()", equalTo(7))
        .statusCode(Response.Status.OK.getStatusCode());
    }

    

}
