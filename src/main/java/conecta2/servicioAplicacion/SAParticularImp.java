package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.dao.DAOParticular;
import conecta2.modelo.Particular;
import conecta2.transfer.TransferParticular;

/**
 * Clase que implementa las funciones de la interfaz SAParticular
 * @author ferlo
 * Clase que se desarrolla la funcionalidad de la entidad Particular
 */
//Anotación de Servicio de Aplicación
@Service //("saParticular")
public class SAParticularImp implements SAParticular{

	/**
	 * DAO que proporciona el acceso a la base de datos
	 */
	@Autowired
	private DAOParticular daoParticular;
	
	/**
	 * Atributo que se utiliza para encriptar las contraseñas una vez el usuario se registra
	 */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /**
     * Servicio de Aplicación para la autenticación por email
     */
    @Autowired
	private SAEmail saEmail;
	
    /**
     * Método que recibe un TransferParticular y lo inserta en la base de datos
     */
    @Transactional
    @Override
    //Convierte el dtoUsuario a Usuario
    public void crearParticular(TransferParticular transferParticular) {
    	
        Particular particular = new Particular();
        
        particular.setDni(transferParticular.getDni());
        particular.setNombre(transferParticular.getNombre());
        particular.setApellidos(transferParticular.getApellidos());
        particular.setTelefono(transferParticular.getTelefono());
        particular.setEmail(transferParticular.getEmail());
        particular.setPassword(bCryptPasswordEncoder.encode(transferParticular.getPassword()));
        particular.setActivo(false);
        daoParticular.save(particular); //Hace el save al repositorio (función interna de JPARepository)
        //Después de esto el usuario ya estaría guardado en la Base de Datos
        saEmail.enviarCorreo("Acceda al siguiente enlace para terminar el registro en Conecta2, ya casi está solo un paso más, ", "Alta cuenta en Conecta2", particular.getEmail());;
    }

    /**
     * Método que recibe un email y busca a un particular con el mismo en la base de datos
     */
	@Override
	public Particular buscarPorEmail(String email) {
		return daoParticular.findByEmail(email);
	}
	
    /**
     * Método que recibe un dni y busca a un particular con el mismo en la base de datos
     */
	@Override
	public Particular buscarPorDni(String dni) {
		return daoParticular.findByDni(dni);
	}
	
	/**
     * Método que recibe un id y busca a un particular con el mismo en la base de datos
     */
	@Override
	public Particular buscarPorId(int id) {
		return daoParticular.findById(id);
	}
	
	/**
	 * Método que recibe un TransferParticular y lo guarda en la base de datos
	 */
	@Override
	public void save(TransferParticular transferParticular) {

		Particular particular = daoParticular.findByDni(transferParticular.getDni());
		
		if(particular == null) {
			particular = new Particular(
					transferParticular.getNombre(),
					transferParticular.getApellidos(),
					transferParticular.getDni(),
					transferParticular.getTelefono(),
					transferParticular.getEmail(),
					transferParticular.getPassword(),
					transferParticular.getActivo(),
					transferParticular.getPuntuacion(),
					transferParticular.getDescripcion()
				);
		}
		else {
			particular.setNombre(transferParticular.getNombre());
			particular.setApellidos(transferParticular.getApellidos());
			particular.setTelefono(transferParticular.getTelefono());
			particular.setDescripcion(transferParticular.getDescripcion());
		}
        
        daoParticular.save(particular);
	}
}
