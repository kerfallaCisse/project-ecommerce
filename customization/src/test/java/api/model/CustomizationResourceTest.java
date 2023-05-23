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

    // Test if returned model is correct
    @Test
    public void testGetImageLarge() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "blue")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(2))
                .body("[0].cloudinary_url", equalTo("http://fake_url.com"));
    }

    @Test
    public void testGetImageSmall() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "blue")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(2))
                .body("[0].cloudinary_url", equalTo("http://fake_url.com"));
    }

    @Test
    public void testGetImageWithoutModel() {
        given()
                .queryParam("bagColor", "blue")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(1))
                .body("[0].cloudinary_url", equalTo("http://fake_url.com"));
    }

    @Test
    public void testGetImageWithoutBagColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(1))
                .body("[0].cloudinary_url", equalTo("http://fake_url.com"));
    }

    @Test
    public void testGetImageWithoutPocketColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(1))
                .body("[0].cloudinary_url", equalTo("http://fake_url.com"));
    }

    @Test
    public void testGetImageWithoutParams() {
        given()
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].id", equalTo(1))
                .body("[0].cloudinary_url", equalTo("http://fake_url.com"));
    }

    // Test if model does not exist
    @Test
    public void testGetImageWithWrongModel() {
        given()
                .queryParam("modelType", "fakeModel")
                .queryParam("bagColor", "Blue")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].error", equalTo("bag not found"));
    }

    @Test
    public void testGetImageWithWrongBagColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "fakeColor")
                .queryParam("pocketColor", "blue")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].error", equalTo("bag not found"));
    }

    @Test
    public void testGetImageWithWrongPocketColor() {
        given()
                .queryParam("modelType", "largeModel")
                .queryParam("bagColor", "blue")
                .queryParam("pocketColor", "fakeColor")
                .when().get("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].error", equalTo("bag not found"));
    }

}