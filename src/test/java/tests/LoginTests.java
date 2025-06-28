package tests;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class LoginTests extends TestBase {

    @Test
    void successfulLoginTest() {
        String authData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";

        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void missingPasswordTest() {
        String authData = "{\"email\":\"eve.holt@reqres.in\"}";

        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingLoginTest() {
        String authData = "{\"password\":\"cityslicka\"}";

        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void userNotFoundTest() {
        String authData = "{\"email\":\"eve.holt@hgmghmreqres.in\",\"password\":\"cityslicka\"}";

        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("user not found"));
    }

    @Test
    void unsuccessfulLogin400Test() {
        String authData = "";

        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .log().uri()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void unsuccessfulLogin415Test() {
        given()
                .log().uri()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }
}
