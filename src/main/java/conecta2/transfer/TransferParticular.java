package conecta2.transfer;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TransferParticular {
	//Anotaciones para cuando los campos del formulario son incorrectos	
	@NotEmpty(message = "* Por favor, introduzca su nombre")
	private String nombre;
	
	@NotEmpty(message = "* Por favor, introduzca su apellido")
	private String apellidos;
	
	@NotEmpty
	@Pattern(regexp="^[0-9]{8}[A-Z]{1}$", message="* Por favor, introduzca un DNI válido (8 dígitos y una letra en mayúscula)")
	private String dni;
	
	@NotEmpty
	@Email
	@Pattern(regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message="* Por favor, introduzca un correo electrónico válido")
	private String email;

	@NotEmpty(message = "* Por favor, introduzca una contraseña")
	@Length(min = 5)
	@Pattern(regexp="^(?=\\w*\\d)(?=\\w*[A-Z])\\S{5,}$", message="* La contraseña debe tener al menos un numero y una mayúscula y al menos 5 caracteres")
	private String password;
	
	@NotEmpty(message = "* Por favor, introduzca de nuevo su contraseña")
	private String passwordConfirmacion;

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
