package conecta2.transfer;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TransferEmpresa {
	//Anotaciones para cuando los campos del formulario son incorrectos	
		@NotEmpty(message = "* Por favor, introduzca su nombre")
		private String nombre;
				
		@NotEmpty
		@Pattern(regexp="^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]|[ABCDEFGHJKLMNPQRSUVW]){1}$", message="* Por favor, introduzca un CIF válido (1 letra en mayúscula, 7 dígitos y 1 número o letra en mayúscula)")
		private String cif;
		
		@NotEmpty
		@Email
		@Pattern(regexp="^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message="* Por favor, introduzca un correo electrónico válido")
		private String email;

		@NotEmpty
		@Length(min = 5, message="Por favor introduzca su contraseña")
		@Pattern(regexp="^(?=\\w*\\d)(?=\\w*[A-Z])\\S{5,}$", message="* La contraseña debe tener al menos un numero y una mayúscula y al menos 5 caracteres")
		private String password;
		
		@NotEmpty(message = "* Por favor, introduzca de nuevo su contraseña")
		private String passwordConfirmacion;

		public TransferEmpresa() {}
		
		public TransferEmpresa(String nombre, String cif, String email, String password, String passwordConfirmation) {
			this.nombre = nombre;
			this.cif = cif;
			this.email = email;
			this.password = password;
			this.passwordConfirmacion = passwordConfirmation; 
		}
		
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
