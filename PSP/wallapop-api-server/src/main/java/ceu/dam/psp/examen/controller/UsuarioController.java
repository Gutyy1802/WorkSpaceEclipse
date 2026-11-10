package ceu.dam.psp.examen.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.dam.psp.examen.dto.request.AutenticarUsuarioRequestDto;
import ceu.dam.psp.examen.dto.request.CrearArticuloRequestDto;
import ceu.dam.psp.examen.dto.request.CrearUsuarioRequestDto;
import ceu.dam.psp.examen.dto.response.AutenticarUsuarioResponseDto;
import ceu.dam.psp.examen.dto.response.BuscarArticuloResponseDto;
import ceu.dam.psp.examen.dto.response.ConsultarArticuloResponseDto;
import ceu.dam.psp.examen.dto.response.ConsultarUsuarioResponseDto;
import ceu.dam.psp.examen.dto.response.CrearArticuloResponseDto;
import ceu.dam.psp.examen.dto.response.CrearUsuarioResponseDto;
import ceu.dam.psp.examen.exceptions.NoAutorizadoException;
import ceu.dam.psp.examen.exceptions.NoExisteException;
import ceu.dam.psp.examen.exceptions.UsuarioDuplicadoException;
import ceu.dam.psp.examen.model.Articulo;
import ceu.dam.psp.examen.service.WallapopService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallapop")
public class UsuarioController {

	private final WallapopService service;
	private final ModelMapper mapper;
	
	UsuarioController(WallapopService service, ModelMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}
	
	@PostMapping("/usuarios")
	public CrearUsuarioResponseDto crearUsuario(@Valid @RequestBody CrearUsuarioRequestDto request) throws UsuarioDuplicadoException {
		request.setContraseña(DigestUtils.sha512_224Hex(request.getContraseña()));
		return mapper.map(service.crearUsuario(request.getIdentificador(),request.getDescripcion(), 
				request.getContraseña()), CrearUsuarioResponseDto.class);
	}
	
	@PostMapping("/login")
	public AutenticarUsuarioResponseDto autenticarUsuario(@Valid @RequestBody AutenticarUsuarioRequestDto request) throws NoAutorizadoException {
		request.setContraseña(DigestUtils.sha512_224Hex(request.getContraseña()));
		return mapper.map(service.autenticarUsuario(request.getIdentificador(), request.getContraseña()), AutenticarUsuarioResponseDto.class);
	}
	
	@GetMapping("/usuarios/id")
	public ConsultarUsuarioResponseDto consultarUsuario(String id) throws NoExisteException {
		return mapper.map(service.consultarUsuario(id), ConsultarUsuarioResponseDto.class);
	}
	
	@GetMapping("/articulos/id")
	public ConsultarArticuloResponseDto consultarArticulo(Long id) throws NoExisteException {
		return mapper.map(service.consultarArticulo(id), ConsultarArticuloResponseDto.class);
	}
	
	@PostMapping("/articulos")
	@Schema(description = "Crear articulo")
	public CrearArticuloResponseDto crearArticulo(CrearArticuloRequestDto request) throws NoExisteException {
		service.consultarUsuario(request.getIdPropietario());
		Articulo a = mapper.map(request, Articulo.class);
		a.setFechaPublicacion(LocalDate.now());
		a.setEstado(0);
		return mapper.map(service.crearArticulo(a), CrearArticuloResponseDto.class);
	}
	
	@GetMapping("/buscar/filtro/fechaMinima")
	public List<BuscarArticuloResponseDto> buscarArticulos(
			@RequestParam(required=false) String filtro,
			@RequestParam(required =false) 
			@DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaMinima) {
		
		
		//return mapper.map(service.buscarArticulos(filtro, filtro, fechaMinima), BuscarArticuloResponseDto.class);
		
		return null;
	}
	
}
//	public void comprarArticulo(Long codigo) {
//		
//	}