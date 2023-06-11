package auth_service;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(AuthenticationService.class)
public class AuthenticationServiceTest {

    @Test
    public void testProtectedEnpoint() {
        given()
          .when().get()
          .then()
             .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

}