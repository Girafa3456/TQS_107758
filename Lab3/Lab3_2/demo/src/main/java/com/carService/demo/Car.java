package com.carService.demo;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    private String model;
    private String maker;

    // Constructors, getters, and setters
    public Car(String model, String maker) {
        this.model = model;
        this.maker = maker;
    }

    public Long getId(){
        return this.carId;
    }

    public String getMaker() {
        return this.maker;
    }

    public String getModel() {
        return this.model;
    }

    public void setId(Long carId){
        this.carId = carId;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString(){
        return "Car " + getId() + ":\n" + "Maker: " + getMaker() + "\nModel: " + getModel();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Car car = (Car) obj;
        return carId != null && carId.equals(car.carId);
    }
}
