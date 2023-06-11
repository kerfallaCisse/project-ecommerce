package auth_service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AuthenticationServiceTest {

    // @Test
    // public void testProtectedEnpoind() {
    //     given()
    //       .when().get("stats")
    //       .then()
    //          .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    // }

}