package ceu.dam.psp.wallapop.model;

import java.util.List;

import lombok.Data;

@Data
public class Usuario {

	private String identificador;
	private String descripcion;
	private String contraseña;
	private List<Articulo> catalogo;
}