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
	
	/**
	 * DAO que proporciona el acceso a la base de datos
	 */
	@Autowired
	private DAOEmpresa daoEmpresa;
	
	/**
	 * Atributo que se utiliza para encriptar las contraseñas una vez el usuario se registra
	 */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Método de Negoio que recibe un TranferEmpresa y lo inserta en la base de datos
     */
    
    @Autowired
  	private SAEmail saEmail;
    

    @Transactional
    @Override
	public void crearEmpresa(TransferEmpresa transferEmpresa) {
    	 Empresa empresa = new Empresa();
         empresa.setCif(transferEmpresa.getCif());
         empresa.setNombre(transferEmpresa.getNombre());
         empresa.setEmail(transferEmpresa.getEmail());
         empresa.setPassword(bCryptPasswordEncoder.encode(transferEmpresa.getPassword()));
         empresa.setActivo(false);

         saEmail.enviarCorreo("Acceda al siguiente enlace para terminar el registro en Conecta2, ya casi está solo un paso más, ", "Alta cuenta en Conecta2", empresa.getEmail());;
        
         daoEmpresa.save(empresa); //Hace el save al repositorio (función interna de JPARepository)
         //Después de esto el usuario ya estaría guardado en la Base de Datos
	}

    /**
     * Método que recibe un email y busca a una empresa con el mismo en la base de datos
     */
	@Override
	public Empresa buscarPorEmail(String email) {
		return daoEmpresa.findByEmail(email);
	}

    /**
     * Método que recibe un cif y busca a una empresa con el mismo en la base de datos
     */
	@Override
	public Empresa buscarPorCif(String cif) {
		return daoEmpresa.findBycif(cif);
	}

}
