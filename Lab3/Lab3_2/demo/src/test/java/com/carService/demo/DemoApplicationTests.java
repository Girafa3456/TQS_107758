package com.carService.demo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CarManagerService carService;

    @InjectMocks
    private CarController carController;

    @Test
    void shouldReturnListOfCars() throws Exception {
        Car car = new Car(1L, "Model S", "Tesla", "Sedan")
        List<Car> cars = List.of(car);
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Tesla"));
    }
}
