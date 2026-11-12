package ceu.dam.ad.orm.practicas.basicas.ejercicio3.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="marcas")
public class Marca {
	
	@Id
	private String codigo;
	private String nombreComercial;
	@ManyToOne
	@JoinColumn(name = "cod_pais", nullable = false)
	private Pais pais;
	@ManyToMany(mappedBy = "marcas")
	private List<CentroComercial> centrosComerciales;
	
	
	public Marca() {
		super();
	}
	public Marca(String codigo, String nombreComercial, Pais pais) {
		super();
		this.codigo = codigo;
		this.nombreComercial = nombreComercial;
		this.pais = pais;
	}
}