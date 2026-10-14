package ceu.dam.ad.pedidos;

import ceu.dam.ad.pedidos.service.PedidoException;
import ceu.dam.ad.pedidos.service.PedidoNotFoundException;
import ceu.dam.ad.pedidos.service.PedidoServiceImp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class PedidosServiceApplication {

	public static void main(String[] args) throws PedidoException, PedidoNotFoundException {
		ConfigurableApplicationContext context = SpringApplication.run(PedidosServiceApplication.class, args);
		PedidoServiceImp service = context.getBean(PedidoServiceImp.class);
		
		System.out.println(service.consultarPedido(1L));
	}
}