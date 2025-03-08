package com.carService.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CarManagerServiceImpl implements CarManagerService{
    private final CarRepository repo;

    public CarManagerServiceImpl(CarRepository repo) {
        this.repo = repo;
    }

    public List<Car> getAllCars(){
        return repo.findAll();
    }

    public Optional<Car> getCarDetails(Long id) {
        return Optional.ofNullable(repo.findByCarId(id));
    }

    public Car save(Car car){
        return repo.save(car);
    }

}
