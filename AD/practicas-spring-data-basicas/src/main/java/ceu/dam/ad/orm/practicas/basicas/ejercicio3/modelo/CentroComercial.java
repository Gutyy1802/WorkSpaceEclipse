package ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class CentroComercial {

	@Id
	@Column(name="uuid_centro")
	private UUID id;
	@Column(name="nombre_comercial")
	private String nombre;
	private String direccion;
	@ManyToOne
	@JoinColumn(name="cod_pais", nullable = false)
	private Pais pais;
	@OneToMany
	@JoinColumn(name="uuid_centro", nullable = false)
	private List<Tienda> tiendas;
	@ManyToMany
	@JoinTable(name="centro_comercial_marcas",
			joinColumns = {@JoinColumn(name="uuid_centro")},
			inverseJoinColumns = {@JoinColumn(name="cod_pais")})
	private List<Marca> marcas;
}