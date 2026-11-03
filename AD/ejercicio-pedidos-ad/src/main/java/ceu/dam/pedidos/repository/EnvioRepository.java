package ceu.dam.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ceu.dam.pedidos.model.Envio;
import java.util.List;


public interface EnvioRepository extends JpaRepository<Envio, Long>{
	List<Envio> findByTemperaturaCelsiusGreaterThanOrLessThan(double temperaturaCelsius);
}
