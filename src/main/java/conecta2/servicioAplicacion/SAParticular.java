package conecta2.servicioAplicacion;

import org.springframework.stereotype.Service;

import conecta2.modelo.Particular;
import conecta2.transfer.TransferParticular;

/**
 * Interfaz que define los casos de uso
 * @author ferlo
 * Interfaz que define los métodos o funciones que puede realizar el particular
 */
@Service
public interface SAParticular {
	public void crearParticular(TransferParticular dtoUsuario);
	public Particular buscarPorEmail(String email);
	public Particular buscarPorDni(String dni);
	public Particular buscarPorId(int id);
	public void save(TransferParticular transferParticular);

}
