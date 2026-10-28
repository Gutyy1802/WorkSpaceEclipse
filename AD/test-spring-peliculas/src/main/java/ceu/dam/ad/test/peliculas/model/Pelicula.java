package ceu.dam.ad.test.peliculas.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name =  "peliculas")
public class Pelicula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private Integer duracion;
	
	@ManyToOne
	@JoinColumn(name="id_director", nullable = false)
	private Director director;
	
	@ManyToMany
	@JoinTable(name = "peliculas_actores",
			joinColumns = { @JoinColumn(name = "id_pelicula")},
			inverseJoinColumns = {@JoinColumn(name = "id_actor")})
	List<Actor> actores;
	
	private Integer añoEstreno;
	@Transient
	private Integer edad;
}
