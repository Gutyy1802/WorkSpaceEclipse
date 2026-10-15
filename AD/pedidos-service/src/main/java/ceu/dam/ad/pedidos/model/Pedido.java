package ceu.dam.ad.pedidos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@Column(name = "id")
	private Long idPedido;
	@Column(name = "fecha")
	private LocalDate fechaPedido;
	@Column(name = "cliente")
	private String dniCliente;
	private BigDecimal total;
	private BigDecimal subtotal;
	private BigDecimal impuestos;
	@Transient
	private List<ArticuloPedido> articulos;
}
