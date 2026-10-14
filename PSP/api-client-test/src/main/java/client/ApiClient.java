package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.List;

import Model.Coche;
import tools.jackson.databind.ObjectMapper;

public class ApiClient {
	
	private String urlBase;
	private HttpClient httpClient;
	
	public ApiClient(String urlBase) {
		this.urlBase = urlBase;
		httpClient = HttpClient.newHttpClient();
	}
	
	public Coche getCocheById(String id) throws CochesApiException, CochesNotFoundException {
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "/" + id)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			System.out.println("Codigo respuesta: " + response.statusCode());
			int status = response.statusCode();
			if (status == 200) {
				return new ObjectMapper().readValue(response.body(), Coche.class);
			}
			if (status == 404) {
				throw new CochesNotFoundException("No existe coche");
			}
			throw new CochesApiException("Codigo respuesta erróneo");
		} catch (IOException | InterruptedException e) {
			System.err.println("Error enviando peticion");
			e.printStackTrace();
			throw new CochesApiException("Error consultando API coches");
		}
	}
	
	public List<Coche> getCochesAll() throws CochesApiException {
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			System.out.println("Codigo respuesta: " + response.statusCode());
			int status = response.statusCode();
			if (status == 200) {
				Coche[] cochesArray = new ObjectMapper().readValue(response.body(), Coche[].class);
				return Arrays.asList(cochesArray);
			}
			throw new CochesApiException("Codigo respuesta erróneo");
		} catch (IOException | InterruptedException e) {
			System.err.println("Error enviando peticion");
			e.printStackTrace();
			throw new CochesApiException("Error consultando API coches");
		}
	}
	
	public Coche postCoche(Coche coche) throws CochesApiException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(coche);
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase))
					.POST(BodyPublishers.ofString(json)).build();
			
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			System.out.println("Codigo respuesta: " + response.statusCode());
			
			int status = response.statusCode();
			if (status == 200) {
				return new ObjectMapper().readValue(response.body(), Coche.class);
			}
			
			throw new CochesApiException("Codigo respuesta erróneo");
		} catch (IOException | InterruptedException e) {
			System.err.println("Error enviando peticion");
			e.printStackTrace();
			throw new CochesApiException("Error consultando API coches");
		}
	}
}
