package fullstack.homeWork;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import fullstack.homeWork.model.Meal;
import fullstack.homeWork.model.Restaurant;
import fullstack.homeWork.repo.MealRepository;
import fullstack.homeWork.repo.RestaurantRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {
    private final RestaurantRepository restaurantRepo;
    private final MealRepository mealRepo;
    private final Random random = new Random();

    private static final Map<String, String> RESTAURANT_MAPPING = new HashMap<>();
    static {
        RESTAURANT_MAPPING.put("Restaurante Universitário de Santiago", "Main Cafeteria");
        RESTAURANT_MAPPING.put("Snack-Bar do Departamento de Economia", "Economics Dept. Snack Bar");
        RESTAURANT_MAPPING.put("Snack-Bar do Departamento de Matemática", "Math Dept. Snack Bar");
    }

    private static final Map<String, PriceRange> MEAL_PRICE_RANGES = new HashMap<>();
    static {
        MEAL_PRICE_RANGES.put("Hambúrguer", new PriceRange(3.50, 5.00));
        MEAL_PRICE_RANGES.put("Pizza", new PriceRange(4.00, 6.50));
        MEAL_PRICE_RANGES.put("Saladas", new PriceRange(2.50, 4.00));
        MEAL_PRICE_RANGES.put("Fruta", new PriceRange(0.50, 1.50));
        MEAL_PRICE_RANGES.put("Sobremesas", new PriceRange(1.00, 2.50));
        MEAL_PRICE_RANGES.put("Bebidas", new PriceRange(0.80, 2.00));
        MEAL_PRICE_RANGES.put("Buffet", new PriceRange(5.00, 8.00));
    }

    public DataLoader(RestaurantRepository restaurantRepo, MealRepository mealRepo) {
        this.restaurantRepo = restaurantRepo;
        this.mealRepo = mealRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Restaurant> restaurants = new HashMap<>();
        for (String name : RESTAURANT_MAPPING.values()) {
            Restaurant restaurant = restaurantRepo.findByName(name)
                .orElseGet(() -> {
                    Restaurant newRest = new Restaurant(name, "University of Aveiro");
                    return restaurantRepo.save(newRest);
                });
            restaurants.put(name, restaurant);
        }
        createMealsFromProvidedData(restaurants);
    }

    private void createMealsFromProvidedData(Map<String, Restaurant> restaurants) {
        List<String> mainDishes = Arrays.asList(
            "Hambúrguer de carne",
            "Hambúrguer de frango",
            "Hambúrguer vegetariano",
            "Pizza margherita",
            "Pizza pepperoni",
            "Pizza vegetariana",
            "Frango assado",
            "Bacalhau à brás",
            "Francesinha",
            "Lasanha de carne",
            "Lasanha vegetariana",
            "Arroz de pato",
            "Feijoada",
            "Bife à portuguesa"
        );
    
        List<String> desserts = Arrays.asList(
            "Pudim flan",
            "Mousse de chocolate",
            "Tarte de maçã",
            "Bolo de bolacha",
            "Gelado de baunilha",
            "Gelado de chocolate",
            "Fruta da época",
            "Iogurte natural",
            "Arroz doce",
            "Leite creme"
        );
    
        List<String> saladVegetables = Arrays.asList(
            "Alface",
            "Tomate",
            "Cenoura ralada",
            "Beterraba",
            "Pepino",
            "Cebola roxa",
            "Rúcula",
            "Espinafre",
            "Milho",
            "Azeitonas"
        );
    
        LocalDate today = LocalDate.now();
        Random random = new Random();
    
        for (Restaurant restaurant : restaurants.values()) {
            if (restaurant == null) continue;

            for (int i = 0; i < 7; i++) {
                LocalDate date = today.plusDays(i);

                String mainDish = mainDishes.get(random.nextInt(mainDishes.size()));
                

                String dessert1 = desserts.get(random.nextInt(desserts.size()));
                String dessert2;
                do {
                    dessert2 = desserts.get(random.nextInt(desserts.size()));
                } while (dessert2.equals(dessert1));

                Collections.shuffle(saladVegetables);
                List<String> selectedVegetables = saladVegetables.subList(0, 4);
                String saladDescription = String.join(", ", selectedVegetables);

                String mealDescription = String.format(
                    "\nSalads: %s\n\nDesserts: %s, %s",
                    saladDescription,
                    dessert1,
                    dessert2
                );

                double price = determinePrice(mainDish, mealDescription);
                Meal meal = new Meal(
                    String.format("%s: %s", date, mainDish), 
                    mealDescription,                         
                    date,                                    
                    restaurant,                              
                    price                                    
                );
                mealRepo.save(meal);
            }
        }
    }


    private double determinePrice(String mealName, String mealDescription) {
        for (Map.Entry<String, PriceRange> entry : MEAL_PRICE_RANGES.entrySet()) {
            if (mealName.toLowerCase().contains(entry.getKey().toLowerCase())) {
                return generateRandomPrice(entry.getValue());
            }
        }
        return generateRandomPrice(new PriceRange(2.80, 5.00));
    }

    private double generateRandomPrice(PriceRange range) {
        return Math.round((range.min + (range.max - range.min) * random.nextDouble()) * 100.0) / 100.0;
    }

    private static class PriceRange {
        final double min;
        final double max;

        PriceRange(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }
}