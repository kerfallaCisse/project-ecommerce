package api.model;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given; 
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

@TestProfile(CustomizationTestProfile.class)
@QuarkusTest
public class CustomizationResourceTest {

    // Test on GET /customization
    
    // Test if returned model is correct
    /* 
    @Test
    public void testGetLargeImageWithoutLogo() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null
        
        given()
                .multiPart("modelType", "largeModel")
                .multiPart("bagColor", "Blue")
                .multiPart("pocketColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("modelType", equalTo("largeModel"))
                .body("bagColor", equalTo("Blue"))
                .body("pocketColor", equalTo("Blue"))
                .body("email", equalTo("test@gmail.com"))
                .body("quantity", equalTo(1))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlueBlue"));
    }

    @Test
    public void testGetSmallImageWithoutLogo() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null
        
        given()
                .multiPart("modelType", "smallModel")
                .multiPart("bagColor", "Blue")
                .multiPart("pocketColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("modelType", equalTo("smallModel"))
                .body("bagColor", equalTo("Blue"))
                .body("pocketColor", equalTo("Blue"))
                .body("email", equalTo("test@gmail.com"))
                .body("quantity", equalTo(1))
                .body("cloudinary_url", equalTo("http://fake_url.com/BlueBlue"));
    }

    @Test
    public void testGetImageWithoutModel() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null

        given()
                .multiPart("bagColor", "Blue")
                .multiPart("pocketColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("type error"));
    }

    @Test
    public void testGetImageWithoutBagColor() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null

        given()
                .multiPart("modelType", "smallModel")
                .multiPart("pocketColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("type error"));
    }

    @Test
    public void testGetImageWithoutPocketColor() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null

        given()
                .multiPart("modelType", "smallModel")
                .multiPart("bagColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("type error"));
    }

    // Test if model does not exist
    @Test
    public void testGetImageWithWrongModel() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null

        given()
                .multiPart("modelType", "wrongModel")
                .multiPart("bagColor", "Blue")
                .multiPart("pocketColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("bag not found"));
    }

    @Test
    public void testGetImageWithWrongBagColor() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null

        given()
                .multiPart("modelType", "largeModel")
                .multiPart("bagColor", "wrongColor")
                .multiPart("pocketColor", "Blue")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("bag not found"));
    }

    @Test
    public void testGetImageWithWrongPocketColor() throws IOException {
        File tempFile = File.createTempFile("test", ".png"); // null

        given()
                .multiPart("modelType", "largeModel")
                .multiPart("bagColor", "Blue")
                .multiPart("pocketColor", "wrongColor")
                .multiPart("email", "test@gmail.com")
                .multiPart("quantity", "1")
                .multiPart("file", tempFile)
                .when().post("/customization")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("error", equalTo("bag not found"));
    }
    */

}