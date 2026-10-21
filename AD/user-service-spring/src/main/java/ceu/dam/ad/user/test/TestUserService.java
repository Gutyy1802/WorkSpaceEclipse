package ceu.dam.ad.user.test;

import org.springframework.stereotype.Component;

import ceu.dam.ad.user.model.User;
import ceu.dam.ad.user.services.DuplicateUserException;
import ceu.dam.ad.user.services.UserException;
import ceu.dam.ad.user.services.UserNotFoundException;
import ceu.dam.ad.user.services.UserService;
import ceu.dam.ad.user.services.UserUnauthorizedException;

@Component
public class TestUserService {
	
	final UserService service;
	
	TestUserService(UserService service) {
		this.service = service;
	}

    public void test() throws DuplicateUserException, UserNotFoundException, UserUnauthorizedException {


        try {
            // =========================
            // 1. CREAR USUARIO CORRECTO
            // =========================
            User user = new User();
            user.setUsername("juan123");
            user.setEmail("juan@test.com");
            user.setPassword("1234");
            user.setName("Juan");

            User created = service.createUser(user);
            System.out.println("Usuario creado: " + created);

            // =========================
            // 2. CREAR USUARIO DUPLICADO
            // =========================
            try {
                service.createUser(user);
            } catch (DuplicateUserException e) {
                System.out.println("OK duplicado: " + e.getMessage());
            }

            // =========================
            // 3. LOGIN CORRECTO (EMAIL)
            // =========================
            User loginUser = service.login("juan@test.com", "1234");
            System.out.println("Login correcto: " + loginUser.getUsername());

            // =========================
            // 4. LOGIN CORRECTO (USERNAME)
            // =========================
            loginUser = service.login("juan123", "1234");
            System.out.println("Login correcto username: " + loginUser.getUsername());

            // =========================
            // 5. LOGIN USUARIO NO EXISTE
            // =========================
            try {
                service.login("noexiste", "1234");
            } catch (UserNotFoundException e) {
                System.out.println("OK usuario no existe");
            }

            // =========================
            // 6. LOGIN PASSWORD INCORRECTA
            // =========================
            try {
                service.login("juan123", "wrong");
            } catch (UserUnauthorizedException e) {
                System.out.println("OK password incorrecta");
            }

            // =========================
            // 7. GET USER CORRECTO
            // =========================
            User fetched = service.getUser(created.getId());
            System.out.println("Usuario obtenido: " + fetched);

            // =========================
            // 8. GET USER NO EXISTE
            // =========================
            try {
                service.getUser(9999L);
            } catch (UserNotFoundException e) {
                System.out.println("OK usuario no encontrado");
            }

            // =========================
            // 9. CHANGE PASSWORD CORRECTO
            // =========================
            service.changePassword(created.getId(), "1234", "abcd");
            System.out.println("Password cambiada correctamente");

            // =========================
            // 10. CHANGE PASSWORD INCORRECTA (OLD WRONG)
            // =========================
            try {
                service.changePassword(created.getId(), "wrong", "zzzz");
            } catch (UserUnauthorizedException e) {
                System.out.println("OK old password incorrecta");
            }

            // =========================
            // 11. CHANGE PASSWORD IGUAL
            // =========================
            try {
                service.changePassword(created.getId(), "abcd", "abcd");
            } catch (UserUnauthorizedException e) {
                System.out.println("OK password igual");
            }

            // =========================
            // 12. CHANGE PASSWORD USER NO EXISTE
            // =========================
            try {
                service.changePassword(9999L, "1234", "abcd");
            } catch (UserNotFoundException e) {
                System.out.println("OK usuario no existe en changePassword");
            }

        } catch (UserException e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}