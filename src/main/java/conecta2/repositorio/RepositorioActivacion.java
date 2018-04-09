package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import conecta2.modelo.Activacion;

/**
 * Clase que proporciona el acceso  a la base de datos, implementado con JPA
 * @author ferlo
 * Para que sea implementado con JPA debe extender de JpaRepository, mapeado mediante
 * el par Activacion, Integer
 */
@Repository("repositorioActivacion")
public interface RepositorioActivacion extends JpaRepository<Activacion, Integer> {
	 Activacion	findByActivacion (String activacion);
	 Activacion findByEmail(String email);
}
