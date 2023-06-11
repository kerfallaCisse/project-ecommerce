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

   
}