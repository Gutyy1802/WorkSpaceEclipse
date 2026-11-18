package ceu.dam.psp.wallapop.client;

import ceu.dam.psp.wallapop.exceptions.ApiConexionException;
import ceu.dam.psp.wallapop.exceptions.NoAutorizadoException;
import ceu.dam.psp.wallapop.exceptions.NoExisteException;
import ceu.dam.psp.wallapop.model.Articulo;
import ceu.dam.psp.wallapop.model.Usuario;

public interface WallapopApiClient {

	/** Recibe el identificador de un usuario y su password sin cifrar. Intenta hacer login 
	 * en API con estos datos. Si todo va bien, devuelve los datos del usuario. Si el usuario
	 * o password es incorrecto porque el API nos devuelva dicho error, lanzararemos NoAutorizadoException.
	 * Si hay cualquier otro error, lanzaremos ApiConexionException
	 */
	Usuario login(String identificador, String password) throws NoAutorizadoException, ApiConexionException;
	

	/** Recibe el código de un artículo. Intenta consultar dicho artículo en el API. Si todo va bien
	 * devuelve los datos del artículo. Si el artículo no existe porque es lo que nos indica el API, lanzaremos
	 * NoExisteException. Si hay cualquier otro error, lanzaremos ApiConexionException
	 */
	Articulo consultarArticulo(Long codigoArticulo) throws NoExisteException, ApiConexionException;
}