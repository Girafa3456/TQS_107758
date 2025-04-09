package fullstack.homeWork.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/worker")
@RequiredArgsConstructor
@Slf4j
public class WorkerController {
    private final BookingService bookingService;

    @GetMapping("/check-in")
    public String showCheckInForm(Model model) {
        log.info("Displaying check-in form");
        List<Reservation> reservations = bookingService.getAllReservations()
            .stream()
            .filter(r -> !r.isUsed())
            .collect(Collectors.toList());
        model.addAttribute("reservations", reservations);
        return "worker-checkin";
    }


    @PostMapping("/verify/{token}")
    public String verifyReservation(@PathVariable String token, Model model) {
        log.info("Attempting to verify reservation with token: {}", token);
        try {
            bookingService.verifyReservation(token);
            model.addAttribute("message", "Reservation verified successfully!");
            log.info("Successfully verified reservation with token: {}", token);
        } catch (Exception e) {
            log.error("Failed to verify reservation with token {}: {}", token, e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/worker/check-in";
    }
    @GetMapping("/reservations/{restaurantId}")
    public String viewRestaurantReservations(@PathVariable Long restaurantId, Model model) {
        log.info("Loading reservations for restaurant with id: {}", restaurantId);
        try {
            model.addAttribute("reservations", bookingService.getAllReservationsByRestaurant(restaurantId));
            log.debug("Loaded reservations for restaurant {}", restaurantId);
            return "worker-reservations";
        } catch (Exception e) {
            log.error("Error loading reservations for restaurant {}: {}", restaurantId, e.getMessage(), e);
            throw e;
        }
    }
}