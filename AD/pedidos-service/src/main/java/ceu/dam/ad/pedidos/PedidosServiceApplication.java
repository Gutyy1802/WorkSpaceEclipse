package ceu.dam.ad.pedidos;

import ceu.dam.ad.pedidos.service.PedidoException;
import ceu.dam.ad.pedidos.service.PedidoNotFoundException;
import ceu.dam.ad.pedidos.test.TestPedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class PedidosServiceApplication {

	public static void main(String[] args) throws PedidoException, PedidoNotFoundException {
		ConfigurableApplicationContext context = SpringApplication.run(PedidosServiceApplication.class, args);
		TestPedido test = context.getBean(TestPedido.class);
		
		test.consultarPedido("COD1");
	}
}