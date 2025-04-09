package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;
import fullstack.homeWork.services.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebController {
    private final RestaurantRepository restaurantRepo;
    private final MealRepository mealRepo;
    private final WeatherService weatherService;

    @GetMapping("/")
    public String index(Model model) {
        log.info("Loading index page");
        try {
            List<Restaurant> restaurants = restaurantRepo.findAll();
            model.addAttribute("restaurants", restaurants);
            log.debug("Loaded {} restaurants for index page", restaurants.size());
            return "index";
        } catch (Exception e) {
            log.error("Error loading index page: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/restaurants/{id}")
    public String restaurantDetails(@PathVariable Long id, Model model) {
        log.info("Loading details for restaurant with id: {}", id);
        try {
            Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Restaurant not found with id: {}", id);
                    return new RuntimeException("Restaurant not found");
                });
            
            LocalDate today = LocalDate.now();
            log.debug("Loading meals for restaurant {} from {} to {}", id, today, today.plusDays(6));
            
            List<Meal> meals = mealRepo.findByDateBetweenAndRestaurantId(
                today, 
                today.plusDays(6), 
                id
            );
            
            Map<String, Map<String, Object>> dayInfo = new LinkedHashMap<>();
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
            
            for (int i = 0; i < 7; i++) {
                LocalDate date = today.plusDays(i);
                String dayName = date.format(dayFormatter);
                
                Map<String, Object> dayData = new HashMap<>();
                List<Meal> dailyMeals = meals.stream()
                    .filter(m -> m.getDate().equals(date))
                    .collect(Collectors.toList());
                
                dayData.put("meals", dailyMeals);
                log.debug("Found {} meals for {} at restaurant {}", dailyMeals.size(), dayName, id);
                
                // Get weather
                Map<String, Object> weather = weatherService.getWeatherForDate(date);
                if (weather != null) {
                    dayData.put("weather", weather);
                    dayData.put("weatherIcon", 
                        weatherService.getWeatherIcon((Integer) weather.get("weatherCode")));
                    log.debug("Added weather data for {}: {}", dayName, weather);
                }
                
                dayInfo.put(dayName, dayData);
            }
            
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("dayInfo", dayInfo);
            log.info("Successfully loaded details for restaurant {}", id);
            return "restaurant";
        } catch (Exception e) {
            log.error("Error loading restaurant details for id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}