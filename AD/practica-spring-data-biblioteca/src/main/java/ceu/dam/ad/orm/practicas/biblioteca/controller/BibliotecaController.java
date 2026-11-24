package ceu.dam.ad.orm.practicas.biblioteca.controller;

import java.util.List;

import ceu.dam.ad.orm.practicas.biblioteca.dto.request.BuscarLibroRequestDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.request.altaLibroRequestDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.AltaLibroResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.BuscarLibroResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.ConsultarSocioResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.altaSocioResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.model.Carnet;
import ceu.dam.ad.orm.practicas.biblioteca.model.Libro;
import ceu.dam.ad.orm.practicas.biblioteca.model.Prestamo;
import ceu.dam.ad.orm.practicas.biblioteca.service.BibliotecaException;
import ceu.dam.ad.orm.practicas.biblioteca.service.NotFoundException;

// URL general: /api/biblioteca/
public interface BibliotecaController {
	
	/** URL: /socio
	 * Recibimos del socio únicamente dni, nombre y apellidos. Ninguno puede ser vacío.
	 * Devolvemos dni, nombre, apellidos y uid del carnet nuevo  */
	public altaSocioResponseDto altaSocio(altaLibroRequestDto socio) throws BibliotecaException;

	/**  URL: /socio
	 * Devolvemos todos los datos del socio, pero sin su lista de préstamos */
	public ConsultarSocioResponseDto consultarSocio(String dni) throws NotFoundException;

	/** URL: /libro
	 * Recibimos todos los datos del libro excepto su ID. Validaciones:
	 * 		- ejemplaresDisponibles > 0
	 * 		- isbn tamaño igual a 13
	 * 		- titulo y autor no pueden ser vacíos
	 * Devolvemos todos los datos del libro. */
	public AltaLibroResponseDto altaLibro(altaLibroRequestDto libro) throws BibliotecaException;

	/** URL: /libro
	 * Recibimos todos los datos por los que queremos filtrar como requestParam. Ninguno es obligatorio
	 * Devolvemos todos los datos de los libros encontrados. */
	public List<BuscarLibroResponseDto> buscarLibros(BuscarLibroRequestDto ejemplo);

	/** URL: /libro/XXX (PUT)
	 * XXX es el id del libro
	 * el dniSocio se recibirá como requestParam
	 * Devolvemos todos los datos del préstamo salvo socio y libro
	 */
	public Prestamo prestarLibro(String dniSocio, Long idLibro) throws NotFoundException, BibliotecaException;

	/** URL: /prestamo/XXX/YYY 
	 * XXX es año
	 * YYY es número de prestamo 
	 * */
	public void devolverLibro(Integer anio, Integer numPrestamo) throws NotFoundException, BibliotecaException;

	/** URL: /socio/XXX/carnet 
	 * XXX es el dni del socio
	 * Devuelve todos los datos del carnet excepto el socio  */
	public Carnet renovarCarnet(String dniSocio) throws NotFoundException, BibliotecaException;

	/** URL: / socio/XXX/prestamos 
	 * Devuelve todos los datos del préstamo excepto socio
	 * */
	public List<Prestamo> consultarPrestamosActivos(String dniSocio) throws NotFoundException;
}
