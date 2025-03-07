package com.carService.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@WebMvcTest(CarControllerImpl.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carService;

    @Test
    void shouldReturnListOfCars() throws Exception {
        Car car = new Car(1L, "Model S", "Tesla");
        List<Car> cars = List.of(car);
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Model S"))
                .andExpect(jsonPath("$[0].maker").value("Tesla"));
    }

    @Test
    void createCarTest() throws Exception {
        Car car = new Car(1L, "Model X", "Tesla");


        when(carService.save(any(Car.class))).thenReturn(car); 
    
        mockMvc.perform(post("/cars")  
        .contentType("application/json")  
        .content("{\"model\": \"Model X\", \"maker\": \"Tesla\"}")) 
        .andExpect(status().isCreated()) 
        .andExpect(jsonPath("$.model").value("Model X"))  
        .andExpect(jsonPath("$.maker").value("Tesla")); 
    }

    @Test
    void getCarByIdTest() throws Exception {
        Car car = new Car(1L, "Model X", "Tesla");

        when(carService.getCarDetails(1L)).thenReturn(Optional.of(car)); 
    
        mockMvc.perform(get("/cars/1")) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$.model").value("Model X"))
                .andExpect(jsonPath("$.maker").value("Tesla"));
    
    }
}
