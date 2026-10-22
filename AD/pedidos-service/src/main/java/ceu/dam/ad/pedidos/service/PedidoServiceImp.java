package ceu.dam.ad.pedidos.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.ad.pedidos.model.Pedido;
import ceu.dam.ad.pedidos.repositorio.PedidoRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImp implements PedidoService{

	private final PedidoRepository repo;
	
	public PedidoServiceImp(PedidoRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Pedido consultarPedido(Long idPedido) throws PedidoException, PedidoNotFoundException {
		try {
			Pedido p = repo.findById(idPedido).orElseThrow(
					() -> new PedidoNotFoundException("Pedido no encontrado con el ID indicado" + idPedido));
			return p;
		} catch (DataAccessException e) {
			throw new PedidoException("Error al acceder a la bbdd", e);
		}
	}

	@Override
	@Transactional
	public Pedido crearPedido(Pedido pedido) throws PedidoException {
		try {
			BigDecimal sumaExenta = pedido.getArticulos().stream()
		            .filter(a -> a.getExentoIva())
		            .map(a -> a.getPrecioSinIva())
		            .reduce(BigDecimal.ZERO, BigDecimal::add);

		    BigDecimal sumaNoExento = pedido.getArticulos().stream()
		            .filter(a -> !a.getExentoIva())
		            .map(a -> a.getPrecioSinIva())
		            .reduce(BigDecimal.ZERO, BigDecimal::add);

		    BigDecimal subtotal = sumaExenta.add(sumaNoExento);
		    BigDecimal impuesto = sumaNoExento.multiply(BigDecimal.valueOf(0.21)).setScale(2);
		    BigDecimal total = subtotal.add(impuesto);

		    pedido.setSubtotal(subtotal);
		    pedido.setImpuestos(impuesto);
		    pedido.setTotal(total);

		    return repo.save(pedido);
		} catch (DataAccessException e) {
			throw new PedidoException("Error al acceder a la bbdd", e);
		}
	}

	@Override
	public List<Long> buscarPedidosArticulo(String codBarras) throws PedidoException, PedidoNotFoundException {
		try { 
			List<Long> listaPedidos = repo.findByArticulosCodBarras(codBarras).stream()
					.map(p -> p.getIdPedido())
					.distinct()
					.toList();
			if (listaPedidos.isEmpty()) {
				throw new PedidoNotFoundException("Pedido no encontrado con articulos con codigo de brras:" + codBarras);
			}
			return listaPedidos;
		} catch (DataAccessException e) {
			throw new PedidoException("Error al acceder a la bbdd", e);
		}
	}
}
