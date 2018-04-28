package conecta2.servicioAplicacion;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;
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
		return this.repoNotificacion.findByParticularAndLeidaFalse(par);
	}

	/**
	 * Devuelve una lista con las notificaciones de una empresa
	 * 
	 * */
	@Override
	public List<Notificacion> buscarPorEmpresa(Empresa emp) {
		// TODO Auto-generated method stub
		return this.repoNotificacion.findByEmpresaAndLeidaFalse(emp);
	}

	@Override
	public void notificarParticularesOfertaFinalizada(Oferta of) {
		
		for (Particular par : of.getParticularesInscritos()) {
			
			if (of.getParticularesSeleccionados().contains(par)) {
				
				Notificacion not = new Notificacion();
				not.setParticular(par);
				not.setSiguiente("/verOferta?id=" + of.getId());
				not.setDescripcion("Enhorabuena has pasado el proceso de selección de la oferta: '" + of.getNombre() + "'");
				this.repoNotificacion.save(not);
			} else {
			
				Notificacion not = new Notificacion();
				not.setParticular(par);
				not.setSiguiente("/verOferta?id=" + of.getId());
				not.setDescripcion("Lo sentimos, la oferta: '"+ of.getNombre() + "' ha finalizado y no has sido seleccionado :(");
				this.repoNotificacion.save(not);
			}
		}
	}

	@Override
	public void notificarEmpresaNuevaInscripcion(Oferta ofe) {
		
		Notificacion not = new Notificacion();
		not.setEmpresa(ofe.getEmpresa());
		not.setSiguiente("/candidatos?id=" + ofe.getId());
		not.setDescripcion("!Hay nuevos candidatos en tu oferta:'" + ofe.getNombre() + "'¡");
		this.repoNotificacion.save(not);
		
	}

}
