package com.carService.demo;

import java.util.List;
import java.util.Optional;

public interface CarManagerService {
    
    public Car save(Car car);

    public List<Car> getAllCars();

    public Optional<Car> getCarDetails(Long carId);
}
