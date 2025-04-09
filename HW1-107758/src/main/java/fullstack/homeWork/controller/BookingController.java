package fullstack.homeWork.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.services.BookingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/book")
    public Reservation bookMeal(@RequestBody BookingRequest request) {
        Long mealId = request.getMealId();
        String studentName = request.getStudentName();
        log.info("Received booking request for meal {} from student {}", mealId, studentName);
        return bookingService.bookMeal(mealId, studentName);
    }

    @GetMapping("/{token}")
    public Reservation getReservation(@PathVariable String token) {
        return bookingService.checkReservation(token);
    }
}

@Data
class BookingRequest {
    private Long mealId;
    private String studentName;
}