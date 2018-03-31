package conecta2.servicioAplicacion;


import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.transfer.TransferEmpresa;

/**
 * Interfaz que define los casos de uso
 * @author ferlo
 * Interfaz que define los métodos o funciones que puede realizar la empresa
 */
@Service
public interface SAEmpresa {
	
	public void crearEmpresa(TransferEmpresa transferEmpresa);
	public Empresa buscarPorEmail(String email);
	public Empresa buscarPorCif (String cif);
	public void save(TransferEmpresa transferEmpresa);
}
