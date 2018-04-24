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
import conecta2.modelo.Oferta;
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
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;
	
	@ManyToMany(mappedBy="ofertas", fetch=FetchType.LAZY)
	private List<Particular> particulares;
	
	//Variables auxiliares
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
	
	//Métodos
	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public TransferOferta() {}
	
	public TransferOferta(int id, String nombre, JornadaLaboral jornada, Contrato contrato, Integer vacantes, Double salario, String ciudad, String descripcion, boolean activo, boolean finalizada, Empresa empresa, List<Particular> particulares) {
		this.id = id;
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
		
		if(this.particulares == null || particulares == null) 
			this.particulares = new ArrayList<Particular>();
		else
			this.particulares = particulares;
	}
	
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
		
		if(this.particulares == null || particulares == null) 
			this.particulares = new ArrayList<Particular>();
		else
			this.particulares = particulares;
	}
	
	public TransferOferta(String nombre, JornadaLaboral jornada, Contrato contrato, String auxVacantes, String auxSalario, String ciudad, String descripcion, boolean activo, boolean finalizada, Empresa empresa, List<Particular> particulares) {
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
		
		if(this.particulares == null || particulares == null) 
			this.particulares = new ArrayList<Particular>();
		else
			this.particulares = particulares;
	}
	
	public static TransferOferta EntityToTransfer(Oferta oferta) {
		return new TransferOferta(
				oferta.getNombre(),
				oferta.getJornadaLaboral(),
				oferta.getContrato(),
				oferta.getVacantes(),
				oferta.getSalario(),
				oferta.getCiudad(),
				oferta.getDescripcion(),
				oferta.getActivo(),
				oferta.getFinalizada(),
				oferta.getEmpresa(),
				oferta.getParticularesInscritos()
				);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((auxSalario == null) ? 0 : auxSalario.hashCode());
		result = prime * result + ((auxVacantes == null) ? 0 : auxVacantes.hashCode());
		result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
		result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + (finalizada ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((jornada == null) ? 0 : jornada.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((particulares == null) ? 0 : particulares.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		result = prime * result + ((vacantes == null) ? 0 : vacantes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferOferta other = (TransferOferta) obj;
		if (activo != other.activo)
			return false;
		if (auxSalario == null) {
			if (other.auxSalario != null)
				return false;
		} else if (!auxSalario.equals(other.auxSalario))
			return false;
		if (auxVacantes == null) {
			if (other.auxVacantes != null)
				return false;
		} else if (!auxVacantes.equals(other.auxVacantes))
			return false;
		if (ciudad == null) {
			if (other.ciudad != null)
				return false;
		} else if (!ciudad.equals(other.ciudad))
			return false;
		if (contrato != other.contrato)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (finalizada != other.finalizada)
			return false;
		if (id != other.id)
			return false;
		if (jornada != other.jornada)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (particulares == null) {
			if (other.particulares != null)
				return false;
		} else if (!particulares.equals(other.particulares))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
			return false;
		if (vacantes == null) {
			if (other.vacantes != null)
				return false;
		} else if (!vacantes.equals(other.vacantes))
			return false;
		return true;
	}
	
}
