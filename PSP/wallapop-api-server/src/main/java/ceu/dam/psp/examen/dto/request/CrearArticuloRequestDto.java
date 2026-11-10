package ceu.dam.psp.examen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CrearArticuloRequestDto {
	
	private Long codigo;
	@NotBlank
	@Size(max=30, message="El nombre no puede tener mas de 30 caracteres")
	private String nombre;
	@NotBlank
	private String detalles;
	@NotNull
	@Min(1)
	private Double precio;
	@NotBlank
	@Size(max=30, message="La categoria no puede tener mas de 30 caracteres")
	private String categoria;
	@NotBlank
	@Schema(description="No puede estar en blanco ni espacios")
	private String idPropietario;
}