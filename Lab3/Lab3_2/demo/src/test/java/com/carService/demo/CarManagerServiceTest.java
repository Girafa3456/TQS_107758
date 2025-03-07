package com.carService.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@WebMvcTest(CarManagerServiceImpl.class)
class CarManagerServiceTest {

    @Mock
    private CarRepository repo;

    @MockBean
    private CarManagerService service;

    @Test
    void returnListOfCars(){
        Car car = new Car(1L, "Civic", "Honda");
        List<Car> cars = List.of(car);
        when(repo.findAll()).thenReturn(cars);

        List<Car> result = service.getAllCars();
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getMaker());
    }

}
