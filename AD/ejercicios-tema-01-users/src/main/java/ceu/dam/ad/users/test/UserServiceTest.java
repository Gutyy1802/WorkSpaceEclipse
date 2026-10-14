package ceu.dam.ad.users.test;

import ceu.dam.ad.users.model.User;
import ceu.dam.ad.users.services.DuplicateUserException;
import ceu.dam.ad.users.services.UserException;
import ceu.dam.ad.users.services.UserNotFoundException;
import ceu.dam.ad.users.services.UserServiceImpl;
import ceu.dam.ad.users.services.UserUnauthorizedException;

public class UserServiceTest {

    public static void main(String[] args) {

        UserServiceImpl service = new UserServiceImpl();

        System.out.println("==========================================");
        System.out.println("        PRUEBAS USER SERVICE");
        System.out.println("==========================================");


        // =====================================================
        // 1. CREAR USUARIO CORRECTAMENTE
        // =====================================================

        System.out.println("\n--- 1. CREAR USUARIO CORRECTAMENTE ---");

        User user = new User();
        user.setUsername("pepe123");
        user.setName("Pepe");
        user.setEmail("pepe@gmail.com");
        user.setPassword("1234");

        User userCreado = null;

        try {

            userCreado = service.createUser(user);

            System.out.println("OK - Usuario creado correctamente");
            System.out.println("ID: " + userCreado.getId());
            System.out.println("Username: " + userCreado.getUsername());
            System.out.println("Email: " + userCreado.getEmail());

        } catch (DuplicateUserException e) {

            System.out.println("ERROR - El usuario ya existe");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 2. CREAR USUARIO CON EMAIL DUPLICADO
        // =====================================================

        System.out.println("\n--- 2. CREAR USUARIO CON EMAIL DUPLICADO ---");

        User userEmailDuplicado = new User();
        userEmailDuplicado.setUsername("otroUsuario");
        userEmailDuplicado.setName("Otro Usuario");
        userEmailDuplicado.setEmail("pepe@gmail.com");
        userEmailDuplicado.setPassword("1234");

        try {

            service.createUser(userEmailDuplicado);

            System.out.println("ERROR - Debería haber lanzado DuplicateUserException");

        } catch (DuplicateUserException e) {

            System.out.println("OK - Se ha detectado correctamente el email duplicado");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 3. CREAR USUARIO CON USERNAME DUPLICADO
        // =====================================================

        System.out.println("\n--- 3. CREAR USUARIO CON USERNAME DUPLICADO ---");

        User userUsernameDuplicado = new User();
        userUsernameDuplicado.setUsername("pepe123");
        userUsernameDuplicado.setName("Otro Pepe");
        userUsernameDuplicado.setEmail("otro@gmail.com");
        userUsernameDuplicado.setPassword("1234");

        try {

            service.createUser(userUsernameDuplicado);

            System.out.println("ERROR - Debería haber lanzado DuplicateUserException");

        } catch (DuplicateUserException e) {

            System.out.println("OK - Se ha detectado correctamente el username duplicado");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 4. BUSCAR USUARIO EXISTENTE
        // =====================================================

        System.out.println("\n--- 4. BUSCAR USUARIO EXISTENTE ---");

        if (userCreado != null) {

            try {

                User userEncontrado = service.getUser(userCreado.getId());

                System.out.println("OK - Usuario encontrado");
                System.out.println(userEncontrado);

            } catch (UserNotFoundException e) {

                System.out.println("ERROR - El usuario debería existir");

            } catch (UserException e) {

                System.out.println("ERROR - " + e.getMessage());
            }
        }


        // =====================================================
        // 5. BUSCAR USUARIO INEXISTENTE
        // =====================================================

        System.out.println("\n--- 5. BUSCAR USUARIO INEXISTENTE ---");

        try {

            service.getUser(999999L);

            System.out.println("ERROR - Debería haber lanzado UserNotFoundException");

        } catch (UserNotFoundException e) {

            System.out.println("OK - Se ha detectado correctamente el usuario inexistente");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 6. LOGIN CORRECTO CON EMAIL
        // =====================================================

        System.out.println("\n--- 6. LOGIN CORRECTO CON EMAIL ---");

        try {

            User loggedUser = service.login(
                    "pepe@gmail.com",
                    "1234"
            );

            System.out.println("OK - Login correcto");
            System.out.println("Usuario: " + loggedUser.getUsername());

        } catch (UserNotFoundException | UserUnauthorizedException e) {

            System.out.println("ERROR - El login debería ser correcto: " + e.getMessage());

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 7. LOGIN CORRECTO CON USERNAME
        // =====================================================

        System.out.println("\n--- 7. LOGIN CORRECTO CON USERNAME ---");

        try {

            User loggedUser = service.login(
                    "pepe123",
                    "1234"
            );

            System.out.println("OK - Login correcto");
            System.out.println("Usuario: " + loggedUser.getUsername());

        } catch (UserNotFoundException | UserUnauthorizedException e) {

            System.out.println("ERROR - El login debería ser correcto: " + e.getMessage());

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 8. LOGIN CON USUARIO INEXISTENTE
        // =====================================================

        System.out.println("\n--- 8. LOGIN CON USUARIO INEXISTENTE ---");

        try {

            service.login(
                    "usuarioQueNoExiste",
                    "1234"
            );

            System.out.println("ERROR - Debería haber lanzado UserNotFoundException");

        } catch (UserNotFoundException e) {

            System.out.println("OK - Usuario inexistente detectado correctamente");

        } catch (UserUnauthorizedException e) {

            System.out.println("ERROR - Se esperaba UserNotFoundException");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 9. LOGIN CON CONTRASEÑA INCORRECTA
        // =====================================================

        System.out.println("\n--- 9. LOGIN CON CONTRASEÑA INCORRECTA ---");

        try {

            service.login(
                    "pepe123",
                    "contraseñaIncorrecta"
            );

            System.out.println("ERROR - Debería haber lanzado UserUnauthorizedException");

        } catch (UserNotFoundException e) {

            System.out.println("ERROR - El usuario existe, debería ser Unauthorized");

        } catch (UserUnauthorizedException e) {

            System.out.println("OK - Contraseña incorrecta detectada correctamente");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 10. CAMBIAR CONTRASEÑA CORRECTAMENTE
        // =====================================================

        System.out.println("\n--- 10. CAMBIAR CONTRASEÑA CORRECTAMENTE ---");

        if (userCreado != null) {

            try {

                service.changePassword(
                        userCreado.getId(),
                        "1234",
                        "5678"
                );

                System.out.println("OK - Contraseña modificada correctamente");

            } catch (UserNotFoundException | UserUnauthorizedException e) {

                System.out.println("ERROR - El cambio debería ser correcto: " + e.getMessage());

            } catch (UserException e) {

                System.out.println("ERROR - " + e.getMessage());
            }
        }


        // =====================================================
        // 11. LOGIN CON LA NUEVA CONTRASEÑA
        // =====================================================

        System.out.println("\n--- 11. LOGIN CON LA NUEVA CONTRASEÑA ---");

        try {

            User loggedUser = service.login(
                    "pepe123",
                    "5678"
            );

            System.out.println("OK - Login con nueva contraseña correcto");
            System.out.println("Usuario: " + loggedUser.getUsername());

        } catch (UserNotFoundException | UserUnauthorizedException e) {

            System.out.println("ERROR - El login debería ser correcto: " + e.getMessage());

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        // =====================================================
        // 12. CAMBIAR CONTRASEÑA CON CONTRASEÑA ANTIGUA INCORRECTA
        // =====================================================

        System.out.println("\n--- 12. CAMBIAR CONTRASEÑA CON CONTRASEÑA ANTIGUA INCORRECTA ---");

        if (userCreado != null) {

            try {

                service.changePassword(
                        userCreado.getId(),
                        "contraseñaIncorrecta",
                        "9999"
                );

                System.out.println("ERROR - Debería haber lanzado UserUnauthorizedException");

            } catch (UserNotFoundException e) {

                System.out.println("ERROR - El usuario existe");

            } catch (UserUnauthorizedException e) {

                System.out.println("OK - Contraseña antigua incorrecta detectada");

            } catch (UserException e) {

                System.out.println("ERROR - " + e.getMessage());
            }
        }


        // =====================================================
        // 13. CAMBIAR CONTRASEÑA POR LA MISMA CONTRASEÑA
        // =====================================================

        System.out.println("\n--- 13. CAMBIAR CONTRASEÑA POR LA MISMA ---");

        if (userCreado != null) {

            try {

                service.changePassword(
                        userCreado.getId(),
                        "5678",
                        "5678"
                );

                System.out.println("ERROR - Debería haber lanzado UserUnauthorizedException");

            } catch (UserNotFoundException e) {

                System.out.println("ERROR - El usuario existe");

            } catch (UserUnauthorizedException e) {

                System.out.println("OK - Se ha detectado que la nueva contraseña es igual");

            } catch (UserException e) {

                System.out.println("ERROR - " + e.getMessage());
            }
        }


        // =====================================================
        // 14. CAMBIAR CONTRASEÑA DE USUARIO INEXISTENTE
        // =====================================================

        System.out.println("\n--- 14. CAMBIAR CONTRASEÑA DE USUARIO INEXISTENTE ---");

        try {

            service.changePassword(
                    999999L,
                    "1234",
                    "5678"
            );

            System.out.println("ERROR - Debería haber lanzado UserNotFoundException");

        } catch (UserNotFoundException e) {

            System.out.println("OK - Usuario inexistente detectado correctamente");

        } catch (UserUnauthorizedException e) {

            System.out.println("ERROR - Se esperaba UserNotFoundException");

        } catch (UserException e) {

            System.out.println("ERROR - " + e.getMessage());
        }


        System.out.println("\n==========================================");
        System.out.println("        FIN DE LAS PRUEBAS");
        System.out.println("==========================================");
    }
}
