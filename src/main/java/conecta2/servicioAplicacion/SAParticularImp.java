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
@Service("saParticular")
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
        particular.setEmail(transferParticular.getEmail());
        particular.setPassword(bCryptPasswordEncoder.encode(transferParticular.getPassword()));
        particular.setActivo(true);
        daoParticular.save(particular); //Hace el save al repositorio (función interna de JPARepository)
        //Después de esto el usuario ya estaría guardado en la Base de Datos
    }

    /**
     * Método que recibe un email y busca a un particular con el mismo en la base de datos
     */
    //Llama al repositorio para hacer el findByEmail
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
}
