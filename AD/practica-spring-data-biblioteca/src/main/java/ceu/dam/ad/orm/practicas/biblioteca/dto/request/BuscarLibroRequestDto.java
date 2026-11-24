package ceu.dam.ad.orm.practicas.biblioteca.dto.request;

import lombok.Data;

@Data
public class BuscarLibroRequestDto {

	private Long id;
	private String isbn;
	private String titulo;
	private String autor;
	private String genero;
	private Integer ejemplaresDisponibles;
}