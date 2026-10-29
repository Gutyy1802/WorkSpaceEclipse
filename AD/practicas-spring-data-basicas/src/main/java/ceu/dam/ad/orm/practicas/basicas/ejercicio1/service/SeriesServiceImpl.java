package ceu.dam.ad.orm.practicas.basicas.ejercicio1.service;

import java.util.List;
import java.util.Optional;

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
		try {
			Optional<Serie> serieOpt = repo.findById(idSerie);
			if (serieOpt.isEmpty()) {
				throw new SerieNotFoundException("Serie no encontrada con id: " + idSerie);
			}
			return serieOpt.get();
		} catch (DataAccessException e) {
			throw new SeriesServiceException("Error al conectar con la bbdd", e);
		}
	}
	
	@Override
	public List<Serie> buscarSeries(String filtroDescripcion) throws SerieNotFoundException, SeriesServiceException {
		try {
			List<Serie> listSeries = repo.findByDescripcionContains(filtroDescripcion);
			if (listSeries.isEmpty()) {
				throw new SerieNotFoundException("Serie no encontrada con descripcion: " + filtroDescripcion);
			}
			return listSeries;
		} catch (DataAccessException e) {
			throw new SeriesServiceException("Error al conectar con la bbdd", e);
		}
	}
	
	@Override
	public void elimnarSerie(Long idSerie) throws SeriesServiceException {
		try {
			repo.deleteById(idSerie);
		} catch (DataAccessException e) {
			throw new SeriesServiceException("Error al conectar con la bbdd", e);
		}
	}
	
	@Override
	public void actualizarSerie(Serie serie) throws SeriesServiceException {
		try {
			repo.save(serie);
		} catch (DataAccessException e) {
			throw new SeriesServiceException("Error al conectar con la bbdd", e);
		}
	}
}
