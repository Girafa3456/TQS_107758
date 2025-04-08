package fullstack.homeWork.services;

import java.time.LocalDateTime;
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
}
