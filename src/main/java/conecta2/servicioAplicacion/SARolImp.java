package conecta2.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.dao.DAORol;
import conecta2.modelo.Rol;

//Anotación de Servicio de Aplicación
@Service("SARol")
public class SARolImp implements SARol {
	@Autowired
	private DAORol repoRol;
	
	@Override
	public List<Rol> findAll(){
		return repoRol.findAll();
	}
}
