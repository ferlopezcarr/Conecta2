package conecta2.servicioAplicacion;

import java.util.List;

import conecta2.modelo.Oferta;
import conecta2.transfer.TransferOferta;

public interface SAOferta {
		public void crearOferta(TransferOferta transferOferta);
		public List<Oferta> buscarTodas();
		public Oferta buscarPorId (int id);
		public void save(TransferOferta transferEmpresa);
}
