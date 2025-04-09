package fullstack.homeWork.integrationTests.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.ReservationRepository;
import fullstack.homeWork.repo.RestaurantRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;
    private Meal meal;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant("Testaurant", "Test Location");
        restaurant = restaurantRepository.save(restaurant);

        meal = new Meal("Test Meal", "Delicious Meal", LocalDate.now(), restaurant, 11.0);
        meal = mealRepository.save(meal);
    }

    @Test
    void testFindByToken() {
        // Given: a reservation with a token
        Reservation reservation = new Reservation("token123", "John Doe", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation);

        // When: retrieving by token
        Optional<Reservation> found = reservationRepository.findByToken("token123");

        // Then: reservation is retrieved correctly
        assertThat(found).isPresent();
        assertThat(found.get().getStudentName()).isEqualTo("John Doe");
    }

    @Test
    void testFindByStudentName() {
        // Given: two reservations for the same student
        Reservation reservation1 = new Reservation("token1", "Alice", LocalDateTime.now(), false, meal);
        Reservation reservation2 = new Reservation("token2", "Alice", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        // When: retrieving reservations by student name
        List<Reservation> reservations = reservationRepository.findByStudentName("Alice");

        // Then: both reservations are returned
        assertThat(reservations).hasSize(2);
    }

    @Test
    void testFindByMealRestaurantId() {
        // Given: a reservation for a meal that belongs to a restaurant
        Reservation reservation = new Reservation("token3", "Bob", LocalDateTime.now(), false, meal);
        reservationRepository.save(reservation);

        // When: retrieving reservations by meal's restaurant id
        List<Reservation> reservations = reservationRepository.findByMealRestaurantId(restaurant.getId());

        // Then: the reservation is found
        assertThat(reservations).hasSize(1);
    }
}
