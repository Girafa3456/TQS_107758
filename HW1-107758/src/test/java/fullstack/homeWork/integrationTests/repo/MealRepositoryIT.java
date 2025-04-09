package fullstack.homeWork.integrationTests.repo;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MealRepositoryIT {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant("Testaurant", "Test Location");
        restaurant = restaurantRepository.save(restaurant);
    }

    @Test
    void testFindByRestaurantId() {
        Meal meal1 = new Meal("Meal1", "Test Meal", LocalDate.now(), restaurant, 10.0);
        Meal meal2 = new Meal("Meal2", "Another Meal", LocalDate.now(), restaurant, 12.0);
        mealRepository.save(meal1);
        mealRepository.save(meal2);

        List<Meal> meals = mealRepository.findByRestaurantId(restaurant.getId());
        assertThat(meals).hasSize(2);
    }

    @Test
    void testFindByDateBetweenAndRestaurantId() {
        // Given: save meals with different dates
        LocalDate today = LocalDate.now();
        Meal meal1 = new Meal("Meal1", "Test Meal", today.minusDays(1), restaurant, 10.0);
        Meal meal2 = new Meal("Meal2", "Another Meal", today, restaurant, 12.0);
        Meal meal3 = new Meal("Meal3", "Extra Meal", today.plusDays(1), restaurant, 15.0);
        mealRepository.save(meal1);
        mealRepository.save(meal2);
        mealRepository.save(meal3);

        // When: querying meals between a date range
        List<Meal> meals = mealRepository.findByDateBetweenAndRestaurantId(today.minusDays(1), today, restaurant.getId());

        // Then: only meals within the range are returned (meal1 and meal2)
        assertThat(meals).hasSize(2);
    }
}
