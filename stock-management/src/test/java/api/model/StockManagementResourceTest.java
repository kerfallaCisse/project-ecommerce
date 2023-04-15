package api.model;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestProfile(StockTestProfile.class)
public class StockManagementResourceTest {

    // Test if new model creation is correct
    @Test
    public void testInsertModel() {
        String requestBody = "{"
                + "\"modelType\": \"small\","
                + "\"color_pocket_name\": \"red\","
                + "\"color_bag_name\": \"blue\","
                + "\"quantity\": 10"
                + "}";
        given()
          .contentType(ContentType.JSON)
          .body(requestBody)
          .when().post("/stock/update")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("[0].result", equalTo("ok"));
    }
    
    // Test if model is shown correctly
    @Test
    public void testGetStock() {
        given()
          .when().get("/stock")
          .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("[0].modelType", equalTo("SmallModel"))
            .body("[0].quantity", equalTo(10))
            .body("[0].color_pocket_name", equalTo("red"))
            .body("[0].color_bag_name", equalTo("blue"));
    }

    // Test if quantity update is correct
    @Test
    public void testUpdateQuantity() {
        String requestBody = "{"
                + "\"modelType\": \"small\","
                + "\"color_pocket_name\": \"red\","
                + "\"color_bag_name\": \"blue\","
                + "\"quantity\": 2"
                + "}";
        given()
          .contentType(ContentType.JSON)
          .body(requestBody)
          .when().post("/stock/update")
          .then()
             .statusCode(200)
             .contentType(ContentType.JSON)
             .body("[0].result", equalTo("ok"));
        
        given()
          .when().get("/stock")
          .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("[0].modelType", equalTo("SmallModel"))
            .body("[0].quantity", equalTo(12))
            .body("[0].color_pocket_name", equalTo("red"))
            .body("[0].color_bag_name", equalTo("blue"));
    }
    
}
