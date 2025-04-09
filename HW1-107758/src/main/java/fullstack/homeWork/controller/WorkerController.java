package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import fullstack.homeWork.services.BookingService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {
    private final BookingService bookingService;

    @GetMapping("/check-in")
    public String showCheckInForm() {
        return "worker-checkin";
    }

    @PostMapping("/verify")
    public String verifyReservation(@RequestParam String token, Model model) {
        try {
            bookingService.verifyReservation(token);
            model.addAttribute("message", "Reservation verified successfully!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "worker-checkin";
    }

    @GetMapping("/reservations/{restaurantId}")
    public String viewRestaurantReservations(@PathVariable Long restaurantId, Model model) {
        model.addAttribute("reservations", bookingService.getAllReservationsByRestaurant(restaurantId));
        return "worker-reservations";
    }
}