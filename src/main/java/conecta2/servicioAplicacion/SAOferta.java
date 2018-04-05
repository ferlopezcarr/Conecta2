package conecta2.servicioAplicacion;

import java.util.List;

import org.springframework.stereotype.Service;

import conecta2.modelo.Oferta;
import conecta2.transfer.TransferOferta;

@Service
public interface SAOferta {

	
		
		public void crearOferta(TransferOferta transferOferta);
		public List<Oferta> listarOfertas();
		public Oferta buscarPorId (int id);
	
	
}
