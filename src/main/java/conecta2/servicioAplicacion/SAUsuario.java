package conecta2.servicioAplicacion;

import conecta2.modelo.Usuario;
import conecta2.transfer.TransferUsuario;

public interface SAUsuario {
	public Usuario findUserByEmail(String email);
	public void guardarUsuario(TransferUsuario dtoUsuario);
}
