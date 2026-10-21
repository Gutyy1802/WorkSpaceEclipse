package ceu.dam.ad.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ceu.dam.ad.user.services.DuplicateUserException;
import ceu.dam.ad.user.services.UserNotFoundException;
import ceu.dam.ad.user.services.UserUnauthorizedException;
import ceu.dam.ad.user.test.TestUserService;

@SpringBootApplication
public class UserServiceSpringApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(UserServiceSpringApplication.class, args);
		TestUserService test = context.getBean(TestUserService.class);
		try {
			test.test();
		} catch (DuplicateUserException | UserNotFoundException | UserUnauthorizedException e) {
			e.printStackTrace();
		}
	}

}
