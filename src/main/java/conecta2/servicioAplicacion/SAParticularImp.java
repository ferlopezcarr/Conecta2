package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioParticular;
import conecta2.transfer.TransferParticular;

/**
 * Clase que implementa las funciones de la interfaz SAParticular
 * Clase que se desarrolla la funcionalidad de la entidad Particular
 */
//Anotación de Servicio de Aplicación
@Service //("saParticular")
public class SAParticularImp implements SAParticular{

	/**
	 * Repositorio que proporciona el acceso a la base de datos
	 */
	@Autowired
	private RepositorioParticular repositorioParticular;
	
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
    	
        Particular particular = Particular.TranferToEntity(transferParticular);
        particular.setPassword(bCryptPasswordEncoder.encode(transferParticular.getPassword()));
        particular.setActivo(false);
        
        repositorioParticular.save(particular); //Hace el save al repositorio (función interna de JPARepository)
        //Después de esto el usuario ya estaría guardado en la Base de Datos
        saEmail.enviarCorreo("Acceda al siguiente enlace para terminar el registro en Conecta2, ya casi está solo un paso más, ", "Alta cuenta en Conecta2", particular.getEmail());;
    }

    /**
     * Método que recibe un email y busca a un particular con el mismo en la base de datos
     */
	@Override
	public Particular buscarPorEmail(String email) {
		return repositorioParticular.findByEmail(email);
	}
	
    /**
     * Método que recibe un dni y busca a un particular con el mismo en la base de datos
     */
	@Override
	public Particular buscarPorDni(String dni) {
		return repositorioParticular.findByDni(dni);
	}
	
	/**
     * Método que recibe un id y busca a un particular con el mismo en la base de datos
     */
	@Override
	public Particular buscarPorId(int id) {
		return repositorioParticular.findById(id);
	}
	
	/**
	 * Método que recibe un TransferParticular y lo guarda en la base de datos,
	 * si ya existe lo modifica
	 */
	@Override
	public Particular save(Particular particular) {

        if(particular != null)
        	particular = repositorioParticular.save(particular);
        
        return particular;
	}

	@Override
	public Particular addValoracion(Particular contratado, double valoracion) {
		
		double puntuacionActual;
		int numValoraciones = contratado.getNumValoraciones();
										
		if(numValoraciones != 0) {//si ya ha sido valorado anteriormente
		
			double puntosTotalesAntes = (numValoraciones * contratado.getPuntuacion());

			puntuacionActual = (puntosTotalesAntes + valoracion) / (numValoraciones+1);
		}
		else {
			puntuacionActual = valoracion;			
		}
		
		//para redondear y quedarse solo con los decimas, tantos 0s como decimales
		puntuacionActual = Math.rint(puntuacionActual*100)/100;
		numValoraciones++;
		
		contratado.setPuntuacion(puntuacionActual);
		contratado.setNumValoraciones(numValoraciones);
		
		contratado = save(contratado);
		
		return contratado;
	}

}
