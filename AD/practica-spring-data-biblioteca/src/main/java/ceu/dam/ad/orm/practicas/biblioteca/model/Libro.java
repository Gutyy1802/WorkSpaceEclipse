package ceu.dam.ad.orm.practicas.biblioteca.model;

import lombok.Data;

@Data
public class Libro {

	private Long id;
	private String isbn;
	private String titulo;
	private String autor;
	private String genero;
	private Integer ejemplaresDisponibles;
}