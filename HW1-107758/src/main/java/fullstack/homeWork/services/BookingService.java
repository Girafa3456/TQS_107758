package fullstack.homeWork.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {
    private final MealRepository mealRepo;
    private final ReservationRepository reservationRepo;

    public Reservation bookMeal(Long mealId, String studentName) {
        log.info("Attempting to book meal {} for student {}", mealId, studentName);
        Meal meal = mealRepo.findById(mealId).orElseThrow(() -> {
            log.error("Meal not found with ID: {}", mealId);
            return new RuntimeException("Meal not found");
        });
        
        Reservation reservation = new Reservation();
        reservation.setToken(UUID.randomUUID().toString());
        reservation.setMeal(meal);
        reservation.setStudentName(studentName);
        reservation.setReservationTime(LocalDateTime.now());
        
        log.debug("Created reservation: {}", reservation);
        return reservationRepo.save(reservation);
    }

    public Reservation verifyReservation(String token) {
        log.info("Verifying reservation with token: {}", token);
        Reservation reservation = reservationRepo.findByToken(token)
            .orElseThrow(() -> {
                log.error("Reservation not found with token: {}", token);
                return new RuntimeException("Reservation not found");
            });
        reservation.setUsed(true);
        log.info("Reservation verified: {}", token);
        return reservationRepo.save(reservation);
    }
        
    public Reservation checkReservation(String token) {
        return reservationRepo.findByToken(token).orElseThrow();
    }

    public List<Reservation> getReservationsByStudent(String studentName) {
        return reservationRepo.findByStudentName(studentName);
    }

    public void cancelReservation(String token) {
        reservationRepo.deleteById(token);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }
    
    public List<Reservation> getAllReservationsByRestaurant(Long restaurantId) {
        return reservationRepo.findByMealRestaurantId(restaurantId);
    }

    
}
