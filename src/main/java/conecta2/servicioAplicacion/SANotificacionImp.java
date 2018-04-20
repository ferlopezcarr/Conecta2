package conecta2.servicioAplicacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioNotificacion;

@Service
public class SANotificacionImp implements SANotificacion {

	
	@Autowired
	private RepositorioNotificacion repoNotificacion;
	
	@Transactional
	@Override
	public void crearNotificacion(Notificacion not) {
		
		this.repoNotificacion.save(not);
	}

	@Override
	public Notificacion buscarPorId(int id) {
		// TODO Auto-generated method stub
		return this.repoNotificacion.findById(id);
	}

	/**
	 * Devuelve una lista con las notificaciones de un particular
	 * 
	 * */
	@Override
	public List<Notificacion> buscarPorParticular(Particular par) {
		// TODO Auto-generated method stub
		return this.repoNotificacion.findByParticular(par);
	}

	/**
	 * Devuelve una lista con las notificaciones de una empresa
	 * 
	 * */
	@Override
	public List<Notificacion> buscarPorEmpresa(Empresa emp) {
		// TODO Auto-generated method stub
		return this.repoNotificacion.findByEmpresa(emp);
	}

}
