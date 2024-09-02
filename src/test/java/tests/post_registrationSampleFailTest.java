package tests;

import baseURL.PetLeoBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;
import io.restassured.http.ContentType;

import java.util.UUID;

public class post_registrationSampleFailTest extends PetLeoBaseUrl {

    @Test
    public void postRegistrationTest01() {

        // Path params with baseUrl
        specPetLeo.pathParams("pp1", "v1", "pp2", "accounts");

        // Request body using test data and dynamic email

        JSONObject requestBody = new JSONObject();

        String email = "test_" + UUID.randomUUID() + "@petleo.net";

        requestBody.put("email",email);
        requestBody.put("password", "xxxxxx");
        requestBody.put("language","en");
        requestBody.put("country_code",JSONObject.NULL);

        // Send the request and save the response

        Response response = given()
                .spec(specPetLeo)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("/{pp1}/{pp2}/");

        response.prettyPrint();

        // Hard Assertion using JUnit

        JsonPath responseJsonPath  = response.jsonPath();

        Assert.assertEquals(requestBody.get("email"),responseJsonPath.get("email"));
        Assert.assertNull(responseJsonPath.get("country_code"));
        Assert.assertEquals(responseJsonPath.get("language"), requestBody.get("language"));

        // Assertion

        response.then().assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue());

        // Logging if registration successful or failed

        String responseEmail = response.jsonPath().getString("email");
        if (email.equals(responseEmail)) {
            System.out.println("User successfully registered with : " + responseEmail);
        } else {
            System.err.println("Registration failed.");
        }
    }
}

