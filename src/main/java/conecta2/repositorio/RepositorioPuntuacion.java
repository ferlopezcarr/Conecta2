package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Puntuacion;

@Repository("repositorioPuntuacion")
public interface RepositorioPuntuacion extends JpaRepository<Puntuacion, Integer> {

	 Puntuacion findById(int id);
	 
}
