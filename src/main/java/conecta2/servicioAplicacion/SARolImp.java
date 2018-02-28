package conecta2.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.modelo.Rol;
import conecta2.repositorio.RepositorioRol;

@Service("SARol")
public class SARolImp implements SARol {
	@Autowired
	private RepositorioRol repoRol;
	
	@Override
	public List<Rol> findAll(){
		return repoRol.findAll();
	}
}
