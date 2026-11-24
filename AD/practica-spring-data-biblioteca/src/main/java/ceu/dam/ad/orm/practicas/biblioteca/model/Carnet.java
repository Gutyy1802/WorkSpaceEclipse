package ceu.dam.ad.orm.practicas.biblioteca.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class Carnet {

	private UUID id;
	private Socio socio;
	private LocalDate fechaAlta;
	private LocalDate fechaCaducidad;
}