package fullstack.homeWork.repo;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import fullstack.homeWork.model.Meal;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByRestaurantId(Long restaurantId);
    List<Meal> findByDateBetweenAndRestaurantId(LocalDate start, LocalDate end, Long restaurantId);
}
