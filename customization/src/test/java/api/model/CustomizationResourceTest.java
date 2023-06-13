package api.model;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given; 
import static org.hamcrest.Matchers.*;

@TestProfile(CustomizationTestProfile.class)
@QuarkusTest
public class CustomizationResourceTest {

    // Test on GET /customization
    /*
    // Test if returned model is correct
    @Test
    public void testGetImageLarge() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "Blue")
                .queryParam("pocketColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(6))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlueBlue"));
    }

    @Test
    public void testGetImageSmall() {
        given()
                .queryParam("modelType", "smallModel")
                .queryParam("bagColor", "Blue")
                .queryParam("pocketColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(6))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlueBlue"));
    }

    @Test
    public void testGetImageWithoutModel() {
        given()
                .queryParam("bagColor", "Blue")
                .queryParam("pocketColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(6))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlueBlue"));
    }

    @Test
    public void testGetImageWithoutBagColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("pocketColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(7))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlackBlue"));
    }

    @Test
    public void testGetImageWithoutPocketColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(2))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlueBlack"));
    }

    @Test
    public void testGetImageWithoutParams() {
        given()
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlackBlack"));
    }

    // Test if model does not exist
    @Test
    public void testGetImageWithWrongModel() {
        given()
                .queryParam("modelType", "fakeModel")
                .queryParam("bagColor", "Blue")
                .queryParam("pocketColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("bag not found"));
    }

    @Test
    public void testGetImageWithWrongBagColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "fakeColor")
                .queryParam("pocketColor", "Blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("bag not found"));
    }

    @Test
    public void testGetImageWithWrongPocketColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "Blue")
                .queryParam("pocketColor", "fakeColor")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("bag not found"));
    }
    */

}