package conecta2.servicioAplicacion;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.modelo.Puntuacion;
import conecta2.repositorio.RepositorioPuntuacion;

@Service
public class SAPuntuacionImp implements SAPuntuacion {
	
	/**
	 * Repositorio que proporciona el acceso a la base de datos
	 */
	@Autowired
	private RepositorioPuntuacion repositorioPuntuacion;

	/**
     * Método que recibe un id y busca a una empresa con el mismo en la base de datos
     */
	@Override
	public Puntuacion buscarPorId(int id) {
		return repositorioPuntuacion.findById(id);
	}
	
	/**
	 * Método que recibe un TransferEmpresa y si no existe lo guarda en la base de datos
	 * en caso contrario lo modifica
	 */
	@Transactional
	@Override
	public Puntuacion save(Puntuacion puntuacion) {

		if(puntuacion != null)
			puntuacion = repositorioPuntuacion.save(puntuacion); //Hace el save al repositorio (función interna de JPARepository)
         
         return puntuacion;
	}
	
}
