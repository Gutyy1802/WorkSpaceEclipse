package ceu.dam.ad.orm.practicas.basicas.ejercicio3.service;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataAccessException;

import ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo.CentroComercial;
import ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo.Marca;
import ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo.Pais;
import ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo.Tienda;
import ceu.dam.ad.orm.practicas.basicas.ejercicio3.repository.CentroComercialRepository;
import ceu.dam.ad.orm.practicas.basicas.ejercicio3.repository.MarcaRepository;
import ceu.dam.ad.orm.practicas.basicas.ejercicio3.repository.PaisRepository;

public class ComercialServiceImpl implements ComercialService{

	private final CentroComercialRepository repoCC;
	private final PaisRepository repoP;
	private final MarcaRepository repoM;
	
	public ComercialServiceImpl(CentroComercialRepository repoCC, PaisRepository repoP, MarcaRepository repoM) {
		this.repoCC = repoCC;
		this.repoP = repoP;
		this.repoM = repoM;
	}
	
	@Override
	public List<Pais> buscarPaises(String filtro) throws ComercialException {
		try {
			return repoP.findByDescripcion(filtro);
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
	}

	@Override
	public void insertarMarca(Marca marca) throws ComercialException {
		try {
			repoM.save(marca);
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
	}

	@Override
	public void insertarCentroComercial(CentroComercial cc) throws ComercialException {
		try {
			repoCC.save(cc);
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
	}

	@Override
	public CentroComercial consultarCentroComercial(String uuidCentro) throws ComercialException, NotFoundException {
		try {
			return repoCC.findById(UUID.fromString(uuidCentro));
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
	}

	@Override
	public Tienda consularTienda(Long idTienda) throws ComercialException, NotFoundException {
		try {
			
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
		return null;
	}

	@Override
	public void borrarTienda(Long idTienda) throws ComercialException {
		try {
			
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
	}

	@Override
	public void borrarCentroComercial(String uuidCentro) throws ComercialException {
		try {
			
		} catch (DataAccessException e) {
			throw new ComercialException("Error al acceder con la base de datos.", e);
		}
	}

}