package fullstack.homeWork.integrationTests.controller;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.ReservationRepository;
import fullstack.homeWork.repo.RestaurantRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingWebControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Reservation reservation;

    @BeforeEach
    public void setup() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();
        
        Restaurant restaurant = new Restaurant("ViewGrill", "GridCity");
        restaurant = restaurantRepository.save(restaurant);

        Meal meal = new Meal("Salmon", "Grilled delight", LocalDate.now(), restaurant, 15.0);
        meal = mealRepository.save(meal);

        reservation = new Reservation();
        reservation.setToken(UUID.randomUUID().toString());
        reservation.setStudentName("Jane Doe");
        reservation.setMeal(meal);
        reservation.setUsed(false);
        reservation.setReservationTime(LocalDateTime.now());

        reservation = reservationRepository.save(reservation);
    }

    @Test
    public void testShowConfirmation_ShouldReturnConfirmationPageWithReservation() throws Exception {
        mockMvc.perform(get("/confirm/" + reservation.getToken()))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmation"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(content().string(containsString("Jane Doe")));
    }
}
