package conecta2.servicioAplicacion;

import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.transfer.TransferEmpresa;

@Service ("SAEmpresa")
public class SAEmpresaImp implements SAEmpresa {

	@Override
	public void crearEmpresa(TransferEmpresa transferEmpresa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Empresa buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
