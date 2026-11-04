package ceu.dam.pedidos.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnvioDto {
	
		private Long id;
		private String codigoSeguimiento;
		private LocalDateTime fechaRegistro;
		private double pesoKg;
		private String estado;
		private double temperaturaCelsius;
}
