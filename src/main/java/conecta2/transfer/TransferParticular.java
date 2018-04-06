package conecta2.transfer;

import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.NotEmpty;

public class TransferParticular {
	//Anotaciones para cuando los campos del formulario son incorrectos	
	
	@NotEmpty(message = "* Por favor, introduzca su nombre")
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$", message="* El nombre debe contener solo letras")
	private String nombre;	
	
	@NotEmpty(message = "* Por favor, introduzca su apellido")
	@Pattern(regexp="^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$", message="* El apellido debe contener solo letras")
	private String apellidos;	
	
	@NotEmpty(message ="* Por favor, introduzca el DNI")
	@Pattern(regexp="^([0-9]{8}[A-Z]{1})*$", message= "* El DNI debe contener 8 dígitos y una letra mayúscula")
	private String dni;
	
	@NotEmpty(message ="* Por favor, introduzca el teléfono")
	@Pattern(regexp="^([0-9]{9})*$", message= "* Introduzca un teléfono válido")
	private String telefono;
	
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

	private boolean activo;
	
	private int puntuacion;
	
	private String descripcion;
	
	public TransferParticular() {}
	
	public TransferParticular(String nombre, String apellidos, String dni, String telefono, String email, String password, boolean activo, int puntuacion, String descripcion) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.activo= activo;
		this.descripcion = descripcion;
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
}
