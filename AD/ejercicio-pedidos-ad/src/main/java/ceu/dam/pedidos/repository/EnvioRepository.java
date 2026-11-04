package ceu.dam.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.pedidos.model.Envio;
import java.util.List;


public interface EnvioRepository extends JpaRepository<Envio, Long>{
	List<Envio> findByTemperaturaCelsiusGreaterThanOrTemperaturaCelsiusLessThan(
		    double limiteSuperior, double limiteInferior);
	
	List<Envio> findByCodigoSeguimientoIn(List<String> codigos);
	
	Envio findFirstByEstadoOrderByPesoKgDesc(String estado);
}