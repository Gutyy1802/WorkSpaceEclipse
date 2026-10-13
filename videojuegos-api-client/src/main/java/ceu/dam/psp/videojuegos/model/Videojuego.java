package ceu.dam.psp.videojuegos.model;

import lombok.Data;

@Data
public class Videojuego {
	private String id;
	private String nombre;
	private Double valoracion;
	private Integer añoPublicacion;
	private String paisOrigen;
}
