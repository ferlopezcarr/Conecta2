package conecta2.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.modelo.Empresa;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioOferta;
import conecta2.transfer.TransferOferta;

@Service
public class SAOfertaImp implements SAOferta {

	@Autowired
	private RepositorioOferta repoOferta;
	
	@Override
	public void crearOferta(TransferOferta transferOferta) {
		
		Oferta oferta = new Oferta(
				transferOferta.getNombre(),
				transferOferta.getJornada(),
				transferOferta.getContrato(),
				transferOferta.getVacantes(),
				transferOferta.getSalario(),
				transferOferta.getCiudad(),
				transferOferta.getDescripcion(),
				transferOferta.getActivo(),
				transferOferta.getFinalizada(),
				transferOferta.getEmpresa(),
				transferOferta.getParticulares()
				);
		
		repoOferta.save(oferta);
	}

	@Override
	public List<Oferta> buscarTodas() {
		return repoOferta.findAll();
	}

	@Override
	public Oferta buscarPorId(int id) {
		return repoOferta.findById(id);
	}
	
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
	@Override
	public List<Oferta> buscarOfertasPorEmpresa(Empresa empresa) {
		return repoOferta.findByEmpresaAndActivoTrue(empresa);
	}
	
	@Override
	public List<Oferta> buscarOfertasPorNombreYNombreMayus(String nombre, String nombreMayusPrimero) {
		return repoOferta.findByNombreContainingOrNombreContaining(nombre, nombreMayusPrimero);
	}
	
	@Override
	public List<Oferta> buscarOfertasParticularInscrito(Particular part) {
		return this.repoOferta.findOfertasParticularInscrito(part);
	}
	
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
	 * Metodo que actualiza una oferta y la devuelve, se usa para actualizar
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
