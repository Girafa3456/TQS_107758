package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.util.List;

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
        LocalDate nextWeek = today.plusDays(7);
        
        List<Meal> meals = mealRepo.findByDateBetweenAndRestaurantId(today, nextWeek, id);
        
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("meals", meals);
        return "restaurant";
    }
}