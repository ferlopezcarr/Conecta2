package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.dao.DAOUsuario;
import conecta2.modelo.Rol;
import conecta2.modelo.Usuario;
import conecta2.transfer.TransferUsuario;

//Anotación de Servicio de Aplicación
@Service("saUsuario")
public class SAUsuarioImp implements SAUsuario{

	@Autowired
	private DAOUsuario repositorioUsuario;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Transactional
    @Override
    //Convierte el dtoUsuario a Usuario
    public void guardarUsuario(TransferUsuario dtoUsuario) {
        Usuario usuario = new Usuario();
	    usuario.setNombre(dtoUsuario.getNombre());
	    usuario.setApellidos(dtoUsuario.getApellidos());
	    usuario.setEmail(dtoUsuario.getEmail());
	    usuario.setPassword(bCryptPasswordEncoder.encode(dtoUsuario.getPassword()));
	    usuario.setRol(dtoUsuario.getRol());
		usuario.setActivo(1);
        repositorioUsuario.save(usuario); //Hace el save al repositorio (función interna de JPARepository)
        //Después de esto el usuario ya estaría guardado en la Base de Datos
    }

    //Llama al repositorio para hacer el findByEmail
	@Override
	public Usuario findUserByEmail(String email) {
		return repositorioUsuario.findByEmail(email);
	}
}
