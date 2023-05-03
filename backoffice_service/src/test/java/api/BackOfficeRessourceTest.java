package api;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
//import static org.hamcrest.CoreMatchers.equalTo;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(BackOfficeRessource.class)
public class BackOfficeRessourceTest {
    
    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"admin"})
    void testWithTheCorrespondingRole() {

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .when()
            .get("connected")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    @TestSecurity(user = "testUser", roles = {"user"})
    void testWithWrongRole() {
        given()
            .when()
            .get("connected")
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }


    @Test
    @Order(1)
    @TestSecurity(authorizationEnabled = false)
    void getNumberOfUserConnected() {
        given()
            .when()
            .get("connected")
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(2)
    @TestSecurity(authorizationEnabled = false)
    void getNumbOfAccountsCreated() {
        given()
        .when()
        .get("user_account")
        .then()
        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(3)
    @TestSecurity(authorizationEnabled = false)
    void getNumberOfUserWhoMadeAnOrder() {
        given()
        .when()
        .get("user_commands")
        .then()
        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(4)
    @TestSecurity(authorizationEnabled = false)
    void getNumberOfAbandonedOrders() {
        given()
        .when()
        .get("abandoned_orders")
        .then()
        .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(5)
    @TestSecurity(authorizationEnabled = false)
    void getColorsStats() {
        given()
        .when()
        .get("colors_stats")
        .then()
        .statusCode(Response.Status.OK.getStatusCode());
    }

    

}
