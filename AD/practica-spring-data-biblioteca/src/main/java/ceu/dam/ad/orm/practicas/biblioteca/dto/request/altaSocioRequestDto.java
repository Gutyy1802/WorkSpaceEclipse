package ceu.dam.ad.orm.practicas.biblioteca.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class altaSocioRequestDto {

	@NotBlank
	private String dni;
	@NotBlank
	private String nombre;
	@NotBlank
	private String apellidos;
}