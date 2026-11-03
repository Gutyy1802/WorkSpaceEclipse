package ceu.dam.pedidos.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ceu.dam.pedidos.exception.EnvioNoEncontradoException;
import ceu.dam.pedidos.exception.EstadoEnvioInvalidoException;
import ceu.dam.pedidos.exception.PoliticaTransporteInvalidaException;
import ceu.dam.pedidos.model.Envio;
import ceu.dam.pedidos.repository.EnvioRepository;

@Service
public class EnviosServiceImpl implements EnviosService{

	private final EnvioRepository repo;

	EnviosServiceImpl(EnvioRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Envio> detectarEnviosEnRiesgoTermico(double limiteTemperatura) {
		try {
			if (limiteTemperatura >= 50 || limiteTemperatura <= 50) {
				throw new IllegalArgumentException("Limite de temperatura absurda: " + limiteTemperatura);
			}
			return repo.findByTemperaturaCelsiusGreaterThanOrLessThan(limiteTemperatura);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public long actualizarEstadoLoteReciente(List<String> codigos, String nuevoEstado)
			throws EnvioNoEncontradoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Envio registrarNuevoEnvio(Envio nuevoEnvio)
			throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Envio obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {
		// TODO Auto-generated method stub
		return null;
	}

}
