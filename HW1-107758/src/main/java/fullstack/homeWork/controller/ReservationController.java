package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.services.BookingService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final BookingService bookingService;

    @GetMapping("/check")
    public String showCheckForm() {
        return "check-reservation";
    }

    @PostMapping("/check")
    public String checkReservations(@RequestParam String studentName, Model model) {
        List<Reservation> reservations = bookingService.getReservationsByStudent(studentName);
        model.addAttribute("reservations", reservations);
        model.addAttribute("studentName", studentName);
        return "reservation-list";
    }

    @PostMapping("/cancel/{token}")
    public String cancelReservation(@PathVariable String token, @RequestParam String studentName) {
        bookingService.cancelReservation(token);
        return "redirect:/reservations/check?studentName=" + studentName;
    }
}