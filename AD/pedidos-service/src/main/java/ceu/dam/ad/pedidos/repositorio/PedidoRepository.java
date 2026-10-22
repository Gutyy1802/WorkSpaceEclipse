package ceu.dam.ad.pedidos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.pedidos.model.Pedido;
import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	List<Pedido> findByArticulosCodBarras(String codBarras);
}
