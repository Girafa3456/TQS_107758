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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private RestaurantRepository restaurantRepository;

    
    private Meal meal;

    @BeforeEach
    void setup() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();

        Restaurant restaurant = new Restaurant("Testaurant", "Testville");
        restaurant = restaurantRepository.save(restaurant);

        meal = new Meal("Burger", "Beef burger", LocalDate.now(), restaurant, 8.99);
        meal = mealRepository.save(meal);

        Reservation r = new Reservation("test-token", "Alice", LocalDateTime.now(), false, meal);
        reservationRepository.save(r);
    }

    @Test
    void testCheckReservationByStudentName() throws Exception {
        mockMvc.perform(post("/reservations/check")
                .param("studentName", "Alice")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reservations"))
                .andExpect(model().attribute("studentName", "Alice"));
    }

    @Test
    void testCancelReservation() throws Exception {
        mockMvc.perform(post("/reservations/cancel/test-token")
                .param("studentName", "Alice"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations/check?studentName=Alice"));

        assertFalse(reservationRepository.findByToken("test-token").isPresent());
    }
}
