package conecta2.servicioAplicacion;

import java.util.List;

import conecta2.modelo.Empresa;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;

public interface SAOferta {
	
		public Oferta buscarPorId (int id);
		
		public Oferta buscarPorIdYEmpresa(int id, Empresa empresa);
		
		public List<Oferta> buscarOfertasPorEmpresa(Empresa empresa);
		
		public List<Oferta> buscarOfertasFinalizadas(Empresa empresa);
		
		public List<Oferta> buscarOfertasParticularInscrito(Particular part);
		
		public List<Oferta> buscarOfertasPorNombreYNombreMayus(String nombre, String nombreMayusPrimero);
		
		public List<Oferta> buscarTodas();
		
		public void eliminarOferta(int id);
		
		public Oferta save(Oferta oferta);
		
}
