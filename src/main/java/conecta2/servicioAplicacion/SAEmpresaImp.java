package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.dao.DAOEmpresa;
import conecta2.modelo.Empresa;
import conecta2.transfer.TransferEmpresa;

/**
 * Clase que implementa las funciones de la interfaz SAEmpresa
 * @author ferlo
 * Clase que se desarrolla la funcionalidad de la entidad Empresa
 */
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
    
    
    public SAEmpresaImp(DAOEmpresa daoEmpresa) {
    	this.daoEmpresa = daoEmpresa;
    }

    /**
     * Método que recibe un TranferEmpresa y lo inserta en la base de datos
     */
    
    @Autowired
  	private SAEmail saEmail;
    

    @Transactional
    @Override
	public void crearEmpresa(TransferEmpresa transferEmpresa) {
    	 Empresa empresa = new Empresa();
         empresa.setCif(transferEmpresa.getCif());
         empresa.setNombreEmpresa(transferEmpresa.getNombreEmpresa());
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
		return daoEmpresa.findByCif(cif);
	}
	
	public void guardarEmpresa(Empresa empresa) {
		// TODO Auto-generated method stub
		daoEmpresa.save(empresa);
	}
	@Override
	public void save(TransferEmpresa transferEmpresa) {
		// TODO Auto-generated method stub
	 	 Empresa empresa = new Empresa();
         
	 	 empresa.setCif(transferEmpresa.getCif());
         empresa.setNombreEmpresa(transferEmpresa.getNombreEmpresa());
         empresa.setEmail(transferEmpresa.getEmail());
         empresa.setPassword(bCryptPasswordEncoder.encode(transferEmpresa.getPassword()));
         empresa.setActivo(transferEmpresa.isActivo());
         empresa.setPuntuacion(transferEmpresa.getPuntuacion());
        
         daoEmpresa.save(empresa); //Hace el save al repositorio (función interna de JPARepository)
	}

	
	

}
