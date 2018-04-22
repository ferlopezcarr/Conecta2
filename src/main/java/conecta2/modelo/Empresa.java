package conecta2.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

import conecta2.transfer.TransferEmpresa;

@Entity
@Table(name = "empresas")
/**
 * Entidad / Objeto de Negocio de Empresa
 * Se utiliza para persistir la información de la empresa
 */
public class Empresa {

	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@NotEmpty
	private String nombreEmpresa;
	
	/**
	 * Filtro para evitar que se introduzcan CIFs erróneos
	 */
	@NotEmpty
	@Pattern(regexp="^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]|[ABCDEFGHJKLMNPQRSUVW]){1}$")
	@Column(unique=true)
	private String cif;
	
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
	
	@OneToMany(mappedBy = "empresa",fetch=FetchType.LAZY)
	private List<Oferta> ofertas;
	
	@OneToMany(mappedBy = "empresa",fetch=FetchType.LAZY)
	private List<Notificacion> notificaciones;
	
	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public Empresa() {}
	
	/**
	 * Constructora por defecto que se utiliza para crear empresas en los tests
	 * @param nombre
	 * @param cif
	 * @param telefono
	 * @param email
	 * @param password
	 * @param activo
	 * @param puntuacion
	 * @param descripcion
	 */
	public Empresa(String nombreEmpresa, String cif, String telefono, String email, String password, String descripcion, int puntuacion, boolean activo, List<Oferta> ofertas, List<Notificacion> notificaciones) {
		this.nombreEmpresa = nombreEmpresa;
		this.cif = cif;
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

	public static Empresa TranferToEntity(TransferEmpresa transferEmpresa, int idEmpresa) {
		Empresa empresa = new Empresa(
				transferEmpresa.getNombreEmpresa(),
				transferEmpresa.getCif(),
				transferEmpresa.getTelefono(),
				transferEmpresa.getEmail(),
				transferEmpresa.getPassword(),
				transferEmpresa.getDescripcion(),
				transferEmpresa.getPuntuacion(),
				transferEmpresa.getActivo(),
				transferEmpresa.getOfertas(),
				transferEmpresa.getNotificaciones()
				);
		empresa.setId(idEmpresa);
		return empresa;
	}
	
	public static Empresa TranferToEntity(TransferEmpresa transferEmpresa) {
		return new Empresa(
				transferEmpresa.getNombreEmpresa(),
				transferEmpresa.getCif(),
				transferEmpresa.getTelefono(),
				transferEmpresa.getEmail(),
				transferEmpresa.getPassword(),
				transferEmpresa.getDescripcion(),
				transferEmpresa.getPuntuacion(),
				transferEmpresa.getActivo(),
				transferEmpresa.getOfertas(),
				transferEmpresa.getNotificaciones()
				);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

}
