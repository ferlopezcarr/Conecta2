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
		
		if(this.ofertas == null || ofertas == null) { 
			this.ofertas = new ArrayList<Oferta>();}
		else {
			this.ofertas = ofertas;
		}
		
		if(this.notificaciones == null || notificaciones == null) {
			this.notificaciones = new ArrayList<Notificacion>();}
		else {
			this.notificaciones = notificaciones;
		}
		
		
	}
	
	/**
	 * Constructor por copia
	 * @param empresa
	 */
	public Empresa(Empresa empresa) {
		this.nombreEmpresa = empresa.nombreEmpresa;
		this.cif = empresa.cif;
		this.telefono = empresa.telefono;
		this.email = empresa.email;
		this.password = empresa.password;
		this.descripcion = empresa.descripcion;
		this.puntuacion = empresa.puntuacion;
		this.activo = empresa.activo;
		
		if(this.ofertas == null || empresa.ofertas == null) 
			this.ofertas = new ArrayList<Oferta>();
		else
			this.ofertas = empresa.ofertas;
		
		if(this.notificaciones == null || empresa.notificaciones == null) 
			this.notificaciones = new ArrayList<Notificacion>();
		else
			this.notificaciones = empresa.notificaciones;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((cif == null) ? 0 : cif.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((nombreEmpresa == null) ? 0 : nombreEmpresa.hashCode());
		result = prime * result + ((notificaciones == null) ? 0 : notificaciones.hashCode());
		result = prime * result + ((ofertas == null) ? 0 : ofertas.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + puntuacion;
		result = prime * result + ((telefono == null) ? 0 : telefono.hashCode());
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
		Empresa other = (Empresa) obj;
		if (activo != other.activo)
			return false;
		if (cif == null) {
			if (other.cif != null)
				return false;
		} else if (!cif.equals(other.cif))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (nombreEmpresa == null) {
			if (other.nombreEmpresa != null)
				return false;
		} else if (!nombreEmpresa.equals(other.nombreEmpresa))
			return false;
		if (notificaciones == null) {
			if (other.notificaciones != null)
				return false;
		} else if (!notificaciones.equals(other.notificaciones))
			return false;
		if (ofertas == null) {
			if (other.ofertas != null)
				return false;
		} else if (!ofertas.equals(other.ofertas))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (puntuacion != other.puntuacion)
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}


}
