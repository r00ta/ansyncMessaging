package org.redhat.developer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class RestHanlderTest {

    @Test
    void test() {
        given().contentType(ContentType.JSON)
                .body("{\"executionId\" : \"test\"}")
                .when()
                .post("/test")
                .then()
                .body(containsString("test"));
    }
}
