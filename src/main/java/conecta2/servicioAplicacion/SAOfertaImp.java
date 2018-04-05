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
	public void crearOferta(TransferOferta tOferta) {
		// TODO Auto-generated method stub
		
		Oferta oferta = new Oferta();
		
		oferta.setNombre(tOferta.getNombre());
		oferta.setCcaa(tOferta.getCcaa());
		oferta.setContrato(tOferta.getContrato());
		oferta.setDescripcion(tOferta.getDescripcion());
		oferta.setJornadaLaboral(tOferta.getJornadaLaboral());
		oferta.setSalario(tOferta.getSalario());
		oferta.setVacantes(tOferta.getVacantes());
		oferta.setEmpresa(tOferta.getEmpresa());
		
		repoOferta.save(oferta);
	}

	@Override
	public List<Oferta> listarOfertas() {
		// TODO Auto-generated method stub
		return repoOferta.findAll();
	}

	@Override
	public Oferta buscarPorId(int id) {
		// TODO Auto-generated method stub
		return repoOferta.findById(id);
	}

}
