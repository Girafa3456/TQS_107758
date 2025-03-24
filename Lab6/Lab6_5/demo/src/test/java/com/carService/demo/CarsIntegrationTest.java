package com.carService.demo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarsIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass")
        .withReuse(true);;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        
        while (!postgres.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while waiting for container to start", e);
            }
        }
        
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.flyway.enabled", () -> "true");
    }


    @Test
    void shouldGetAllCars() {
        given()
            .port(port)
        .when()
            .get("/api/cars")
        .then()
            .statusCode(200)
            .body("$.size()", greaterThanOrEqualTo(3))
            .body("[0].maker", equalTo("Tesla"))
            .body("[0].model", equalTo("Model S"))
            .body("[1].maker", equalTo("Toyota"))
            .body("[1].model", equalTo("Yaris"));
    }

    @Test
    void shouldCreateNewCar() {
        given()
            .port(port)
            .contentType("application/json")
            .body("{\"maker\": \"Ferrari\", \"model\": \"Roma\"}")
        .when()
            .post("/api/cars")
        .then()
            .statusCode(201)
            .body("maker", equalTo("Ferrari"))
            .body("model", equalTo("Roma"));
    }

    @Test
    void shouldGetCarById() {
        Integer carId = given()
            .port(port)
        .when()
            .get("/api/cars")
        .then()
            .extract()
            .path("[0].id");

        given()
            .port(port)
        .when()
            .get("/api/cars/" + carId)
        .then()
            .statusCode(200)
            .body("id", equalTo(carId))
            .body("maker", not(emptyString()))
            .body("model", not(emptyString()));
    }

    @Test
    void shouldReturn404ForUnknownCar() {
        given()
            .port(port)
        .when()
            .get("/api/cars/99999")
        .then()
            .statusCode(404);
    }
}