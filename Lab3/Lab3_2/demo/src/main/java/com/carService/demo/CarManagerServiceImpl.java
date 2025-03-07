package com.carService.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CarManagerServiceImpl implements CarManagerService{
    private final CarRepository repo;

    public CarManagerService(CarRepository repo) {
        this.repo = repo;
    }

    public List<Car> getAllCars()

}
