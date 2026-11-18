package ceu.dam.psp.wallapop;

import java.util.Scanner;

import ceu.dam.psp.wallapop.client.WallapopApiClient;
import ceu.dam.psp.wallapop.client.WallapopApiClientImpl;
import ceu.dam.psp.wallapop.exceptions.ApiConexionException;
import ceu.dam.psp.wallapop.exceptions.NoAutorizadoException;
import ceu.dam.psp.wallapop.exceptions.NoExisteException;
import ceu.dam.psp.wallapop.model.Articulo;
import ceu.dam.psp.wallapop.model.Usuario;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WallapopApiClient client = new WallapopApiClientImpl();

        boolean continuar = true;

        while (continuar) {
            try {
                System.out.print("Introduce el identificador de usuario: ");
                String usuario = scanner.nextLine();

                System.out.print("Introduce la contraseña: ");
                String password = scanner.nextLine();

                // Intentar login
                Usuario usuarioLogueado = client.login(usuario, password);
                System.out.println("\nLogin correcto. Datos del usuario:");
                System.out.println(usuarioLogueado);

            } catch (NoAutorizadoException e) {
                System.out.println("\nError: usuario o contraseña incorrectos.");
            } catch (ApiConexionException e) {
                System.out.println("\nError: no se pudo conectar con la API.");
                e.printStackTrace();
            }

            System.out.print("\n¿Desea continuar para consultar un artículo? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                continuar = false;
                break;
            }

            try {
                System.out.print("Introduce el ID del artículo a consultar: ");
                Long idArticulo = Long.parseLong(scanner.nextLine());

                Articulo articulo = client.consultarArticulo(idArticulo);
                System.out.println("\nDatos del artículo:");
                System.out.println(articulo);

            } catch (NoExisteException e) {
                System.out.println("\nError: el artículo no existe.");
            } catch (ApiConexionException e) {
                System.out.println("\nError: no se pudo conectar con la API.");
            } catch (NumberFormatException e) {
                System.out.println("\nError: ID de artículo inválido.");
            }

            System.out.print("\n¿Desea repetir las dos pruebas? (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                continuar = false;
            }
        }

        System.out.println("\nPrograma finalizado.");
        scanner.close();
    }
}

