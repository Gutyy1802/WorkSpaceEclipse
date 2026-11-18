package ceu.dam.psp.padel.client;
import java.util.List;

import ceu.dam.psp.padel.dto.JugadorResponseDto;
import ceu.dam.psp.padel.exceptions.ApiConexionException;
import ceu.dam.psp.padel.exceptions.PadelInvalidDataException;
import ceu.dam.psp.padel.exceptions.PadelNotFoundException;

public interface PadelApiClient {

    /**
     * Busca jugadores aplicando filtros opcionales.
     * Debe realizar una petición HTTP al API de búsqueda de jugadores.
     * El cliente debe enviar la cabecera "API-KEY" en la petición. Este API-KEY
     * debe ir cifrado en Sha512 
     *
     * @param nivel nivel del jugador (puede ser null o vacío para no filtrar por nivel)
     * @param edadMin edad mínima del jugador
     * @param edadMax edad máxima del jugador
     * @return lista de jugadores que cumplen los criterios de búsqueda
     *
     * @throws PadelNotFoundException si la petición es correcta pero no se encuentra ningún jugador
     * @throws ApiConexionException si ocurre un error de conexión o el servidor devuelve un error inesperado
     */
    List<JugadorResponseDto> buscarJugadores(String nivel, int edadMin, int edadMax)
            throws ApiConexionException, PadelNotFoundException;


    /**
     * Crea un nuevo partido de dobles (no individual) para hoy.
     * Debe realizar una petición HTTP al API de creación de partidos.
     * El partido será para la fecha actual, por tanto NO HAY QUE ENVIAR NINGUNA FECHA. 
     * El API será la encargada de poner la fecha de hoy por defecto.
     * 
     * El cliente debe enviar la cabecera "API-KEY" en la petición. Este API-KEY
     * debe ir cifrado en Sha512 
     *
     * @param idOrganizador identificador del jugador que organiza el partido
     * @param hora hora del partido (por ejemplo 18 para las 18:00)
     * @return código del partido creado
     *
     * @throws PadelNotFoundException si no existe el jugador organizador
     * @throws PadelInvalidDataException si los datos enviados son incorrectos
     * @throws ApiConexionException si ocurre un error de conexión o del servidor
     */
    String crearPartidoDobles(Long idOrganizador, Integer hora)
            throws ApiConexionException, PadelNotFoundException, PadelInvalidDataException;


    /**
     * Añade un jugador a un partido existente.
     * Debe realizar una petición HTTP al API correspondiente,
     * indicando el código del partido y el id del jugador en la URL.
     * El cliente debe enviar la cabecera "API-KEY" en la petición. Este API-KEY
     * debe ir cifrado en Sha512 
     *
     * @param codigoPartido código identificador del partido
     * @param idJugador identificador del jugador a añadir
     * @return lista actualizada de jugadores inscritos en el partido
     *
     * @throws PadelNotFoundException si el partido o el jugador no existen
     * @throws PadelInvalidDataException si el jugador ya está inscrito o el partido está completo
     * @throws ApiConexionException si ocurre un error de conexión o del servidor
     */
    List<JugadorResponseDto> addJugador(String codigoPartido, Long idJugador)
            throws ApiConexionException, PadelNotFoundException, PadelInvalidDataException;

}
