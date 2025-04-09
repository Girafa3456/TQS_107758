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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation reservation;
    private Meal meal;
    private Restaurant restaurant;

    @BeforeEach
    public void setup() {
        reservationRepository.deleteAll();
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();

        restaurant = new Restaurant("Testaurant", "Testville");
        restaurant = restaurantRepository.save(restaurant);

        meal = new Meal("Pizza", "Cheesy slice", LocalDate.now(), restaurant, 9.99);
        meal = mealRepository.save(meal);

        reservation = new Reservation();
        reservation.setToken(UUID.randomUUID().toString());
        reservation.setMeal(meal);
        reservation.setStudentName("John Doe");
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setUsed(false);

        reservation = reservationRepository.save(reservation);
    }

    @Test
    public void testShowCheckInForm_ShouldReturnHtmlWithReservationList() throws Exception {
        mockMvc.perform(get("/worker/check-in"))
                .andExpect(status().isOk())
                .andExpect(view().name("worker-checkin"))
                .andExpect(model().attributeExists("reservations"))
                .andExpect(content().string(containsString("John Doe")));
    }

    @Test
    public void testVerifyReservation_ShouldRedirectToCheckIn() throws Exception {
        mockMvc.perform(post("/worker/verify/" + reservation.getToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/worker/check-in"));

        Reservation updated = reservationRepository.findByToken(reservation.getToken()).orElseThrow();
        assert updated.isUsed();
    }

    @Test
    public void testViewRestaurantReservations_ShouldReturnHtmlWithReservations() throws Exception {
        mockMvc.perform(get("/worker/reservations/" + restaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("worker-reservations"))
                .andExpect(model().attributeExists("reservations"))
                .andExpect(content().string(containsString("John Doe")));
    }
}
