package ceu.dam.ad.orm.practicas.basicas.ejercicio1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.orm.practicas.basicas.ejercicio1.model.Serie;
import java.util.List;


public interface SerieRepository extends JpaRepository<Serie, Long>{
	List<Serie> findByDescripcionContains(String descripcion);
}
