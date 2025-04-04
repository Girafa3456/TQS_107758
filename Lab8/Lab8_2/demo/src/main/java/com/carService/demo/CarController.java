package com.carService.demo;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CarController {

    public ResponseEntity<Car> createCar(Car car);
    
    public List<Car> getAllCars();

    public ResponseEntity<Car> getCarById(Long id);
}
