package conecta2.servicioAplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conecta2.dao.RepositorioOferta;
import conecta2.modelo.Oferta;
import conecta2.transfer.TransferOferta;

@Service
public class SAOfertaImp implements SAOferta {

	@Autowired
	private RepositorioOferta repoOferta;
	
	@Override
	public void crearOferta(TransferOferta transferOferta) {
		
		Oferta oferta = new Oferta(
				transferOferta.getNombre(),
				transferOferta.getJornadaLaboral(),
				transferOferta.getContrato(),
				transferOferta.getVacantes(),
				transferOferta.getSalario(),
				transferOferta.getCiudad(),
				transferOferta.getDescripcion(),
				transferOferta.getActivo(),
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
	public void save(TransferOferta tOferta) {
		Oferta oferta = new Oferta();
		
		oferta.setNombre(tOferta.getNombre());
		oferta.setCiudad(tOferta.getCiudad());
		oferta.setContrato(tOferta.getContrato());
		oferta.setDescripcion(tOferta.getDescripcion());
		oferta.setJornadaLaboral(tOferta.getJornadaLaboral());
		oferta.setSalario(tOferta.getSalario());
		oferta.setVacantes(tOferta.getVacantes());
		
		repoOferta.save(oferta);
	}

}
