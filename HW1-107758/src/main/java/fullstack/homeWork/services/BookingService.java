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

@Service
@RequiredArgsConstructor
public class BookingService {
    private final MealRepository mealRepo;
    private final ReservationRepository reservationRepo;

    public Reservation bookMeal(Long mealId, String studentName) {
        Meal meal = mealRepo.findById(mealId).orElseThrow();
        
        Reservation reservation = new Reservation();
        reservation.setToken(UUID.randomUUID().toString());
        reservation.setMeal(meal);
        reservation.setStudentName(studentName);
        reservation.setReservationTime(LocalDateTime.now());
        
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

    public Reservation verifyReservation(String token) {
        Reservation reservation = reservationRepo.findByToken(token).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setUsed(true);
        return reservationRepo.save(reservation);
    }
}
