package ceu.dam.ad.pedidos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.ad.pedidos.model.Pedido;

public interface ClientePedido extends JpaRepository<Pedido, Long>{

}
