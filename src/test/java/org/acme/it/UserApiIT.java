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
import static org.junit.Assert.assertEquals;

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
    void shouldReturnSuccessWhenPostUser() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"johndoe\",\n \"dateOfBirth\": \"1978-09-02\"}")
                .post("/users")
            .then()
                .statusCode(201);
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenUsernameInvalid() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"johndoe1\",\n \"dateOfBirth\": \"1978-09-02\"}")
                .post("/users")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Username must contain only letters."));
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenBirthdayAfterToday() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"johndoe\",\n \"dateOfBirth\": " + "\"" + LocalDate.now().plusYears(1) + "\"}")
                .post("/users")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Date of birth not valid."));
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenUsernameIsNull() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"dateOfBirth\": \"1978-09-02\"}")
                .post("/users")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Username must contain only letters."));
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenBirthdayIsNull() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"johndoe\"}")
                .post("/users")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Date of birth not valid."));
    }
    
    @Test
    @DataSet(value = "dataset/user.yml", cleanBefore = true)
    void shouldReturnSuccessWhenGetUser() {
        String body = given()
            .when()
                .contentType(ContentType.JSON)
                .get("/users/johndoe")
            .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        
        assertEquals("{\"username\":\"johndoe\",\"dateOfBirth\":\"1978-09-02\"}", body);
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenGetUserInvalid() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .get("/users/johndoe1")
            .then()
                .statusCode(400)
                .body("parameterViolations[0].message", is("Username must contain only letters"));
    }
    
    @Test
    @DataSet(cleanBefore = true)
    void shouldConvertConstraintViolationInBadRequestWhenGetCheckBirthday() {
        given()
            .when()
                .contentType(ContentType.JSON)
                .get("/users/johndoe1/birthday")
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
                .get("/users/inexistentuser")
            .then()
                .statusCode(404);
    }
}
