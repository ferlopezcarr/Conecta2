package conecta2.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import conecta2.transfer.TransferParticular;

@Entity
@Table(name = "particulares")
/**
 * Entidad / Objeto de Negocio de Particular
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
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$")
	private String nombre;
	
	@NotEmpty
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$")
	private String apellidos;
	
	/**
	 * Filtro para evitar que se introduzcan dnis erróneos
	 */
	@NotEmpty
	@Pattern(regexp="^[0-9]{8}[A-Z]{1}$")
	@Column(unique=true)
	private String dni;
	
	/**
	 * Filtro para evitar que se introduzcan telefonos erróneos
	 */
	@NotEmpty
	@Pattern(regexp="^([0-9]{9})*$")
	private String telefono;
	
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
	
	private String descripcion;
	
	private int puntuacion;
	
	private boolean activo;
	
	
	@ManyToMany
	private List<Oferta> ofertas;
	
	@OneToMany(mappedBy = "particular",fetch=FetchType.LAZY)
	private List<Notificacion> notificaciones;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;

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
	public Particular(String nombre, String apellidos, String dni, String telefono, String email, String password, String descripcion, int puntuacion, boolean activo, List<Oferta> ofertas, List<Notificacion> notificaciones) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.descripcion = descripcion;
		this.puntuacion = puntuacion;
		this.activo = activo;
		
		if(this.ofertas == null || ofertas == null) 
			this.ofertas = new ArrayList<Oferta>();
		else
			this.ofertas = ofertas;
		
		if(this.notificaciones == null || notificaciones == null) 
			this.notificaciones = new ArrayList<Notificacion>();
		else
			this.notificaciones = notificaciones;
	}
	
	/**
	 * Constructor por copia
	 * @param particular
	 */
	public Particular(Particular particular) {
		this.nombre = particular.nombre;
		this.apellidos = particular.apellidos;
		this.dni = particular.dni;
		this.telefono = particular.telefono;
		this.email = particular.email;
		this.password = particular.password;
		this.descripcion = particular.descripcion;
		this.puntuacion = particular.puntuacion;
		this.activo = particular.activo;
		
		if(this.ofertas == null || particular.ofertas == null) 
			this.ofertas = new ArrayList<Oferta>();
		else
			this.ofertas = particular.ofertas;
		
		if(this.notificaciones == null || particular.notificaciones == null) 
			this.notificaciones = new ArrayList<Notificacion>();
		else
			this.notificaciones = particular.notificaciones;
	}
	
	public static Particular TranferToEntity(TransferParticular transferParticular, int idParticular) {
		Particular particular = new Particular(
				transferParticular.getNombre(),
				transferParticular.getApellidos(),
				transferParticular.getDni(),
				transferParticular.getTelefono(),
				transferParticular.getEmail(),
				transferParticular.getPassword(),
				transferParticular.getDescripcion(),
				transferParticular.getPuntuacion(),
				transferParticular.getActivo(),
				transferParticular.getOfertas(),
				transferParticular.getNotificaciones()
				);
		particular.setId(idParticular);
		return particular;
	}
	
	public static Particular TranferToEntity(TransferParticular transferParticular) {
		return new Particular(
				transferParticular.getNombre(),
				transferParticular.getApellidos(),
				transferParticular.getDni(),
				transferParticular.getTelefono(),
				transferParticular.getEmail(),
				transferParticular.getPassword(),
				transferParticular.getDescripcion(),
				transferParticular.getPuntuacion(),
				transferParticular.getActivo(),
				transferParticular.getOfertas(),
				transferParticular.getNotificaciones()
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	public void anadirOferta(Oferta oferta) {
		
		if(this.ofertas == null)
			this.ofertas = new ArrayList<Oferta>();
		this.ofertas.add(oferta);		
		
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

    
}
