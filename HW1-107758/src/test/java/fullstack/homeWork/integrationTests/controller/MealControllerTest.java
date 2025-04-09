package fullstack.homeWork.integrationTests.controller;

import fullstack.homeWork.controller.MealController;
import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MealController.class)
public class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MealRepository mealRepo;

    @MockBean
    private RestaurantRepository restaurantRepo;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        Meal meal1 = new Meal("Pasta", "Delicious pasta", LocalDate.now(), restaurant, 12.5);
        Meal meal2 = new Meal("Pizza", "Tasty pizza", LocalDate.now().plusDays(2), restaurant, 15.0);
        Meal meal3 = new Meal("Salad", "Fresh salad", LocalDate.now().plusDays(5), restaurant, 8.0);

        when(mealRepo.findByRestaurantId(1L)).thenReturn(Arrays.asList(meal1, meal2, meal3));
        when(mealRepo.findByDateBetweenAndRestaurantId(LocalDate.now(), LocalDate.now().plusDays(7), 1L))
            .thenReturn(Arrays.asList(meal1, meal2));
    }

    @Test
    void testGetMealsByRestaurant() throws Exception {
        mockMvc.perform(get("/api/meals/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Pasta"))
                .andExpect(jsonPath("$[1].name").value("Pizza"))
                .andExpect(jsonPath("$[2].name").value("Salad"));
        
        verify(mealRepo, times(1)).findByRestaurantId(1L);
    }

    @Test
    void testGetUpcomingMeals() throws Exception {
        mockMvc.perform(get("/api/meals/restaurant/1/upcoming"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pasta"))
                .andExpect(jsonPath("$[1].name").value("Pizza"));

        verify(mealRepo, times(1)).findByDateBetweenAndRestaurantId(LocalDate.now(), LocalDate.now().plusDays(7), 1L);
    }
}
