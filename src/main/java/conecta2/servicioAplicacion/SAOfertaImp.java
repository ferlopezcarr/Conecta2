package conecta2.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioOferta;
import conecta2.transfer.TransferOferta;

/**
 * Clase que implementa las funciones de la interfaz SAOferta
 * Clase que se desarrolla la funcionalidad de la entidad Oferta
 */
@Service
public class SAOfertaImp implements SAOferta {

	/**
	 * Repositorio que proporciona el acceso a la base de datos
	 */
	@Autowired
	private RepositorioOferta repoOferta;
	

	/**
	 * Método que devuelve una lista con todas las ofertas
	 */
	@Override
	public List<Oferta> buscarTodas() {
		return repoOferta.findAll();
	}

	/**
	 * Método que dado el id de la oferta, la busca en la BD y la devuelve
	 */
	@Override
	public Oferta buscarPorId(int id) {
		return repoOferta.findById(id);
	}
	
	/**
	 * Método que dado el id y la empresa, la busca en la BD y la devuelve
	 */
	@Override
	public Oferta buscarPorIdYEmpresa(int id, Empresa empresa) {
		return repoOferta.findByIdAndEmpresa(id, empresa);
	}
	/*
	@Override
	public Oferta buscarPorNombreAndJornadaAndContratoAndEmpresa(String nombre, JornadaLaboral jornada, Contrato contrato, Empresa empresa) {
		return repoOferta.findByNombreAndJornadaAndContratoAndEmpresa(nombre, jornada, contrato, empresa);
	}
	*/
	/**
	 * Método que devuelve la lista de ofertas de una empresa
	 */
	@Override
	public List<Oferta> buscarOfertasPorEmpresa(Empresa empresa) {
		return repoOferta.findByEmpresaAndActivoTrue(empresa);
	}
	
	/**
	 * Método que busca una oferta por su nombre y devuelve todas las que coincidan
	 */
	@Override
	public List<Oferta> buscarOfertasPorNombreYNombreMayus(String nombre, String nombreMayusPrimero) {
		return repoOferta.findByNombreContainingOrNombreContaining(nombre, nombreMayusPrimero);
	}
	
	/**
	 * Método que devuelve una lista con las ofertas en las que está inscrito un particular
	 */
	@Override
	public List<Oferta> buscarOfertasParticularInscrito(Particular part) {
		return this.repoOferta.findOfertasParticularInscrito(part);
	}
	
	/**
	 * Método que crea una oferta con los datos del transfer y lo guarda en la BD
	 */
	@Override
	public Oferta save(TransferOferta tOferta) {
		Oferta oferta = new Oferta();
		
		oferta.setNombre(tOferta.getNombre());
		oferta.setJornadaLaboral(tOferta.getJornada());
		oferta.setContrato(tOferta.getContrato());
		oferta.setVacantes(tOferta.getVacantes());
		oferta.setSalario(tOferta.getSalario());
		oferta.setCiudad(tOferta.getCiudad());
		oferta.setDescripcion(tOferta.getDescripcion());
		oferta.setActivo(tOferta.getActivo());
		oferta.setEmpresa(tOferta.getEmpresa());
		oferta.setParticulares(tOferta.getParticulares());
		
		return repoOferta.save(oferta);
	}

	/**
	 * Método que actualiza una oferta y la devuelve, se usa para actualizar
	 * la lista de de candidatos al inscribirse un particular
	 */
	@Override
	public Oferta actualizarOferta(Oferta oferta) {
		
		if(oferta != null) {//se encuentra la oferta en la BD
			return this.repoOferta.save(oferta);
		}
		else {//no se encuentra la oferta
			return null;
		}
	}
	
}
