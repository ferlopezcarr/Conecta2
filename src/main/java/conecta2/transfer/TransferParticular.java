package conecta2.transfer;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TransferParticular {
	//Anotaciones para cuando los campos del formulario son incorrectos	
	
	@NotEmpty(message = "* Por favor, introduzca su nombre")
	@Pattern(regexp="^[a-zA-Z ]*$", message="* El nombre debe contener solo letras")
	private String nombre;	
	
	@NotEmpty(message = "* Por favor, introduzca su apellido")
	@Pattern(regexp="^[a-zA-Z ]*$", message="* El apellido debe contener solo letras")
	private String apellidos;	
	
	@NotEmpty(message ="* Por favor, introduzca el DNI")
	@Pattern(regexp="^([0-9]{8}[A-Z]{1})*$", message= "* El DNI debe contener 8 dígitos y una letra mayúscula")
	private String dni;
	
	@NotEmpty(message="* Por favor, introduzca un email")
	//@Email
	@Pattern(regexp="^([^@]+@[^@]+\\.[a-zA-Z]{2,})*$", message="* Por favor, introduzca un correo electrónico válido")
	private String email;
	
	@NotEmpty(message = "* Por favor, introduzca una contraseña")	
	@Pattern(regexp="^((?=\\w*\\d)(?=\\w*[A-Z])\\S{5,})*$", message="* La contraseña debe tener al menos un número y una mayúscula, y al menos 5 caracteres")
	//@Length(min = 5, message="")
	private String password;		
	
	@NotEmpty(message = "* Por favor, introduzca de nuevo su contraseña")
	private String passwordConfirmacion;

	public TransferParticular() {}
	
	public TransferParticular(String nombre, String apellidos, String dni, String email, String password, boolean activo, int puntuacion ) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.password = password;
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

	public String getPasswordConfirmacion() {
		return passwordConfirmacion;
	}

	public void setPasswordConfirmacion(String passwordConfirmacion) {
		this.passwordConfirmacion = passwordConfirmacion;
	}	
}
