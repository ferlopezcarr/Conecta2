package conecta2.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "ofertas")
public class Oferta {
	
	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	@Length(max = 50)
	private String nombre;
	
	@NotEmpty
	private JornadaLaboral jornada;
	
	@NotEmpty
	private Contrato contrato;
	
	@NotEmpty
	@Range(min=1)
	private int vacantes;
	
	private double salario;

	private String ccaa;
	
	@Length(max = 1000)
	private String descripcion;
	
	private boolean activo;
	
	@ManyToMany(mappedBy="ofertas", fetch=FetchType.LAZY)
	private List<Particular> particulares;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;

	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public Oferta() {}
	
	public Oferta(String nombre, JornadaLaboral jornada, Contrato contrato, int vacantes, double salario, String ccaa, String descripcion, boolean activo) {
		this.nombre = nombre;
		this.jornada = jornada;
		this.contrato = contrato;
		this.vacantes = vacantes;
		this.salario = salario;
		this.ccaa = ccaa;
		this.descripcion = descripcion;
		this.activo = activo;
		this.particulares = new ArrayList<Particular>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public JornadaLaboral getJornadaLaboral() {
		return jornada;
	}

	public void setJornadaLaboral(JornadaLaboral jornada) {
		this.jornada = jornada;
	}
	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	public int getVacantes() {
		return vacantes;
	}

	public void setVacantes(int vacantes) {
		this.vacantes = vacantes;
	}
	
	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	public String getCcaa() {
		return ccaa;
	}

	public void setCcaa(String ccaa) {
		this.ccaa = ccaa;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public List<Particular> getParticulares() {
		return particulares;
	}

	public void setParticulares(List<Particular> particulares) {
		this.particulares = particulares;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
}
