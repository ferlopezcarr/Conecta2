package conecta2.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.modelo.Puntuacion;

/**
 * Transfer de Particular
 * Se utiliza para enviar y recibir datos de las vistas
 */
public class TransferParticular {
	//Anotaciones para cuando los campos del formulario son incorrectos	
	
	/**
	 * Filtro para evitar que se introduzcan nombres erróneos
	 */
	@NotEmpty(message = "* Por favor, introduzca su nombre")
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$", message="* Introduzca únicamente letras")
	private String nombre;	
	
	/**
	 * Filtro para evitar que se introduzcan apellidos erróneos
	 */
	@NotEmpty(message = "* Por favor, introduzca su apellido")
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$", message="* Introduzca únicamente letras")
	private String apellidos;	
	
	/**
	 * Filtro para evitar que se introduzcan DNI erróneos
	 */
	@NotEmpty(message ="* Por favor, introduzca el DNI")
	@Pattern(regexp="^([0-9]{8}[A-Z]{1})*$", message= "* El DNI debe contener 8 dígitos y una letra mayúscula")
	private String dni;
	
	/**
	 * Filtro para evitar que se introduzcan teléfonos erróneos
	 */
	@NotEmpty(message ="* Por favor, introduzca el teléfono")
	@Pattern(regexp="^([0-9]{9})*$", message= "* Introduzca un teléfono válido")
	private String telefono;
	
	/**
	 * Filtro para evitar que se introduzcan emails erróneos
	 */
	@NotEmpty(message="* Por favor, introduzca un email")
	@Pattern(regexp="^([^@]+@[^@]+\\.[a-zA-Z]{2,})*$", message="* Por favor, introduzca un correo electrónico válido")
	private String email;
	
	/**
	 * Filtro para evitar que se introduzcan contraseñas erróneas
	 */
	@NotEmpty(message = "* Por favor, introduzca una contraseña")	
	@Pattern(regexp="^((?=\\w*\\d)(?=\\w*[A-Z])\\S{5,})*$", message="* La contraseña debe tener al menos un número y una mayúscula, y al menos 5 caracteres")
	private String password;		
	
	@NotEmpty(message = "* Por favor, introduzca de nuevo su contraseña")
	private String passwordConfirmacion;
	
	private String descripcion;
	
	@OneToMany(mappedBy = "empresa",fetch=FetchType.LAZY)
	private List<Puntuacion> puntuaciones;
	
	private boolean activo;
	
	@ManyToMany
	private List<Oferta> ofertas;
	
	@OneToMany(mappedBy = "particular",fetch=FetchType.LAZY)
	private List<Notificacion> notificaciones;
	
	public TransferParticular() {}
	
	public TransferParticular(String nombre, String apellidos, String dni, String telefono, String email, String password, String passwordConfirmacion, String descripcion, boolean activo, List<Oferta> ofertas, List<Notificacion> notificaciones) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.passwordConfirmacion = passwordConfirmacion;
		this.descripcion = descripcion;
		this.activo= activo;
		
		this.puntuaciones = new ArrayList<Puntuacion>();
		
		if(this.ofertas == null || ofertas == null) 
			this.ofertas = new ArrayList<Oferta>();
		else
			this.ofertas = ofertas;
		
		if(this.notificaciones == null || notificaciones == null) 
			this.notificaciones = new ArrayList<Notificacion>();
		else
			this.notificaciones = notificaciones;
	}
	
	public static TransferParticular EntityToTransfer(Particular particular) {
		return new TransferParticular(
				particular.getNombre(),
				particular.getApellidos(),
				particular.getDni(),
				particular.getTelefono(),
				particular.getEmail(),
				particular.getPassword(),
				particular.getPassword(),
				particular.getDescripcion(),
				particular.getActivo(),
				particular.getOfertas(),
				particular.getNotificaciones()
				);
	}
	
	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
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

	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
	}

	public List<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(List<Puntuacion> puntuaciones) {
		this.puntuaciones = puntuaciones;
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
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((notificaciones == null) ? 0 : notificaciones.hashCode());
		result = prime * result + ((ofertas == null) ? 0 : ofertas.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmacion == null) ? 0 : passwordConfirmacion.hashCode());
		result = prime * result + ((puntuaciones == null) ? 0 : puntuaciones.hashCode());
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
		TransferParticular other = (TransferParticular) obj;
		if (activo != other.activo)
			return false;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
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
		if (passwordConfirmacion == null) {
			if (other.passwordConfirmacion != null)
				return false;
		} else if (!passwordConfirmacion.equals(other.passwordConfirmacion))
			return false;
		if (puntuaciones == null) {
			if (other.puntuaciones != null)
				return false;
		} else if (!puntuaciones.equals(other.puntuaciones))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

}
