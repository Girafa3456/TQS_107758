package com.carService.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarControllerImpl implements CarController{
    private final CarManagerService service;

    public CarControllerImpl(CarManagerService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Car> getAllCars(){
        return service.getAllCars();
    }

    @Override
    public ResponseEntity<Car> createCar(Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(car));
    }

    @Override
    public ResponseEntity<Car> getCarById(Long id){
        Optional<Car> car = service.getCarDetails(id);
        
        if (car.isPresent()){
            return ResponseEntity.ok(car.get());
        }
        return ResponseEntity.notFound().build();
    }
}

