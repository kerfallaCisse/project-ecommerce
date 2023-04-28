package api.model;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CustomizationResourceTest {

    @Test
    public void testCustomizationEndpoint() {
        given()
          .when().get("customization")
          .then()
             .statusCode(200)
             .body(is("Hello from customization endpoint"));
    }

}