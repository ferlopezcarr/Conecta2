package conecta2.servicioAplicacion;

import java.util.List;

import conecta2.modelo.Empresa;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.transfer.TransferOferta;

public interface SAOferta {
	
		
		public Oferta actualizarOferta(Oferta oferta);
		
		public Oferta save(TransferOferta transferOferta);
		
		public Oferta buscarPorId (int id);
		
		public Oferta buscarPorIdYEmpresa(int id, Empresa empresa);
		
		public List<Oferta> buscarOfertasPorEmpresa(Empresa empresa);
		
		public List<Oferta> buscarOfertasFinalizadas(Empresa empresa);
		
		public List<Oferta> buscarOfertasParticularInscrito(Particular part);
		
		public List<Oferta> buscarOfertasPorNombreYNombreMayus(String nombre, String nombreMayusPrimero);
		
		public List<Oferta> buscarTodas();
		
}
