package conecta2.transfer;

import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.NotEmpty;

public class TransferEmpresa {
	//Anotaciones para cuando los campos del formulario son incorrectos
	
	@NotEmpty(message = "* Por favor, introduzca el nombre de la empresa")
	private String nombreEmpresa;
				
	@NotEmpty (message ="* Por favor, introduzca el CIF")
	@Pattern(regexp="^([ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]|[ABCDEFGHJKLMNPQRSUVW]){1})*$", message="* Por favor, introduzca un CIF válido (1 letra en mayúscula, 7 dígitos y 1 número o letra en mayúscula)")
	private String cif;
				
	@NotEmpty(message ="* Por favor, introduzca un email")
	//@Email
	@Pattern(regexp="^([^@]+@[^@]+\\.[a-zA-Z]{2,})*$", message="* Por favor, introduzca un correo electrónico válido")
	private String email;		
		
	@NotEmpty(message = "* Por favor, introduzca una contraseña")	
	//@Length(min = 5, message="")
	@Pattern(regexp="^((?=\\w*\\d)(?=\\w*[A-Z])\\S{5,})*$", message="* La contraseña debe tener al menos un numero y una mayúscula y al menos 5 caracteres")
	private String password;	
		
	@NotEmpty(message = "* Por favor, introduzca de nuevo su contraseña")
	private String passwordConfirmacion;

	private boolean activo;
	
	private int puntuacion;
	
	public TransferEmpresa() {}
		
	public TransferEmpresa(String nombreEmpresa, String cif, String email, String password, String passwordConfirmation, boolean activo) {
		this.nombreEmpresa = nombreEmpresa;
		this.cif = cif;
		this.email = email;
		this.password = password;
		this.passwordConfirmacion = passwordConfirmation; 
		this.activo=activo;
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

	public boolean isActivo() {
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
