package com.carService.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CarControllerImpl implements CarController{
    private final CarManagerService service;

    public CarControllerImpl(CarManagerService service) {
        this.service = service;
    }

    @Override
    @GetMapping("/cars")
    public List<Car> getAllCars(){
        return service.getAllCars();
    }

    @Override
    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(car));
    }

    @Override
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id){
        Optional<Car> car = service.getCarDetails(id);
        
        if (car.isPresent()){
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

