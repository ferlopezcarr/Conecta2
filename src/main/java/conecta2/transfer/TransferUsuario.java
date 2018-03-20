package conecta2.transfer;

import java.util.Set;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import conecta2.modelo.Rol;


public class TransferUsuario {
	//Anotaciones para cuando los campos del formulario son incorrectos
	@NotEmpty(message = "* Por favor, introduzca su nombre")
	private String nombre;
	
	@NotEmpty(message = "* Por favor, introduzca su apellido")
	private String apellidos;	
	
	@Email(message = "* Por favor, introduzca un correo electrónico válido")
	@NotEmpty(message = "* Por favor, introduzca un correo electrónico")
	private String email;

	@Length(min = 6, message = "* La contraseña deberá tener al menos 6 caracteres")
	@NotEmpty(message = "* Por favor, introduzca una contraseña")
	private String password;
	
	@NotEmpty(message = "* Por favor, introduzca una contraseña")
	private String confirmarPassword;
	
	private Rol rol;
	
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getConfirmarPassword() {
		return confirmarPassword;
	}

	public void setConfirmarPassword(String confirmarPassword) {
		this.confirmarPassword = confirmarPassword;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}	
}
