package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class StatusTests {

    @Test
    void checkTotal5() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(5));
    }

    @Test
    void checkTotaWithResponsLogs() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    @Test
    void checkTotaWithLogs() {
        given()
                .log().all()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(5));
    }
}
