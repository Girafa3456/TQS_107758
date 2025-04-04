package com.carService.demo;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class CarManagerServiceTest {

    @Mock
    private CarRepository repo;

    @InjectMocks
    private CarManagerServiceImpl service;

    @BeforeEach
    void setUp(){
        reset(repo);
    }

    @Test
    void returnListOfCars(){
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Civic", "Honda"));
        cars.add(new Car("Yaris", "Toyota"));
        
        
        when(repo.findAll()).thenReturn(cars);

        List<Car> result = service.getAllCars();
        assertEquals(2, result.size());
        assertThat(result).isEqualTo(cars);
    }

    @Test
    void validCarWhenSaved() {
        Car car = new Car("Roma", "Ferrari");
    
        when(repo.save(car)).thenReturn(car);

        assertThat(service.save(car)).isEqualTo(car);
    }

    @Test
    void getCarDetailsReturnNull() {
        when(repo.findByCarId(1L)).thenReturn(null);

        assertThat(service.getCarDetails(1L)).isEqualTo(Optional.empty());
    }
    
    
    @Test
    void getCarDetailsReturnCar() {
        Car car = new Car("Taycan", "Porsche");

        when(repo.findByCarId(car.getId())).thenReturn(car);

        assertThat(service.getCarDetails(car.getId())).isEqualTo(Optional.of(car));
    }

}
