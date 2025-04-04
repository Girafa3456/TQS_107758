package com.carService.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


// annotation for testing JpaRepositorys
@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private CarRepository repo;

    @Autowired
    private TestEntityManager manager;

    @Test
    public void findByCarIdReturnCarTest() {
        Car car = new Car("Clio", "Renault");
        // save the car entity and flushes it to the db
        manager.persistAndFlush(car);

        assertThat(repo.findByCarId(car.getId()).getMaker()).isEqualTo(car.getMaker());
    } 

    @Test
    public void findByCarIdReturnNullTest() {
        Car null_car = repo.findByCarId(10L);

        assertThat(null_car).isNull();
    }

    @Test
    public void getAllCarsReturnAllCarsTest() {
        Car car1 = new Car("Clio", "Renault");
        Car car2 = new Car("Taycan", "Porsche");
        Car car3 = new Car("Roma", "Ferrari");

        manager.persist(car1);
        manager.persist(car2);
        manager.persist(car3);

        manager.flush();
    
        List<Car> cars = repo.findAll();

        assertThat(cars).hasSize(3);
        assertThat(cars).contains(car1, car2, car3);
    }
}
