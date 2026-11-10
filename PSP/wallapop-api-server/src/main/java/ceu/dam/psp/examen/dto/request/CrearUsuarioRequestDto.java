package ceu.dam.psp.examen.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CrearUsuarioRequestDto {

	@NotBlank
	@Size(max=30, message="El identificador no puede tener mas de 30 caracteres.")
	private String identificador;
	@NotBlank
	private String descripcion;
	@NotBlank
	@Size(min=10, message="La contraseña tiene que tener minimo 10 caracteres.")
	private String contraseña;
}