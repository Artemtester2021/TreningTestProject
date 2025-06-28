package tests;


import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendedTests extends TestBase {

    @Test
    void successfulLoginBadPracticeTest() {
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
    void successfulLoginTest() {
//        String authData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";

        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = given()
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
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

}
