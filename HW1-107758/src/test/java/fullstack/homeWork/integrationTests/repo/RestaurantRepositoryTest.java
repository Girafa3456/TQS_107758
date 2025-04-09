package fullstack.homeWork.integrationTests.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import fullstack.homeWork.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import fullstack.homeWork.repo.RestaurantRepository;

@DataJpaTest
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void testFindByName() {
        // Given: a restaurant is saved
        Restaurant restaurant = new Restaurant("Testaurant", "Test Location");
        restaurantRepository.save(restaurant);

        // When: searching by name
        Optional<Restaurant> optional = restaurantRepository.findByName("Testaurant");

        // Then: the restaurant is found
        assertThat(optional).isPresent();
        assertThat(optional.get().getName()).isEqualTo("Testaurant");
    }
}
