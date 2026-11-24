package ceu.dam.psp.wallapop.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import ceu.dam.psp.wallapop.exceptions.ApiConexionException;
import ceu.dam.psp.wallapop.exceptions.NoAutorizadoException;
import ceu.dam.psp.wallapop.exceptions.NoExisteException;
import ceu.dam.psp.wallapop.model.Articulo;
import ceu.dam.psp.wallapop.model.Usuario;

public class WallapopApiClientImpl implements WallapopApiClient {

	private String urlBase = "http://localhost:8080/wallapop";
	private HttpClient httpClient;
	
	public WallapopApiClientImpl(String uuidUrl) {
		urlBase = String.format(urlBase, uuidUrl);
		httpClient = HttpClient.newHttpClient();
	}
	
	@Override
	public Usuario login(String identificador, String password) throws NoAutorizadoException, ApiConexionException {
		try {
			HttpRequest request = HttpRequest.newBuilder(URI.create(urlBase + "/" + identificador)).GET().build();
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			int status = response.statusCode();
			System.out.println("Codigo respuesta: " + status);
			if (status == 200) {
				//return new ObjectMapper().readValue(response.body(), Usuario.class);
			}
			throw new NoAutorizadoException("Codigo de respuesta erroneo");
		} catch (Exception e) {
			throw new ApiConexionException("", e);
		}
		//return null;
	}

	@Override
	public Articulo consultarArticulo(Long codigoArticulo) throws NoExisteException, ApiConexionException {
		return null;
	}

}
