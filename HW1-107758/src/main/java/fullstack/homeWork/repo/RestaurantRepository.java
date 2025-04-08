package fullstack.homeWork.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.homeWork.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByName(String name);
}