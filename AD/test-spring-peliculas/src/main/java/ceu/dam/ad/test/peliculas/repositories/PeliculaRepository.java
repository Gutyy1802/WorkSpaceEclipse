package ceu.dam.ad.test.peliculas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.test.peliculas.model.Pelicula;



public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

	List<Pelicula> findByDirectorContainsAndEstrenoEquals(String director, Integer año);
	
	List<Pelicula> findByTituloContainsOrDirectorContains(String filtroTitulo, String filtroDirector );
	
	
	List<Pelicula> findByEstrenoAfter(Integer añoDesde);
	
	Optional<Pelicula> findByTitulo(String titulo);
}
