package com.carService.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(CarControllerImpl.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carService;

    @BeforeEach
    void setUp() throws Exception {}

    @Test
    void shouldReturnListOfCars() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Model S", "Tesla"));
        cars.add(new Car("Yaris", "Toyota"));
        
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Model S"))
                .andExpect(jsonPath("$[0].maker").value("Tesla"))
                .andExpect(jsonPath("$[1].model").value("Yaris"))
                .andExpect(jsonPath("$[1].maker").value("Toyota"));
            }

    @Test
    void createCarTest() throws Exception {
        Car car = new Car("Model X", "Tesla");


        when(carService.save(any(Car.class))).thenReturn(car); 
    
        mockMvc.perform(post("/api/cars")  
        .contentType("application/json")  
        .content("{\"model\": \"Model X\", \"maker\": \"Tesla\"}")) 
        .andExpect(status().isCreated()) 
        .andExpect(jsonPath("$.model").value("Model X"))  
        .andExpect(jsonPath("$.maker").value("Tesla")); 
    
        verify(carService, Mockito.times(1)).save(Mockito.any(Car.class));
    }

    @Test
    void getCarByIdTest() throws Exception {
        Car car = new Car("Model X", "Tesla");

        when(carService.getCarDetails(1L)).thenReturn(Optional.of(car)); 
    
        mockMvc.perform(get("/api/cars/1")) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.model").value("Model X"))
                .andExpect(jsonPath("$.maker").value("Tesla"));
    
    }
}
