package ceu.dam.psp.videojuegos.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ceu.dam.psp.videojuegos.exceptions.ApiException;
import ceu.dam.psp.videojuegos.exceptions.NotFoundException;
import ceu.dam.psp.videojuegos.model.Videojuego;
import tools.jackson.databind.ObjectMapper;

public class VideojuegoApiClientImpl implements VideojuegoApiClient {

	private String urlBase = "https://ca0a1e3fd17dbc62ec55.free.beeceptor.com/api/videojuego";
	private HttpClient httpClient;

	public VideojuegoApiClientImpl(String uuidUrl) {
		// El constructor recibe el identificador que ha generado crudcrud.com para
		// nuestro API y construye la URL base
		urlBase = String.format(urlBase, uuidUrl);
		httpClient = HttpClient.newHttpClient();
	}

	// TODO: Implementar el resto de métodos
	@Override
	public Videojuego findById(String id) throws NotFoundException, ApiException {
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "/" + id)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();
			System.out.println("Codigo respuesta: " + status);
			if (status == 200) {

				return new ObjectMapper().readValue(response.body(), Videojuego.class);
			} else if (status == 404) {
				throw new NotFoundException("No existe ese videojuego");
			}
			throw new NotFoundException("Codigo de respuesta erroneo");
		} catch (IOException | InterruptedException e) {
			System.err.println("Error enviando peticion");
			e.printStackTrace();
			throw new ApiException("Error consultando API videojuego", e);
		}

	}

	@Override
	public List<Videojuego> findByAñoPublicacion(Integer año) throws ApiException, NotFoundException {
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();
			if (status != 200) {
				throw new ApiException("Codigo de respuesta inesperado: " + status, null);
			}

			List<Videojuego> todos = Arrays.asList(new ObjectMapper().readValue(response.body(), Videojuego[].class));

			List<Videojuego> resultado = new ArrayList<Videojuego>();

			for (Videojuego v : todos) {
				if (año.equals(v.getAñoPublicacion())) {
					resultado.add(v);
				}
			}
			if (resultado.isEmpty()) {
				throw new NotFoundException("No hay videojuegos publicados en " + año);
			}
			return resultado;
		} catch (IOException e) {
			throw new ApiException("Error consultando API videojuego", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ApiException("Peticion interrumpida", e);
		}
	}

	@Override
	public String create(Videojuego videojuego) throws ApiException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(videojuego);

			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase)).POST(BodyPublishers.ofString(json))
					.build();

			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();

			if (status < 200 || status >= 300) {
				throw new ApiException("Error al crear el videojuego. Código: " + status);
			}

			Videojuego creado = mapper.readValue(response.body(), Videojuego.class);
			return creado.getId();

		} catch (IOException e) {
			throw new ApiException("Error de comunicación con la API", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ApiException("Petición interrumpida", e);
		}
	}

	@Override
	public void update(Videojuego videojuego) throws NotFoundException, ApiException {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(videojuego);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlBase + videojuego.getId()))
					.PUT(HttpRequest.BodyPublishers.ofString(json)).build();

			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();

			if (status == 404) {
				throw new NotFoundException("No existe el videojuego con id " + videojuego.getId());
			}
			if (status < 200 || status >= 300) {
				throw new ApiException("Error al actualizar el videojuego: código " + response.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			throw new ApiException("Error de comunicación con la API", e);
		}
	}

	@Override
	public void delete(String id) throws NotFoundException, ApiException {
	    try {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(urlBase + id))
	                .DELETE()
	                .build();

	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	        int status = response.statusCode();
	        if (status == 404) {
	            throw new NotFoundException("No existe el videojuego con id " + id);
	        }
	        if (status < 200 || status >= 300) {
	            throw new ApiException("Error al borrar el videojuego. Código: " + status);
	        }
	    } catch (IOException | InterruptedException e) {
	        throw new ApiException("Error de comunicación con la API: " + e.getMessage());
	    }
	}
}