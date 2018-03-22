package conecta2.servicioAplicacion;

import conecta2.modelo.Particular;
import conecta2.transfer.TransferParticular;

public interface SAParticular {
	public void crearParticular(TransferParticular dtoUsuario);
	public Particular buscarPorEmail(String email);
}
