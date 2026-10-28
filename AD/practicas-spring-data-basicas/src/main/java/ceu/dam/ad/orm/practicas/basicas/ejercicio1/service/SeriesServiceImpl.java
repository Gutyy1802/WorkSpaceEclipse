package ceu.dam.ad.orm.practicas.basicas.ejercicio1.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.ad.orm.practicas.basicas.ejercicio1.model.Serie;
import ceu.dam.ad.orm.practicas.basicas.ejercicio1.repository.SerieRepository;

@Service
public class SeriesServiceImpl implements SeriesService {

	final SerieRepository repo;

	SeriesServiceImpl(SerieRepository repo) {
		this.repo = repo;
	}
	

	@Override
	public Serie crearSerie(Serie serie) throws SeriesServiceException {
		try {
			
			return repo.save(serie);
		} catch (DataAccessException e) {
			throw new SeriesServiceException("Error al conectar con la bbdd", e);
		}
		
	}
	
	@Override
	public Serie consultarSerie(Long idSerie) throws SerieNotFoundException, SeriesServiceException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
	}
	
	@Override
	public List<Serie> buscarSeries(String filtroDescripcion) throws SerieNotFoundException, SeriesServiceException {
		return null;
		// TODO: IMPLEMENTAR POR EL ALUMNO...
		
	}
	
	@Override
	public void elimnarSerie(Long idSerie) throws SeriesServiceException {
		// TODO: IMPLEMENTAR POR EL ALUMNO...
		
	}
	
	@Override
	public void actualizarSerie(Serie serie) throws SeriesServiceException {
		// TODO: IMPLEMENTAR POR EL ALUMNO...
		
	}
}
