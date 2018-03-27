package conecta2.servicioAplicacion;

import conecta2.modelo.Empresa;
import conecta2.transfer.TransferEmpresa;

/**
 * Interfaz que define las casos de uso
 * @author ferlo
 * Interfaz que define los métodos o funciones que puede realizar la empresa
 */
public interface SAEmpresa {
	public void crearEmpresa(TransferEmpresa transferEmpresa);
	public Empresa buscarPorEmail(String email);
	public Empresa buscarPorCif (String email);
}
