package ceu.dam.ad.orm.practicas.biblioteca.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Prestamo {

	private Integer anio;
	private Integer numPrestamo;
	private Socio socio;
	private Libro libro;
	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucion;
}