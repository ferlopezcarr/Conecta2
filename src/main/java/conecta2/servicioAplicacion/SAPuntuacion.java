package conecta2.servicioAplicacion;

import conecta2.modelo.Puntuacion;

public interface SAPuntuacion {

	public Puntuacion buscarPorId(int id);
	
	public Puntuacion save(Puntuacion puntuacion);
	
}
