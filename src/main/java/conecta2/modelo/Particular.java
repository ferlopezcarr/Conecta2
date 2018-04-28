package conecta2.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@OneToMany(mappedBy = "empresa",fetch=FetchType.LAZY)
	private List<Puntuacion> puntuaciones;
	
	private boolean activo;
	
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Oferta> ofertasInscritos;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Oferta> ofertasSeleccionados;
	
	
	@OneToMany(mappedBy = "particular",fetch=FetchType.LAZY)
	private List<Notificacion> notificaciones;
	

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
	public Particular(String nombre, String apellidos, String dni, String telefono, String email, String password, String descripcion, boolean activo, List<Oferta> ofertasInscritos, List<Notificacion> notificaciones) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.descripcion = descripcion;
		this.activo = activo;
		
		this.puntuaciones = new ArrayList<Puntuacion>();
		
		this.ofertasSeleccionados = new ArrayList<Oferta>(); 
		
		if(this.ofertasInscritos == null || ofertasInscritos == null) 
			this.ofertasInscritos = new ArrayList<Oferta>();
		else
			this.ofertasInscritos = ofertasInscritos;
		
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
		this.puntuaciones = particular.puntuaciones;
		this.activo = particular.activo;
		
		if(this.ofertasInscritos == null || particular.ofertasInscritos == null) 
			this.ofertasInscritos = new ArrayList<Oferta>();
		else
			this.ofertasInscritos = particular.ofertasInscritos;
		
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

	public List<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(List<Puntuacion> puntuaciones) {
		this.puntuaciones = puntuaciones;
	}
	
	public void aniadirPuntuacion(Puntuacion puntuacion) {
		
		if(this.puntuaciones == null)
			this.puntuaciones = new ArrayList<Puntuacion>();
		this.puntuaciones.add(puntuacion);
		
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Oferta> getOfertas() {
		return ofertasInscritos;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertasInscritos = ofertas;
	}
	
	public void anadirOferta(Oferta oferta) {
		
		if(this.ofertasInscritos == null)
			this.ofertasInscritos = new ArrayList<Oferta>();
		this.ofertasInscritos.add(oferta);		
		
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public List<Oferta> getOfertasSeleccionados() {
		return ofertasSeleccionados;
	}

	public void setOfertasSeleccionados(List<Oferta> ofertasSeleccionados) {
		this.ofertasSeleccionados = ofertasSeleccionados;
	}

	public List<Oferta> getOfertasInscritos() {
		return ofertasInscritos;
	}

	public void setOfertasInscritos(List<Oferta> ofertasInscritos) {
		this.ofertasInscritos = ofertasInscritos;
	}

	public double getPuntuacionMedia()  {
		
		double puntosTotales = 0;
		int numValoraciones =  this.getPuntuaciones().size();
		double puntuacionMedia = 0;
		
		if(numValoraciones != 0) {
			for(int j = 0; j < numValoraciones; j++) {
				puntosTotales += this.getPuntuaciones().get(j).getPuntuacion();
			}
			
			puntuacionMedia = puntosTotales / numValoraciones;
			
			puntuacionMedia = Math.rint(puntuacionMedia*100)/100;
		}

		return puntuacionMedia;
	}
    
}
