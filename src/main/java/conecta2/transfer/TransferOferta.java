package conecta2.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Particular;

public class TransferOferta {
	
	@NotEmpty(message = "* Por favor, introduzca el nombre de la oferta")
	@Length(max = 50, message = "* Por favor, el nombre no debe superar los 50 caracteres")
	private String nombre;
	
	@NotEmpty(message = "* Por favor, introduzca una jornada laboral válida")
	private JornadaLaboral jornada;
	
	@NotEmpty(message = "* Por favor, introduzca un contrato válido")
	private Contrato contrato;
	
	@NotEmpty(message = "* Por favor, introduzca el número de vacantes")
	@Range(min=1, message = "* Por favor, debe haber al menos una vacante")
	private int vacantes;
	
	private double salario;

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
	
	public TransferOferta(String nombre, JornadaLaboral jornada, Contrato contrato, int vacantes, double salario, String ciudad, String descripcion, boolean activo, Empresa empresa, List<Particular> particulares) {
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
