package conecta2.transfer;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import conecta2.modelo.Contrato;
import conecta2.modelo.JornadaLaboral;

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

	private String ccaa;
	
	@Length(max = 1000, message = "* Por favor, la descripción no debe superar los 1000 caracteres")
	private String descripcion;
	
	private boolean activo;
	
	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public TransferOferta() {}
	
	public TransferOferta(String nombre, JornadaLaboral jornada, Contrato contrato, int vacantes, double salario, String ccaa, String descripcion, boolean activo) {
		this.nombre = nombre;
		this.jornada = jornada;
		this.contrato = contrato;
		this.vacantes = vacantes;
		this.salario = salario;
		this.ccaa = ccaa;
		this.descripcion = descripcion;
		this.activo = activo;
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
	
}
