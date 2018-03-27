package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.dao.DAOEmpresa;
import conecta2.modelo.Empresa;
import conecta2.transfer.TransferEmpresa;

@Service ("SAEmpresa")
public class SAEmpresaImp implements SAEmpresa {
	
	@Autowired
	private DAOEmpresa daoEmpresa;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Método de Negoio que recibe un TranferEmpresa y lo inserta en la base de datos
     */
    @Transactional
    @Override
	public void crearEmpresa(TransferEmpresa transferEmpresa) {
    	 Empresa empresa = new Empresa();
         empresa.setCif(transferEmpresa.getCif());
         empresa.setNombre(transferEmpresa.getNombre());
         empresa.setEmail(transferEmpresa.getEmail());
         empresa.setPassword(bCryptPasswordEncoder.encode(transferEmpresa.getPassword()));
         empresa.setActivo(true);
         daoEmpresa.save(empresa); //Hace el save al repositorio (función interna de JPARepository)
         //Después de esto el usuario ya estaría guardado en la Base de Datos
	}

	@Override
	public Empresa buscarPorEmail(String email) {
		return daoEmpresa.findByEmail(email);
	}

	@Override
	public Empresa buscarPorCif(String cif) {
		return daoEmpresa.findBycif(cif);
	}

}
