package com.carService.demo;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CarControllerImpl.class)
public class CarControllerRestAssureTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carService;

    @Test
    public void shouldReturnListOfCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Model S", "Tesla"));
        cars.add(new Car("Yaris", "Toyota"));
        
        when(carService.getAllCars()).thenReturn(cars);

        RestAssuredMockMvc
            .given()
                .mockMvc(mockMvc)
            .when()
                .get("/api/cars")
            .then()
                .statusCode(200)
                .body("$.size()", equalTo(2))
                .body("[0].model", equalTo("Model S"))
                .body("[0].maker", equalTo("Tesla"))
                .body("[1].model", equalTo("Yaris"))
                .body("[1].maker", equalTo("Toyota"));
    }

    @Test
    public void createCarTest() {
        Car car = new Car("Model X", "Tesla");
        when(carService.save(any(Car.class))).thenReturn(car);

        RestAssuredMockMvc
            .given()
                .mockMvc(mockMvc)
                .contentType("application/json")
                .body("{\"model\": \"Model X\", \"maker\": \"Tesla\"}")
            .when()
                .post("/api/cars")
            .then()
                .statusCode(201)
                .body("model", equalTo("Model X"))
                .body("maker", equalTo("Tesla"));

        verify(carService, Mockito.times(1)).save(any(Car.class));
    }

    @Test
    public void getCarByIdTest() {
        Car car = new Car("Model X", "Tesla");
        when(carService.getCarDetails(1L)).thenReturn(Optional.of(car));

        RestAssuredMockMvc
            .given()
                .mockMvc(mockMvc)
            .when()
                .get("/api/cars/1")
            .then()
                .statusCode(200)
                .body("model", equalTo("Model X"))
                .body("maker", equalTo("Tesla"));
    }

    @Test
    public void getCarByIdNotFoundTest() {
        when(carService.getCarDetails(99L)).thenReturn(Optional.empty());

        RestAssuredMockMvc
            .given()
                .mockMvc(mockMvc)
            .when()
                .get("/api/cars/99")
            .then()
                .statusCode(404);
    }
}

