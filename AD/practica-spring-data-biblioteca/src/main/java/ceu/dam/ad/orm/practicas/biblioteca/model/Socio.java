package ceu.dam.ad.orm.practicas.biblioteca.model;

import java.util.List;

import lombok.Data;

@Data
public class Socio {

	private String dni;
	private String nombre;
	private String apellidos;
	private Carnet carnet;
	private List<Prestamo> prestamos;
}