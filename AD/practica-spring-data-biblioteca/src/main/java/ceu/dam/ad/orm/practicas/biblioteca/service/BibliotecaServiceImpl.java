package ceu.dam.ad.orm.practicas.biblioteca.service;

import java.util.List;

import ceu.dam.ad.orm.practicas.biblioteca.model.Carnet;
import ceu.dam.ad.orm.practicas.biblioteca.model.Libro;
import ceu.dam.ad.orm.practicas.biblioteca.model.Prestamo;
import ceu.dam.ad.orm.practicas.biblioteca.model.Socio;

public class BibliotecaServiceImpl implements BibliotecaService {

	@Override
	public Socio altaSocio(Socio socio) throws BibliotecaException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public Socio consultarSocio(String dni) throws NotFoundException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public Libro altaLibro(Libro libro) throws BibliotecaException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public List<Libro> buscarLibros(Libro ejemplo) {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public Prestamo prestarLibro(String dniSocio, Long idLibro) throws NotFoundException, BibliotecaException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public void devolverLibro(Integer anio, Integer numPrestamo) throws NotFoundException, BibliotecaException {
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public Carnet renovarCarnet(String dniSocio) throws NotFoundException, BibliotecaException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

	@Override
	public List<Prestamo> consultarPrestamosActivos(String dniSocio) throws NotFoundException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}

}
