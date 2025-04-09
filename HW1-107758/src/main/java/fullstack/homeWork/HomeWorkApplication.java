package fullstack.homeWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HomeWorkApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomeWorkApplication.class, args);
	}

}
