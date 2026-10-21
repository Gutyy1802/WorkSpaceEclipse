package ceu.dam.ad.test.peliculas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ceu.dam.ad.test.peliculas.model.Pelicula;
import ceu.dam.ad.test.peliculas.repositories.PeliculaRepository;

@Service
public class PeliculaService {

	private final PeliculaRepository repo;


	PeliculaService(PeliculaRepository repo) {
		this.repo = repo;
	}
	
	public List<Pelicula> consultarPeliculasAños(Integer cantAños){
		return repo.findByEstrenoAfter(LocalDate.now().getYear()-cantAños);
	}
	
	
	public List<Pelicula> buscarPeliculas(String filtro){
		return repo.findByTituloContainsOrDirectorContains(filtro, filtro);
	}
	
	public List<Pelicula> consultarBlas(String director, Integer año){
		return repo.findByDirectorContainsAndEstrenoEquals(director, año);
				
	}
	
	
	public List<Pelicula> consultarPeliculas(){
		return repo.findAll();
	}
	
	
	public Pelicula consultarPelicula(Long id) throws PeliculaNotFoundException {
		Optional<Pelicula> pelicula = repo.findById(id);
		
//		if (!pelicula.isEmpty()) {
//			return pelicula.get();
//		}
//		throw new PeliculaNotFoundException("No existe pelicula con id " + id);
		
		return pelicula.orElseThrow(()->new PeliculaNotFoundException("No existe pelicula con id " + id) );
		
	}
	
	
	public Pelicula crearPelicula(Pelicula pelicula) {
		return repo.save(pelicula);
	}
	
	public Pelicula actualizarPelicula(Pelicula pelicula) {
		return repo.save(pelicula);
	}
	
	public void borrarPelicula(Long id) {
		repo.deleteById(id);
	}
	
	
}





