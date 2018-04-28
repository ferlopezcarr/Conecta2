package conecta2.servicioAplicacion;

import java.util.List;

import conecta2.modelo.Empresa;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;

public interface SANotificacion {
	
	public void crearNotificacion(Notificacion not);
	public Notificacion buscarPorId(int id);
	public List<Notificacion> buscarPorParticular(Particular par);
	public List<Notificacion> buscarPorEmpresa(Empresa emp);
	public void notificarParticularesOfertaFinalizada(Oferta ofe);
	public void notificarEmpresaNuevaInscripcion(Oferta ofe);
	
}
