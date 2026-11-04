package ceu.dam.pedidos.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "envios")
public class Envio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigoSeguimiento;
	private LocalDateTime fechaRegistro;
	private double pesoKg;
	private String estado;
	private double temperaturaCelsius;
	
}