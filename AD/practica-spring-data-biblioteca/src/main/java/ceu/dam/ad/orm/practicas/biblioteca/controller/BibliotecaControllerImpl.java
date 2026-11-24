package ceu.dam.ad.orm.practicas.biblioteca.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ceu.dam.ad.orm.practicas.biblioteca.dto.request.BuscarLibroRequestDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.request.altaLibroRequestDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.AltaLibroResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.BuscarLibroResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.ConsultarSocioResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.dto.response.altaSocioResponseDto;
import ceu.dam.ad.orm.practicas.biblioteca.model.Carnet;
import ceu.dam.ad.orm.practicas.biblioteca.model.Libro;
import ceu.dam.ad.orm.practicas.biblioteca.model.Prestamo;
import ceu.dam.ad.orm.practicas.biblioteca.model.Socio;
import ceu.dam.ad.orm.practicas.biblioteca.service.BibliotecaException;
import ceu.dam.ad.orm.practicas.biblioteca.service.BibliotecaService;
import ceu.dam.ad.orm.practicas.biblioteca.service.NotFoundException;
import jakarta.validation.Valid;

@RestController
public class BibliotecaControllerImpl implements BibliotecaController{

	private BibliotecaService service;
	private ModelMapper mapper;

	@Override
	@PostMapping("/socio")
	public altaSocioResponseDto altaSocio(@Valid @RequestBody altaLibroRequestDto socio) throws BibliotecaException {
		return mapper.map(service.altaSocio(mapper.map(socio, Socio.class)), altaSocioResponseDto.class);
	}

	@Override
	@GetMapping("/socio/id")
	public ConsultarSocioResponseDto consultarSocio(@RequestParam String dni) throws NotFoundException {
		return mapper.map(service.consultarSocio(dni), ConsultarSocioResponseDto.class);
	}

	@Override
	@PostMapping("/libro")
	public AltaLibroResponseDto altaLibro(@Valid @RequestBody altaLibroRequestDto libro) throws BibliotecaException {
		return mapper.map(service.altaLibro(mapper.map(libro, Libro.class)), AltaLibroResponseDto.class);
	}

	@Override
	@GetMapping("/libro")
	public List<BuscarLibroResponseDto> buscarLibros(@RequestParam BuscarLibroRequestDto ejemplo) {
		List<Libro> listaLibros = service.buscarLibros(mapper.map(ejemplo, Libro.class));
		List<BuscarLibroResponseDto> list = new ArrayList<BuscarLibroResponseDto>();
		for (Libro libro : listaLibros) {
			list.add(mapper.map(libro, BuscarLibroResponseDto.class));
		}
		return list;
	}

	@Override
	@PutMapping("/libro")
	public Prestamo prestarLibro(@RequestParam String dniSocio,@RequestParam Long idLibro) throws NotFoundException, BibliotecaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void devolverLibro(Integer anio, Integer numPrestamo) throws NotFoundException, BibliotecaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Carnet renovarCarnet(String dniSocio) throws NotFoundException, BibliotecaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prestamo> consultarPrestamosActivos(String dniSocio) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
