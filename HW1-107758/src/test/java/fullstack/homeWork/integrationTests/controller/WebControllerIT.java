package fullstack.homeWork.integrationTests.controller;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;
import fullstack.homeWork.services.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MealRepository mealRepository;

    @MockBean
    private WeatherService weatherService;

    private Restaurant restaurant;
    private Meal meal;

    @BeforeEach
    public void setup() {
        mealRepository.deleteAll();
        restaurantRepository.deleteAll();

        restaurant = new Restaurant("Mock Café", "Mocktown");
        restaurant = restaurantRepository.save(restaurant);

        meal = new Meal("Burger", "Tasty and juicy", LocalDate.now(), restaurant, 10.99);
        mealRepository.save(meal);

        Map<String, Object> weather = new HashMap<>();
        weather.put("weatherCode", 1);
        weather.put("tempMax", 22.0);
        weather.put("tempMin", 14.0);

        Mockito.when(weatherService.getWeatherForDate(Mockito.any(LocalDate.class)))
                .thenReturn(weather);
        Mockito.when(weatherService.getWeatherIcon(Mockito.anyInt()))
                .thenReturn("⛅");
    }

    @Test
    public void testIndex_ShouldReturnIndexViewWithRestaurants() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("restaurants"))
                .andExpect(content().string(containsString("Mock Café")));
    }

    @Test
    public void testRestaurantDetails_ShouldReturnRestaurantPageWithMealsAndWeather() throws Exception {
        mockMvc.perform(get("/restaurants/" + restaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurant"))
                .andExpect(model().attributeExists("restaurant"))
                .andExpect(model().attributeExists("dayInfo"))
                .andExpect(content().string(containsString("Burger")))
                .andExpect(content().string(containsString("⛅"))); // weather icon
    }
}
