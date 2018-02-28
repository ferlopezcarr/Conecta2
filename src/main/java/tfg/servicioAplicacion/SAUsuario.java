package tfg.servicioAplicacion;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Usuario;

public interface SAUsuario {
	public Usuario findUserByEmail(String email);
	public void guardarUsuario(DTOUsuario dtoUsuario);
}
