package conecta2.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.NotEmpty;

import conecta2.modelo.Empresa;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;

/**
 * Transfer de Empresa
 * Se utiliza para enviar y recibir datos de las vistas
 */
public class TransferEmpresa {
	//Anotaciones para cuando los campos del formulario son incorrectos
	
	@NotEmpty(message = "* Por favor, introduzca el nombre de la empresa")
	private String nombreEmpresa;
	
	/**
	 * Filtro para evitar que se introduzcan CIFs erróneos
	 */	
	@NotEmpty (message ="* Por favor, introduzca el CIF")
	@Pattern(regexp="^([ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]|[ABCDEFGHJKLMNPQRSUVW]){1})*$", message="* Por favor, introduzca un CIF válido (1 letra en mayúscula, 7 dígitos y 1 número o letra en mayúscula)")
	private String cif;
	
	/**
	 * Filtro para evitar que se introduzcan telefonos erróneos
	 */
	@NotEmpty(message ="* Por favor, introduzca el teléfono")
	@Pattern(regexp="^([0-9]{9})*$", message= "* Introduzca un teléfono válido")
	private String telefono;
	
	/**
	 * Filtro para evitar que se introduzcan emails erróneos
	 */
	@NotEmpty(message ="* Por favor, introduzca un email")
	@Pattern(regexp="^([^@]+@[^@]+\\.[a-zA-Z]{2,})*$", message="* Por favor, introduzca un correo electrónico válido")
	private String email;		
	
	/**
	 * Filtro para evitar que se introduzcan contraseñas erróneas
	 */
	@NotEmpty(message = "* Por favor, introduzca una contraseña")	
	@Pattern(regexp="^((?=\\w*\\d)(?=\\w*[A-Z])\\S{5,})*$", message="* La contraseña debe tener al menos un numero y una mayúscula y al menos 5 caracteres")
	private String password;	
		
	@NotEmpty(message = "* Por favor, introduzca de nuevo su contraseña")
	private String passwordConfirmacion;
	
	private String descripcion;
	
	private int puntuacion;
	
	private boolean activo;
	
	@OneToMany(mappedBy = "empresa",fetch=FetchType.LAZY)
	private List<Oferta> ofertas;
	
	@OneToMany(mappedBy = "empresa",fetch=FetchType.LAZY)
	private List<Notificacion> notificaciones;
	
	
	public TransferEmpresa() {}
		
	public TransferEmpresa(String nombreEmpresa, String cif, String telefono, String email, String password, String passwordConfirmation, String descripcion, int puntuacion, boolean activo, List<Oferta> ofertas, List<Notificacion> notificaciones) {
		this.nombreEmpresa = nombreEmpresa;
		this.cif = cif;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.passwordConfirmacion = passwordConfirmation;
		this.descripcion = descripcion;
		this.puntuacion = puntuacion;
		this.activo=activo;
		
		if(this.ofertas == null || ofertas == null) 
			this.ofertas = new ArrayList<Oferta>();
		else
			this.ofertas = ofertas;
		
		if(this.notificaciones == null || notificaciones == null) 
			this.notificaciones = new ArrayList<Notificacion>();
		else
			this.notificaciones = notificaciones;
	}
	
	public static TransferEmpresa EntityToTransfer(Empresa empresa) {
		return new TransferEmpresa(
				empresa.getNombreEmpresa(),
				empresa.getCif(),
				empresa.getTelefono(),
				empresa.getEmail(),
				empresa.getPassword(),
				empresa.getPassword(),
				empresa.getDescripcion(),
				empresa.getPuntuacion(),
				empresa.getActivo(),
				empresa.getOfertas(),
				empresa.getNotificaciones()
				);
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

	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
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
		result = prime * result + ((nombreEmpresa == null) ? 0 : nombreEmpresa.hashCode());
		result = prime * result + ((notificaciones == null) ? 0 : notificaciones.hashCode());
		result = prime * result + ((ofertas == null) ? 0 : ofertas.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((passwordConfirmacion == null) ? 0 : passwordConfirmacion.hashCode());
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
		TransferEmpresa other = (TransferEmpresa) obj;
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
		if (passwordConfirmacion == null) {
			if (other.passwordConfirmacion != null)
				return false;
		} else if (!passwordConfirmacion.equals(other.passwordConfirmacion))
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
