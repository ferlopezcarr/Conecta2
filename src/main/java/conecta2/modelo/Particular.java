package conecta2.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "particulares")
/**
 * Entidad / Objeto de Negocio de Particular
 * @author ferlo
 * Se utiliza para persistir la información del particular
 */
public class Particular {
	
	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	//@Pattern(regexp="^([a-zA-Z ])*$")
	private String nombre;
	
	@NotEmpty
	//@Pattern(regexp="^([a-zA-Z ])*$")
	private String apellidos;
	
	/**
	 * Filtro para evitar que se introduzcan dnis erróneos
	 */
	@NotEmpty
	@Pattern(regexp="^[0-9]{8}[A-Z]{1}$")
	@Column(unique=true)
	private String dni;
	
	/**
	 * Filtro para evitar que se introduzcan emails erróneos
	 */
	@Email
	@Pattern(regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")
	@NotEmpty
	@Column(unique=true)
	private String email;
	
	@Length(min = 5)
	@NotEmpty
	@Transient
	private String password;


	private boolean activo;
	
	
	private int puntuacion;

	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public Particular() {}
	
	/**
	 * Constructora por defecto que se utiliza para crear particulates en los tests
	 * @param nombre
	 * @param apellidos
	 * @param dni
	 * @param email
	 * @param password
	 * @param activo
	 * @param puntuacion
	 */
	public Particular(String nombre, String apellidos, String dni, String email, String password, boolean activo, int puntuacion ) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.password = password;
		this.activo = activo;
		this.puntuacion = puntuacion;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
}
