package fullstack.homeWork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fullstack.homeWork.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookingWebController {
    private final BookingService bookingService;

    @GetMapping("/confirm/{token}")
    public String showConfirmation(@PathVariable String token, Model model) {
        model.addAttribute("reservation", bookingService.checkReservation(token));
        log.info("Showing confirmation for token: {}", token);
        return "confirmation";
    }
}