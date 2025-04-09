package fullstack.homeWork.integrationTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import fullstack.homeWork.controller.BookingController;
import fullstack.homeWork.model.BookingRequest;
import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Meal testMeal;
    private Reservation testReservation;

    @BeforeEach
    void setUp() {
        testMeal = new Meal("Pasta", "Delicious pasta", LocalDate.now(), null, 8.99);
        testMeal.setId(1L);

        testReservation = new Reservation("abc123", "Alice", LocalDateTime.now(), false, testMeal);
    }

    @Test
    void testBookMeal() throws Exception {
        BookingRequest request = new BookingRequest();
        request.setMealId(1L);
        request.setStudentName("Alice");

        when(bookingService.bookMeal(1L, "Alice")).thenReturn(testReservation);

        mockMvc.perform(post("/api/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("abc123"))
                .andExpect(jsonPath("$.studentName").value("Alice"))
                .andExpect(jsonPath("$.meal.name").value("Pasta"));

        verify(bookingService).bookMeal(1L, "Alice");
    }

    @Test
    void testGetReservationByToken() throws Exception {
        when(bookingService.checkReservation("abc123")).thenReturn(testReservation);

        mockMvc.perform(get("/api/bookings/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("abc123"))
                .andExpect(jsonPath("$.studentName").value("Alice"))
                .andExpect(jsonPath("$.meal.name").value("Pasta"));

        verify(bookingService).checkReservation("abc123");
    }
}
