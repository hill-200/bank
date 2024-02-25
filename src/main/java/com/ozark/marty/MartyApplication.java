package com.ozark.marty;

import com.ozark.marty.authenticatication.AuthenticationService;
import com.ozark.marty.authenticatication.RegisterRequest;
import com.ozark.marty.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.ozark.marty.entities.Role.ADMIN;

@SpringBootApplication
public class MartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MartyApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	){
		return  args -> {

			var admin = RegisterRequest.builder()
					.firstName("Admin")
					.lastName("Admin")
					.email("admin@gmail.com")
					.password("password123")
					.role(ADMIN)
					.build();
			var token = service.register(admin);
			var accessToken = token.getAccessToken();
			var refreshToken = token.getRefreshToken();

			System.out.println("Admin access token is: " + accessToken);
			System.out.println("Admin refresh token is : " + refreshToken);
		};
	}
}
