package ceu.dam.ad.pedidos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.ad.pedidos.model.ArticuloPedido;
import ceu.dam.ad.pedidos.model.Pedido;
import ceu.dam.ad.pedidos.repositorio.ClienteArticulo;
import ceu.dam.ad.pedidos.repositorio.ClientePedido;
import jakarta.transaction.Transactional;

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
			Pedido p = repoPedido.findById(idPedido).orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con el ID indicado" + idPedido));
			
			p.setArticulos(repoArticulo.findByIdPedido(idPedido));
			
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

		    repoPedido.save(pedido);
			
			for (ArticuloPedido a  : pedido.getArticulos()) {
				a.setIdPedido(pedido.getIdPedido());
				
				repoArticulo.save(a);
			}
			
			return pedido;
		} catch (DataAccessException e) {
			throw new PedidoException("Error al acceder a la bbdd", e);
		}
	}

	@Override
	public List<Long> buscarPedidosArticulo(String codBarras) throws PedidoException, PedidoNotFoundException {
		try { 
			List<ArticuloPedido> listArticulo = repoArticulo.findByCodBarras(codBarras);
			List<Long> listIdPedidos = new ArrayList<Long>();
 
			if (listArticulo.isEmpty()) {
				throw new PedidoNotFoundException("No existen ningun articulo con ese codigo de barras");
			}
 
			for (ArticuloPedido a : listArticulo) {
				listIdPedidos.add(a.getIdPedido());				
			}
 
			return listIdPedidos;
		} catch (DataAccessException e) {
			throw new PedidoException("Error al acceder a la bbdd", e);
		}
	}
}
