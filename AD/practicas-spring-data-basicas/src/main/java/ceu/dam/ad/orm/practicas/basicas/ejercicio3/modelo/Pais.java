package ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Pais {

	@Id
	@Column(name="cod_pais")
	private String codigo;
	private String descripcion;
}