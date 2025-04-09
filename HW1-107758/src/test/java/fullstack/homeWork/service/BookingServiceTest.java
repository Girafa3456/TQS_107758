package fullstack.homeWork.service;

import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Reservation;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.ReservationRepository;
import fullstack.homeWork.services.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock private MealRepository mealRepo;
    @Mock private ReservationRepository reservationRepo;

    @InjectMocks private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookMeal_ShouldCreateReservation_WhenMealExists() {
        Meal meal = new Meal("Pizza", "Cheesy", LocalDate.now(), null, 7.5);
        meal.setId(1L);

        when(mealRepo.findById(1L)).thenReturn(Optional.of(meal));
        when(reservationRepo.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation res = bookingService.bookMeal(1L, "John Doe");

        assertNotNull(res.getToken());
        assertEquals("John Doe", res.getStudentName());
        assertEquals(meal, res.getMeal());
        verify(reservationRepo).save(any(Reservation.class));
    }

    @Test
    void bookMeal_ShouldThrowException_WhenMealNotFound() {
        when(mealRepo.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookingService.bookMeal(2L, "Jane"));
        assertEquals("Meal not found", ex.getMessage());
    }

    @Test
    void verifyReservation_ShouldMarkUsed_WhenValidToken() {
        Reservation reservation = new Reservation("token123", "Alice", LocalDateTime.now(), false, null);
        when(reservationRepo.findByToken("token123")).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        Reservation verified = bookingService.verifyReservation("token123");

        assertTrue(verified.isUsed());
        verify(reservationRepo).save(reservation);
    }

    @Test
    void verifyReservation_ShouldThrow_WhenTokenInvalid() {
        when(reservationRepo.findByToken("fake")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookingService.verifyReservation("fake"));
    }

    @Test
    void cancelReservation_ShouldDeleteById() {
        bookingService.cancelReservation("tok123");
        verify(reservationRepo).deleteById("tok123");
    }

    @Test
    void getReservationsByStudent_ShouldReturnList() {
        List<Reservation> mockList = List.of(new Reservation("t1", "Bob", LocalDateTime.now(), false, null));
        when(reservationRepo.findByStudentName("Bob")).thenReturn(mockList);

        List<Reservation> result = bookingService.getReservationsByStudent("Bob");

        assertEquals(1, result.size());
    }

    @Test
    void getAllReservationsByRestaurant_ShouldQueryCorrectly() {
        when(reservationRepo.findByMealRestaurantId(10L)).thenReturn(List.of(new Reservation()));
        List<Reservation> result = bookingService.getAllReservationsByRestaurant(10L);
        assertEquals(1, result.size());
    }
}
