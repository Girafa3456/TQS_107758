package fullstack.homeWork.controller;

import org.springframework.web.bind.annotation.*;
import fullstack.homeWork.model.Meal;
import fullstack.homeWork.repo.MealRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
@Slf4j
public class MealController {
    private final MealRepository mealRepo;

    @GetMapping("/restaurant/{restaurantId}")
    public List<Meal> getMealsByRestaurant(@PathVariable Long restaurantId) {
        log.info("Fetching meals for restaurant ID: {}", restaurantId);
        return mealRepo.findByRestaurantId(restaurantId);
    }

    @GetMapping("/restaurant/{restaurantId}/upcoming")
    public List<Meal> getUpcomingMeals(@PathVariable Long restaurantId) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        log.info("Fetching upcoming meals for restaurant ID: {} from {} to {}", restaurantId, today, nextWeek);
        return mealRepo.findByDateBetweenAndRestaurantId(today, nextWeek, restaurantId);
    }
}