package fullstack.homeWork.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fullstack.homeWork.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Optional<Reservation> findByToken(String token);
    List<Reservation> findByStudentName(String studentName);
    List<Reservation> findByMealRestaurantId(Long restaurantId);
}
