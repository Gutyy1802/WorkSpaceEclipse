package ceu.dam.ad.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.ad.pedidos.model.Pedido;
import ceu.dam.ad.pedidos.repositorio.ClienteArticulo;
import ceu.dam.ad.pedidos.repositorio.ClientePedido;

@Service
public class PedidoServiceImp implements PedidoService{

	private final ClientePedido repoPedido;
	private final ClienteArticulo repoArticulo;
	
	public PedidoServiceImp(ClientePedido repo, ClienteArticulo repoArticulo) {
		this.repoPedido = repo;
		this.repoArticulo = repoArticulo;
	}
	
	@Override
	public Pedido consultarPedido(Long idPedido) throws PedidoException, PedidoNotFoundException {
		try {
			Optional<Pedido> pedidoOpt = repoPedido.findById(idPedido);
			
			if (pedidoOpt.isEmpty()) {
				throw new PedidoNotFoundException("Pedido no encontrado con el ID indicado" + idPedido);
			}
			
			Pedido p = pedidoOpt.get();
			p.setArticulos(repoArticulo.findByIdPedido(idPedido));
			
			return p;
		} catch (DataAccessException e) {
			throw new PedidoException("Error", e);
		}
	}

	@Override
	public Pedido crearPedido(Pedido pedido) throws PedidoException {
		try {
			repoPedido.save(pedido);
		} catch (DataAccessException e) {
			throw new PedidoException("Error", e);
		}
		return null;
	}

	@Override
	public List<Long> buscarPedidosArticulo(String codBarras) throws PedidoException, PedidoNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
