package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final BookingService bookingService;

    @GetMapping("/check")
    public String showCheckForm() {
        return "check-reservation";
    }

    @PostMapping("/check")
    public String checkReservations(@RequestParam String studentName, Model model) {
        if (bookingService.getReservationsByStudent(studentName).isEmpty()) {
            log.error("Student name is empty");
        }
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