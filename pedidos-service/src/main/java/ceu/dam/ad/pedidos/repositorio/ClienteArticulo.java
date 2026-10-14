package ceu.dam.ad.pedidos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.pedidos.model.ArticuloPedido;
import java.util.List;


public interface ClienteArticulo extends JpaRepository<ArticuloPedido, Long>{
	List<ArticuloPedido> findByIdPedido(Long idPedido);
}
