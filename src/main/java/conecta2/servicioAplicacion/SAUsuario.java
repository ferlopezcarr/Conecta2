package conecta2.servicioAplicacion;

import conecta2.DTO.DTOUsuario;
import conecta2.modelo.Usuario;

public interface SAUsuario {
	public Usuario findUserByEmail(String email);
	public void guardarUsuario(DTOUsuario dtoUsuario);
}
