package fullstack.homeWork.integrationTests.controller;

import fullstack.homeWork.controller.RestaurantController;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantRepository restaurantRepo;

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private List<Restaurant> restaurantList;

    @BeforeEach
    void setUp() {
        restaurant1 = new Restaurant(1L, "Pizza Palace", "Rome", null);
        restaurant2 = new Restaurant(2L, "Sushi Spot", "Tokyo", null);
        restaurantList = Arrays.asList(restaurant1, restaurant2);
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        when(restaurantRepo.findAll()).thenReturn(restaurantList);

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pizza Palace"))
                .andExpect(jsonPath("$[1].name").value("Sushi Spot"));

        verify(restaurantRepo).findAll();
    }

    @Test
    void testGetRestaurantById() throws Exception {
        when(restaurantRepo.findById(1L)).thenReturn(Optional.of(restaurant1));

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pizza Palace"));

        verify(restaurantRepo).findById(1L);
    }

    @Test
    void testGetRestaurantById_NotFound() throws Exception {
        when(restaurantRepo.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/restaurants/99"))
                .andExpect(status().isNotFound());
    }
}
