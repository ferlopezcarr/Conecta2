package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import conecta2.DTO.DTOUsuario;
import conecta2.modelo.Rol;
import conecta2.modelo.Usuario;
import conecta2.repositorio.RepositorioUsuario;

@Service("saUsuario")
public class SAUsuarioImp implements SAUsuario{

	@Autowired
	private RepositorioUsuario repositorioUsuario;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Transactional
    @Override
    public void guardarUsuario(DTOUsuario dtoUsuario) {
        Usuario usuario = new Usuario();    
	    usuario.setNombre(dtoUsuario.getNombre());
	    usuario.setApellidos(dtoUsuario.getApellidos());
	    usuario.setEmail(dtoUsuario.getEmail());
	    usuario.setPassword(bCryptPasswordEncoder.encode(dtoUsuario.getPassword()));
	    usuario.setRol(dtoUsuario.getRol());
		usuario.setActivo(1);
        repositorioUsuario.save(usuario);
    }

	@Override
	public Usuario findUserByEmail(String email) {
		return repositorioUsuario.findByEmail(email);
	}
}
