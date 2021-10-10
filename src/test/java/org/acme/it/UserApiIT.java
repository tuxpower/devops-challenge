package org.acme.it;

import org.acme.api.UserApi;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.containsString;

import java.time.LocalDate;

/**
 * Test class for {@link UserApi}.
 */
@QuarkusTest
@QuarkusTestResource(DatasourceResource.class)
@DBRider
public class UserApiIT {
    
    @Test
    @ExpectedDataSet(value = "expected/user.yml")
    void shouldReturnSuccessWhenPutUser() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"dateOfBirth\": \"1978-09-02\"}")
                .put("/hello/johndoe")
            .then()
                .statusCode(204);
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenPutUserInvalid() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"dateOfBirth\": \"1978-09-02\"}")
                .put("/hello/johndoe1")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Username must contain only letters"));
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenBirthdayAfterToday() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"dateOfBirth\": " + "\"" + LocalDate.now().plusYears(1) + "\"}")
                .put("/hello/johndoe")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Date of birth not valid."));
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenBirthdayIsNull() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .put("/hello/johndoe")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("must not be null"));
    }
    
    @Test
    @DataSet(value = "dataset/user.yml", cleanBefore = true)
    void shouldReturnSuccessWhenGetUser() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .get("/hello/johndoe")
            .then()
                .statusCode(200)
                .body(
                        startsWith("{\"message\": \"Hello, johndoe!"),
                        containsString("birthday"));
            
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenGetUserInvalid() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .get("/hello/johndoe1")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Username must contain only letters"));
    }
    
    @Test
    @DataSet(value = "dataset/empty.yml", cleanBefore = true)
    void shouldReturnNotFoundWhenGetUserInexistent() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .get("/hello/inexistentuser")
            .then()
                .statusCode(404);
    }
}
