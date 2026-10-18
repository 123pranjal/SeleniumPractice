package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetSingleUser() {
        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/users/2");

        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected status code 200 for a valid user");

        String email = response.jsonPath().getString("data.email");
        Assert.assertTrue(email.contains("@"), "Email field should look like a real email");

        System.out.println("Fetched user email: " + email);
    }

    @Test
    public void testGetUserNotFound() {
        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/users/999");

        Assert.assertEquals(response.getStatusCode(), 404,
                "Expected status code 404 for a non-existent user");
    }

    @Test
    public void testCreateUser() {
        String requestBody = "{ \"name\": \"pranjal\", \"job\": \"QA Engineer\" }";

        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users");

        Assert.assertEquals(response.getStatusCode(), 201,
                "Expected status code 201 when creating a new user");

        String createdName = response.jsonPath().getString("name");
        Assert.assertEquals(createdName, "pranjal", "Returned name should match what was sent");

        System.out.println("Created user response: " + response.asString());
    }
}