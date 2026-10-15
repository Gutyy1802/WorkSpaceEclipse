package ceu.dam.ad.pedidos.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ceu.dam.ad.pedidos.model.ArticuloPedido;
import ceu.dam.ad.pedidos.model.Pedido;
import ceu.dam.ad.pedidos.service.PedidoException;
import ceu.dam.ad.pedidos.service.PedidoNotFoundException;
import ceu.dam.ad.pedidos.service.PedidoService;

@Component
public class TestPedido {
	
	final PedidoService service;
	
	public TestPedido(PedidoService service) {
		this.service = service;
	}
	
	public void testConsultar(Long idPedido) {
		try {
			Pedido p = service.consultarPedido(idPedido);
			System.out.println(p);
		} catch (PedidoException | PedidoNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Pedido damePedido() {
		Pedido p = new Pedido();
		p.setDniCliente("1111111X");
		p.setFechaPedido(LocalDate.now());
		List<ArticuloPedido> articulos = new ArrayList<>();
		p.setArticulos(articulos);
		for (int i = 1; i <= 3; i++) {
			ArticuloPedido a = new ArticuloPedido();
			a.setCodBarras("COD" + i);
			a.setDescripcionArticulo("Artículo " + i);
			a.setExentoIva(i%2==0);
			a.setPrecioSinIva(BigDecimal.valueOf(10*i));
			articulos.add(a);
		}
		return p;
	}
	
	public void consultarPedido(String codbarra) {
		try {
			System.out.println(service.buscarPedidosArticulo(codbarra));
		} catch (PedidoException | PedidoNotFoundException e) {
			e.printStackTrace();
		}
	}
}
