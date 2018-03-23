package conecta2.transfer;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TransferEmpresa {
	//Anotaciones para cuando los campos del formulario son incorrectos	
		@NotEmpty(message = "* Por favor, introduzca su nombre")
		private String nombre;
				
		@NotEmpty(message = "* Por favor, introduzca su cif")
		@Pattern(regexp="^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]|[ABCDEFGHJKLMNPQRSUVW]){1}$", message="* Por favor, introduzca un CIF válido (1 letra en mayúscula, 7 dígitos y 1 número o letra en mayúscula")
		private String cif;
		
		@NotEmpty(message = "* Por favor, introduzca un correo electrónico")
		@Email(message = "* Por favor, introduzca un correo electrónico válido")
		@Pattern(regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message="* Por favor, introduzca un correo electrónico válido")
		private String email;

		@NotEmpty(message = "* Por favor, introduzca una contraseña")
		@Length(min = 5, message = "* La contraseña deberá tener al menos 5 caracteres")
		@Pattern(regexp="^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S", message="* La contraseña debe tener al menos un numero y una mayúscula")
		private String password;
		
		@NotEmpty(message = "* Por favor, introduzca una contraseña")
		private String passwordConfirmacion;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
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
}
