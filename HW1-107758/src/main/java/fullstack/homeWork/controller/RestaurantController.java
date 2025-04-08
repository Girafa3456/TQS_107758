package fullstack.homeWork.controller;

import org.springframework.web.bind.annotation.*;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantRepository restaurantRepo;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        return restaurantRepo.findById(id).orElseThrow();
    }
}