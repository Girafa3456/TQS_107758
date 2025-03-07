package com.carService.demo;

import java.util.List;

public interface CarRepository {
    public List<Car> findAll();

    public Car findByCarId(Long carId);
}
