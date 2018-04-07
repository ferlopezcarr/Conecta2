package conecta2.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Particular;

public class TransferOferta {
	
	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message = "* Por favor, introduzca el nombre de la oferta")
	@Length(max = 50, message = "* Por favor, el nombre no debe superar los 50 caracteres")
	private String nombre;
	
	@NotNull(message = "* Por favor, introduzca una jornada laboral válida")
	private JornadaLaboral jornada;
	
	@NotNull(message = "* Por favor, introduzca un contrato válido")
	private Contrato contrato;
	
	@Digits(integer = 3, fraction = 0, message = "* Por favor, el número de vacantes debe ser un entero")
	@Min(value = 1, message = "* Por favor, debe haber al menos una vacante")
	@Max(value = 999, message = "* Por favor, sólo puede haber como máximo 999 vacantes")
	private Integer vacantes;
	
	@DecimalMin(value = "0.00", message = "* Por favor, debe introducir un número positivo")
	private Double salario;

	private String ciudad;
	
	@Length(max = 1000, message = "* Por favor, la descripción no debe superar los 1000 caracteres")
	private String descripcion;
	
	private boolean activo;
	
	@ManyToMany(mappedBy="ofertas", fetch=FetchType.LAZY)
	private List<Particular> particulares;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;
	
	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public TransferOferta() {}
	
	public TransferOferta(String nombre, JornadaLaboral jornada, Contrato contrato, Integer vacantes, Double salario, String ciudad, String descripcion, boolean activo, Empresa empresa, List<Particular> particulares) {
		this.nombre = nombre;
		this.jornada = jornada;
		this.contrato = contrato;
		this.vacantes = vacantes;
		this.salario = salario;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.activo = activo;
		this.empresa = empresa;
		
		if(particulares == null)
			this.particulares = new ArrayList<Particular>();
		else
			this.particulares = particulares;
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

	public JornadaLaboral getJornada() {
		return jornada;
	}

	public void setJornada(JornadaLaboral jornada) {
		this.jornada = jornada;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Integer getVacantes() {
		return vacantes;
	}

	public void setVacantes(Integer vacantes) {
		this.vacantes = vacantes;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
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

	public boolean containsJornada(String text) {
		
	    for (JornadaLaboral j : JornadaLaboral.values()) {
	        if (j.name().equals(text)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public boolean containsContrato(String text) {
		
	    for (Contrato j : Contrato.values()) {
	        if (j.name().equals(text)) {
	            return true;
	        }
	    }

	    return false;
	}
	
}
