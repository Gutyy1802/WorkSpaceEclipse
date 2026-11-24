package ceu.dam.ad.orm.practicas.biblioteca.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class altaLibroRequestDto {

	@Size(min=13, max=13, message="El tamaño tiene que ser de 13 cifras.")
	private String isbn;
	@NotBlank
	private String titulo;
	@NotBlank
	private String autor;
	private String genero;
	@Min(1)
	private Integer ejemplaresDisponibles;
}