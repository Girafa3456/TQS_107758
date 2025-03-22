package tqs.flywayTest;

import org.springframework.boot.SpringApplication;

public class TestFlywayTestApplication {

	public static void main(String[] args) {
		SpringApplication.from(FlywayTestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
