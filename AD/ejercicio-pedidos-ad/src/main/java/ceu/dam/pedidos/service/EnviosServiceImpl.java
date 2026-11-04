package ceu.dam.pedidos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.pedidos.exception.EnvioNoEncontradoException;
import ceu.dam.pedidos.exception.EstadoEnvioInvalidoException;
import ceu.dam.pedidos.exception.PoliticaTransporteInvalidaException;
import ceu.dam.pedidos.model.Envio;
import ceu.dam.pedidos.repository.EnvioRepository;
import jakarta.transaction.Transactional;

@Service
public class EnviosServiceImpl implements EnviosService{

	private final EnvioRepository repo;

	EnviosServiceImpl(EnvioRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Envio> detectarEnviosEnRiesgoTermico(double limiteTemperatura) {
		try {
			if (limiteTemperatura >= 50 || limiteTemperatura <= -50) {
				throw new IllegalArgumentException("Limite de temperatura absurda: " + limiteTemperatura);
			}
			return repo.findByTemperaturaCelsiusGreaterThanOrTemperaturaCelsiusLessThan(limiteTemperatura, limiteTemperatura);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public long actualizarEstadoLoteReciente(List<String> codigos, String nuevoEstado)
			throws EnvioNoEncontradoException {
		
		if (codigos == null || codigos.isEmpty()) {
		    throw new IllegalArgumentException("La lista de códigos esta vacia");
		}
		if (nuevoEstado == null) {
		    throw new IllegalArgumentException("El estado esta vacio");
		}

		List<Envio> envios = repo.findByCodigoSeguimientoIn(codigos);
		List<String> codigosEncontrados = new ArrayList<>();

		for (Envio e : envios) {
		    codigosEncontrados.add(e.getCodigoSeguimiento());
		}
		if (!codigosEncontrados.containsAll(codigos)) {
		    throw new EnvioNoEncontradoException("Algun codigo de seguimiento no existe");
		}

		LocalDateTime fechaLimite = LocalDateTime.now().minusHours(24);
		List<Envio> enviosActualizar = new ArrayList<>(); 

		for (Envio e : envios) {
			if (e.getFechaRegistro() != null &&
				!e.getFechaRegistro().isBefore(fechaLimite)) {
		            e.setEstado(nuevoEstado);
		            enviosActualizar.add(e);
		    }
		}

		repo.saveAll(enviosActualizar);
		return enviosActualizar.size();
	}

	@Override
	public Envio registrarNuevoEnvio(Envio nuevoEnvio)
			throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {
		try {
			if (nuevoEnvio.getPesoKg() > 1500) {
				throw new PoliticaTransporteInvalidaException("Peso de envia superior a 1500.");
			}
			if (nuevoEnvio.getEstado().equalsIgnoreCase("entregado") || nuevoEnvio == null) {
				throw new EstadoEnvioInvalidoException("No se puede crear directamente como entregado o null.");
			}
			
			nuevoEnvio.setCodigoSeguimiento(UUID.randomUUID().toString());
			repo.save(nuevoEnvio);
			return repo.findById(nuevoEnvio.getId()).get();
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public Envio obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {
		try {
			Envio envio = repo.findFirstByEstadoOrderByPesoKgDesc("PENDIENTE");
			if (envio == null) {
				throw new EstadoEnvioInvalidoException("No hay ningun pedido en estado pendiente");
			}
			return envio;
		} catch (DataAccessException e) {
			throw e;
		}
	}
}