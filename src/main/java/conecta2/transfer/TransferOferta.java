package conecta2.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Particular;

/**
 * Transfer de Oferta
 * Se utiliza para enviar y recibir datos de las vistas
 */
public class TransferOferta {
	
	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * Filtro para evitar que se introduzcan nombres erróneos
	 */
	@NotEmpty(message = "* Por favor, introduzca el nombre de la oferta")
	@Length(max = 50, message = "* Por favor, el nombre no debe superar los 50 caracteres")
	private String nombre;
	
	@NotNull(message = "* Por favor, introduzca una jornada laboral válida")
	private JornadaLaboral jornada;
	
	@NotNull(message = "* Por favor, introduzca un contrato válido")
	private Contrato contrato;
	
	private Integer vacantes;
	
	private Double salario;

	/**
	 * Filtro para evitar que se introduzcan ciudades erróneas
	 */
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$", message="* Introduzca únicamente letras")
	private String ciudad;
	
	@Length(max = 1000, message = "* Por favor, la descripción no debe superar los 1000 caracteres")
	private String descripcion;
	
	private boolean activo;
	
	private boolean finalizada;
	
	@ManyToMany(mappedBy="ofertas", fetch=FetchType.LAZY)
	private List<Particular> particulares;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;
	
	
	/**
	 * Variables Auxiliares para el parseo de los campos númericos porque los doubles 
	 * no admiten patrones ni etiquetas de decimales como @DecimalMin
	 */
	@Pattern(regexp="^([0-9]{1,}(\\.[0-9]{1,}){0,1})*$", message= "* No puede introducir letras ni números negativos")
	private String auxSalario;
	
	/**
	 * Variables Auxiliares para el parseo de los campos númericos porque los int no admiten patrones
	 */
	@NotEmpty(message ="* Por favor, introduzca un número")
	@Pattern(regexp="^([0-9])*$", message= "* No puede introducir letras ni números negativos")
	private String auxVacantes;
	
	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public TransferOferta() {}
	
	public TransferOferta(String nombre, JornadaLaboral jornada, Contrato contrato, Integer vacantes, Double salario, String ciudad, String descripcion, boolean activo, boolean finalizada, Empresa empresa, List<Particular> particulares) {
		this.nombre = nombre;
		this.jornada = jornada;
		this.contrato = contrato;
		this.vacantes = vacantes;
		this.salario = salario;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.activo = activo;
		this.finalizada = finalizada;
		this.empresa = empresa;
		
		if(particulares == null)
			this.particulares = new ArrayList<Particular>();
		else
			this.particulares = particulares;
	}
	
	public TransferOferta(int id, String nombre, JornadaLaboral jornada, Contrato contrato, String auxVacantes, String auxSalario, String ciudad, String descripcion, boolean activo, boolean finalizada, Empresa empresa, List<Particular> particulares) {
		this.id = id;
		this.nombre = nombre;
		this.jornada = jornada;
		this.contrato = contrato;
		this.auxVacantes = auxVacantes;
		this.auxSalario = auxSalario;
		this.ciudad = ciudad;
		this.descripcion = descripcion;
		this.activo = activo;
		this.finalizada = finalizada;
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
	

	public String getAuxSalario() {
		return auxSalario;
	}

	/**
	 * Funcion que cambia la variable auxSalario por la enviada como parámetro y si es posible
	 * parsea el número para dar valor a la variable Salario del transfer, si no es posible o
	 * es un número negativo toma el valor null
	 * @param auxSalario
	 */
	public void setAuxSalario(String auxSalario) {
		this.auxSalario = auxSalario;
		try {
			this.salario = Double.parseDouble(auxSalario);
			if(this.salario < 0) this.salario = null;
		}
		catch(Exception e) {
			this.salario = null;
		}
	}

	public String getAuxVacantes() {
		return auxVacantes;
	}

	/**
	 * Funcion que cambia la variable auxVacantes por la enviada como parámetro y si es posible
	 * parsea el número para dar valor a la variable Vacantes del transfer, si no es posible o
	 * es un número negativo toma el valor null
	 * @param auxVacantes
	 */
	public void setAuxVacantes(String auxVacantes) {
		this.auxVacantes = auxVacantes;
		try {
			this.vacantes = Integer.parseInt(auxVacantes);
			if(this.vacantes < 0) this.vacantes = null;
		}
		catch(Exception e) {
			this.vacantes = null;
		}
	}

	public boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
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
