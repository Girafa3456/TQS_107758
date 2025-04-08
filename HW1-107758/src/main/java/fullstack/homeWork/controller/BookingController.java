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

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/book")
    public Reservation bookMeal(@RequestBody BookingRequest request) {
        return bookingService.bookMeal(request.getMealId(), request.getStudentName());
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