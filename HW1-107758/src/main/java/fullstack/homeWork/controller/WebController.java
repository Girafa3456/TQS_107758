package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final RestaurantRepository restaurantRepo;
    private final MealRepository mealRepo;

    @GetMapping("/")
    public String index(Model model) {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        model.addAttribute("restaurants", restaurants);
        return "index";
    }

    @GetMapping("/restaurants/{id}")
    public String restaurantDetails(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantRepo.findById(id).orElseThrow();
        LocalDate today = LocalDate.now();

        List<Meal> meals = mealRepo.findByDateBetweenAndRestaurantId(
            today, 
            today.plusDays(6), 
            id
        );
        
        Map<String, List<Meal>> mealsByDay = new LinkedHashMap<>();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
        
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            String dayName = date.format(dayFormatter);
            mealsByDay.put(dayName, new ArrayList<>());
        }
        
        for (Meal meal : meals) {
            String dayName = meal.getDate().format(dayFormatter);
            mealsByDay.get(dayName).add(meal);
        }
        
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("mealsByDay", mealsByDay);
        return "restaurant";
    }
}