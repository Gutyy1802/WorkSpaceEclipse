package ceu.dam.psp.padel.client;
import java.util.List;

import ceu.dam.psp.padel.dto.JugadorResponseDto;
import ceu.dam.psp.padel.exceptions.ApiConexionException;
import ceu.dam.psp.padel.exceptions.PadelInvalidDataException;
import ceu.dam.psp.padel.exceptions.PadelNotFoundException;


public class PadelApiClientImpl  implements PadelApiClient{

    private String apiKey;

    public PadelApiClientImpl(String apiKey) {
        this.apiKey = apiKey; // Este apiKey está sin cifrar
    }

	@Override
	public List<JugadorResponseDto> buscarJugadores(String nivel, int edadMin, int edadMax)
			throws ApiConexionException, PadelNotFoundException {
		return null;
	}

	@Override
	public String crearPartidoDobles(Long idOrganizador, Integer hora)
			throws ApiConexionException, PadelNotFoundException, PadelInvalidDataException {
		return null;
	}

	@Override
	public List<JugadorResponseDto> addJugador(String codigoPartido, Long idJugador)
			throws ApiConexionException, PadelNotFoundException, PadelInvalidDataException {
		return null;
	}


}
