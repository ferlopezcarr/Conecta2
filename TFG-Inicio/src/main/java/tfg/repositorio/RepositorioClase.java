package tfg.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfg.modelo.Clase;

@Repository("repositorioClase")
public interface RepositorioClase extends JpaRepository<Clase, Integer>{
	Clase findByNombre(String nombre);
}
