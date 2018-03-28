package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.dao.DAOParticular;
import conecta2.modelo.Particular;
import conecta2.transfer.TransferParticular;

//Anotación de Servicio de Aplicación
@Service("saParticular")
public class SAParticularImp implements SAParticular{

	@Autowired
	private DAOParticular daoParticular;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
	private SAEmail saEmail;
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
        saEmail.enviarCorreo("Acceda al siguiente enlace para terminar el registro en Conecta2, ya casi está solo un paso más, ", "Alta cuenta en Conecta2", particular.getEmail());;

        
    }

    //Llama al repositorio para hacer el findByEmail
	@Override
	public Particular buscarPorEmail(String email) {
		return daoParticular.findByEmail(email);
	}
}
