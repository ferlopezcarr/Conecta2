package tfg.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tfg.DTO.DTOUsuario;
import tfg.modelo.Rol;
import tfg.modelo.Usuario;
import tfg.repositorio.RepositorioUsuario;

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
