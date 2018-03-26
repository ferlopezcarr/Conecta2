package conecta2.servicioAplicacion;

import conecta2.modelo.Empresa;
import conecta2.transfer.TransferEmpresa;

public interface SAEmpresa {
	public void crearEmpresa(TransferEmpresa transferEmpresa);
	public Empresa buscarPorEmail(String email);
	public Empresa buscarPorCif (String email);
}
