package ceu.dam.pedidos.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ceu.dam.pedidos.dto.response.EnvioDto;
import ceu.dam.pedidos.exception.EnvioNoEncontradoException;
import ceu.dam.pedidos.exception.EstadoEnvioInvalidoException;
import ceu.dam.pedidos.exception.PoliticaTransporteInvalidaException;
import ceu.dam.pedidos.model.Envio;
import ceu.dam.pedidos.service.EnviosService;

@RestController
@RequestMapping("/envio")
public class EnvioController {

	final EnviosService service;
	private final ModelMapper mapper;
	
	public EnvioController(EnviosService service) {
		this.service = service;
		this.mapper = new ModelMapper();
	}

	@GetMapping("/{limiteTemperatura}")
	public List<EnvioDto> detectarEnviosEnRiesgoTermico(@PathVariable double limiteTemperatura) {

	    return service.detectarEnviosEnRiesgoTermico(limiteTemperatura)
	            .stream()
	            .map(e -> mapper.map(e, EnvioDto.class))
	            .toList();
	}
	
	@PutMapping("/{nuevoEstado}")
	public long actualizarEstadoLoteReciente(@RequestBody List<String> codigos,@PathVariable String nuevoEstado) throws EnvioNoEncontradoException {
		return service.actualizarEstadoLoteReciente(codigos, nuevoEstado);
	}
	
	@PostMapping
	public EnvioDto registrarNuevoEnvio(@RequestBody Envio nuevoEnvio) throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException {
		return mapper.map(service.registrarNuevoEnvio(nuevoEnvio), EnvioDto.class);
	}
	
	@GetMapping("/maxPesado")
	public EnvioDto obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException {
		return mapper.map(service.obtenerEnvioPesadoPrioritario(), EnvioDto.class);
	}
}