package ceu.dam.psp.padel;
import java.util.List;
import java.util.Scanner;

import ceu.dam.psp.padel.client.PadelApiClientImpl;
import ceu.dam.psp.padel.dto.JugadorResponseDto;
import ceu.dam.psp.padel.exceptions.ApiConexionException;
import ceu.dam.psp.padel.exceptions.PadelInvalidDataException;
import ceu.dam.psp.padel.exceptions.PadelNotFoundException;

public class AppPadel {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce tu API key: ");
        String apiKey = scanner.nextLine();

        PadelApiClientImpl client = new PadelApiClientImpl(apiKey);

        boolean continuar = true;

        while (continuar) {
            // --- 1. Buscar jugadores ---
            System.out.println("\n=== Búsqueda de jugadores ===");
            System.out.print("Nivel (PRINCIPIANTE / INTERMEDIO / AVANZADO) o vacío para no filtrar: ");
            String nivel = scanner.nextLine().trim();
            if (nivel.isEmpty()) nivel = null;

            int edadMin = 0, edadMax = 100;
            try {
                System.out.print("Edad mínima: ");
                edadMin = Integer.parseInt(scanner.nextLine());
                System.out.print("Edad máxima: ");
                edadMax = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Edad inválida, usando rango 0-100");
            }

            try {
                List<JugadorResponseDto> jugadores = client.buscarJugadores(nivel, edadMin, edadMax);
                System.out.println("\nJugadores encontrados:");
                jugadores.forEach(System.out::println);
            } catch (PadelNotFoundException e) {
                System.out.println("No se encontraron jugadores: " + e.getMessage());
            } catch (ApiConexionException e) {
                System.out.println("Error de conexión: " + e.getMessage());
                e.printStackTrace();
            }

            // --- 2. Crear un partido ---
            System.out.println("\n=== Crear partido dobles ===");
            try {
                System.out.print("ID del jugador organizador: ");
                Long idOrganizador = Long.parseLong(scanner.nextLine());

                System.out.print("Hora del partido (8-22): ");
                int hora = Integer.parseInt(scanner.nextLine());

                String codPartido = client.crearPartidoDobles(idOrganizador, hora);
                System.out.println("\nPartido creado correctamente con código: " + codPartido);

            } catch (PadelNotFoundException e) {
                System.out.println("Error: jugador organizador no encontrado. " + e.getMessage());
            } catch (PadelInvalidDataException e) {
                System.out.println("Error: datos del partido inválidos. " + e.getMessage());
            } catch (ApiConexionException e) {
                System.out.println("Error de conexión: " + e.getMessage());
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Error: ID u hora inválidos. Deben ser números.");
            }

            // --- 3. Añadir un jugador a un partido ---
            System.out.println("\n=== Añadir jugador a un partido ===");
            try {
                System.out.print("Código del partido: ");
                String codigoPartido = scanner.nextLine();

                System.out.print("ID del jugador a añadir: ");
                Long idJugador = Long.parseLong(scanner.nextLine());

                List<JugadorResponseDto> listaActualizada = client.addJugador(codigoPartido, idJugador);
                System.out.println("\nJugador añadido correctamente. Lista de jugadores del partido:");
                listaActualizada.forEach(System.out::println);

            } catch (PadelNotFoundException e) {
                System.out.println("Error: partido o jugador no encontrado. " + e.getMessage());
            } catch (PadelInvalidDataException e) {
                System.out.println("Error: no se puede añadir el jugador. " + e.getMessage());
            } catch (ApiConexionException e) {
                System.out.println("Error de conexión: " + e.getMessage());
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Error: ID inválido. Debe ser numérico.");
            }

            System.out.print("\n¿Desea repetir todo el flujo? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                continuar = false;
            }
        }

        System.out.println("\nPrograma finalizado.");
        scanner.close();
    }
}
