package api.model;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@TestProfile(StockTestProfile.class)
class StockManagementResourceTest {

	// Test on POST /stock

	// Test if new model creation is correct
	@Test
	void testInsertModel() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": 10"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].result", equalTo("ok"));
	}

	// Test if quantity can be negative
	void testInsertModelWithNegativeQuantity() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": -10"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("subtraction error"))
				.log().body();
	}

	// Test if some of body data is missing
	@Test
	void testInsertModelWithoutModel() {
		String requestBody = "{"
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": 10"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("missing param or type error"));
	}

	@Test
	void testInsertModelWithoutPocketName() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": 10"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("missing param or type error"));
	}

	@Test
	void testInsertModelWithoutBagName() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_pocket_name\": \"red\","
				+ "\"quantity\": 10"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("missing param or type error"));
	}

	@Test
	void testInsertModelWithoutQuantity() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\""
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("missing param or type error"));
	}

	// Test if some of body data has wrong types
	@Test
	void testInsertModelWithWrongModelType() {
		String requestBody = "{"
				+ "\"modelType\": 10,"
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": 10"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("missing param or type error"));
	}

	// Test if quantity update is correct
	@Test
	void testStockManagement() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": 5"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].result", equalTo("ok"));

		// Check the state of the stock
		given()
				.when().get("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].modelType", equalTo("SmallModel"))
				.body("[0].quantity", equalTo(20))
				.body("[0].color_pocket_name", equalTo("red"))
				.body("[0].color_bag_name", equalTo("blue"));
	}

	// Test on GET /stock

	// Test if model is shown correctly
	@Test
	void testGetStock() {
		given()
				.when().get("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].result", nullValue());
	}

	// Test on GET /stock/quantity

	// Test if returned quantity is correct
	@Test
	void testGetQuantity() {
		String requestBody = "{"
				+ "\"modelType\": \"small\","
				+ "\"color_pocket_name\": \"red\","
				+ "\"color_bag_name\": \"blue\","
				+ "\"quantity\": 5"
				+ "}";
		given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.when().post("/stock")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].result", equalTo("ok"));

		given()
				.queryParam("modelType", "smallModel")
				.queryParam("bagColor", "blue")
				.queryParam("pocketColor", "red")
				.when().get("/stock/quantity")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].quantity", equalTo(15));
	}

	@Test
	void testGetQuantityWithoutModel() {
		given()
				.queryParam("bagColor", "blue")
				.queryParam("pocketColor", "red")
				.when().get("/stock/quantity")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("empty params error"));
	}

	@Test
	void testGetQuantityWithoutBagName() {
		given()
				.queryParam("modelType", "smallModel")
				.queryParam("pocketColor", "red")
				.when().get("/stock/quantity")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("empty params error"));
	}

	@Test
	void testGetQuantityWithoutPocketName() {
		given()
				.queryParam("modelType", "smallModel")
				.queryParam("bagColor", "blue")
				.when().get("/stock/quantity")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("empty params error"));
	}

	@Test
	void testGetQuantityOfInexistingModel() {
		given()
				.queryParam("modelType", "fakeModel")
				.queryParam("bagColor", "blue")
				.queryParam("pocketColor", "blue")
				.when().get("/stock/quantity")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("[0].error", equalTo("invalid model"));
	}

}
