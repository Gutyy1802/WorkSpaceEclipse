package ceu.dam.psp.wallapop.client;

import ceu.dam.psp.wallapop.exceptions.ApiConexionException;
import ceu.dam.psp.wallapop.exceptions.NoAutorizadoException;
import ceu.dam.psp.wallapop.exceptions.NoExisteException;
import ceu.dam.psp.wallapop.model.Articulo;
import ceu.dam.psp.wallapop.model.Usuario;

public class WallapopApiClientImpl implements WallapopApiClient {

	@Override
	public Usuario login(String identificador, String password) throws NoAutorizadoException, ApiConexionException {
		return null;
	}

	@Override
	public Articulo consultarArticulo(Long codigoArticulo) throws NoExisteException, ApiConexionException {
		return null;
	}

}
