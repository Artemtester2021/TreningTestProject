package tests;


import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.lombok.MissingPasswordModel;
import models.pojo.LoginBodyModel;
import models.pojo.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class LoginExtendedTests extends TestBase {

    @Test
    void successfulLoginBadPracticeTest() {
        String authData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";

        given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulLoginPojoTest() {

        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }


    @Test
    void successfulLoginLombokTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }


    @Test
    void successfulLoginAllureTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .filter(new AllureRestAssured())
                .log().uri()
                .log().body()
                .log().headers()
                .body(authData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginCustomAllureTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .body(authData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithStepsTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make request", () ->
                given()
                        .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                        .filter(withCustomTemplates())
                        .log().uri()
                        .log().body()
                        .log().headers()
                        .body(authData)
                        .contentType(JSON)
                        .when()
                        .post("https://reqres.in/api/login")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void successfulLoginWithWithSpecsTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(LoginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void missingPasswordTest() {

        LoginBodyLombokModel authData = new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");

        MissingPasswordModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .header(FREE_API_KEY_NAME, FREE_API_KEY_VALUE)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(missingPasswordRespons)
                        .extract().as(MissingPasswordModel.class));

        step("Check response", () ->
                assertEquals("Missing password", response.getError()));
    }
}
