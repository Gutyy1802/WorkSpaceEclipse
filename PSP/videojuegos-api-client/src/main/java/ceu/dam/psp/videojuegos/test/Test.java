package ceu.dam.psp.videojuegos.test;

import java.util.List;

import ceu.dam.psp.videojuegos.client.VideojuegoApiClient;
import ceu.dam.psp.videojuegos.client.VideojuegoApiClientImpl;
import ceu.dam.psp.videojuegos.exceptions.ApiException;
import ceu.dam.psp.videojuegos.exceptions.NotFoundException;
import ceu.dam.psp.videojuegos.model.Videojuego;

public class Test {

    public static void main(String[] args) {
        VideojuegoApiClient client = new VideojuegoApiClientImpl("");

        try {
            // 1. Crear
            Videojuego nuevo = new Videojuego();
            nuevo.setNombre("Zelda");
            nuevo.setValoracion(9.5);
            nuevo.setAñoPublicacion(2017);
            nuevo.setPaisOrigen("Japón");
            String id = client.create(nuevo);
            System.out.println("Creado con id: " + id);

            // 2. Buscar por id
            Videojuego encontrado = client.findById(id);
            System.out.println("Encontrado: " + encontrado);

            // 3. Buscar por año
            List<Videojuego> lista = client.findByAñoPublicacion(2012);
            System.out.println("Juegos de 2012: " + lista.size());
            for (Videojuego v : lista) {
                System.out.println(" - " + v);
            }

            // 4. Actualizar
            Videojuego existente = new Videojuego();
            existente.setId("0799823b876bcbfb24d8");
            existente.setNombre("Zelda actualizado");
            existente.setPaisOrigen("USA");
            existente.setAñoPublicacion(2017);

            try {
                client.update(existente);
                System.out.println("OK: videojuego " + existente.getId() + " actualizado");
            } catch (NotFoundException e) {
                System.out.println("NO ENCONTRADO: " + e.getMessage());
            } catch (ApiException e) {
                System.out.println("ERROR API: " + e.getMessage());
            }

            // 4.1 Actualizar un videojuego que NO existe (debe lanzar NotFoundException)
            Videojuego inexistente = new Videojuego();
            inexistente.setId("99999");
            inexistente.setNombre("No existo");
            inexistente.setPaisOrigen("Ninguno");
            inexistente.setAñoPublicacion(1900);

            try {
                client.update(inexistente);
                System.out.println("FALLO: debería haber lanzado NotFoundException");
            } catch (NotFoundException e) {
                System.out.println("OK: NotFoundException lanzada -> " + e.getMessage());
            } catch (ApiException e) {
                System.out.println("ERROR API: " + e.getMessage());
            }

            // 5. Borrar el videojuego creado en el paso 1
            try {
                client.delete(id);
                System.out.println("OK: videojuego " + id + " borrado");
            } catch (NotFoundException e) {
                System.out.println("NO ENCONTRADO: " + e.getMessage());
            } catch (ApiException e) {
                System.out.println("ERROR API: " + e.getMessage());
            }

            // 5.1 Borrarlo otra vez (ya no existe, debe lanzar NotFoundException)
            try {
                client.delete(id);
                System.out.println("FALLO: debería haber lanzado NotFoundException");
            } catch (NotFoundException e) {
                System.out.println("OK: NotFoundException lanzada -> " + e.getMessage());
            } catch (ApiException e) {
                System.out.println("ERROR API: " + e.getMessage());
            }

        } catch (NotFoundException e) {
            System.out.println("No encontrado: " + e.getMessage());
        } catch (ApiException e) {
            System.out.println("Error de API: " + e.getMessage());
        }
    }
}