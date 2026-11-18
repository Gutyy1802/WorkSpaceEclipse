package ceu.dam.psp.wallapop.model;

import lombok.Data;

@Data
public class Articulo {

	private Long codigo;
	private String nombre;
	private String detalles;
	private Double precio;
	private String categoria;
	private String idPropietario;
	private Integer estado; // 0: Disponible // 1: Vendido 
}