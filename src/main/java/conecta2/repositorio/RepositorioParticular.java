package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Particular;

/**
 * Clase que proporciona el acceso  a la base de datos, implementado con JPA
 * @author ferlo
 * Para que sea implementado con JPA debe extender de JpaRepository, mapeado mediante
 * el par Particular, Integer
 */
//Anotación para indicar un repositorio
@Repository("repositorioParticular")
public interface RepositorioParticular extends JpaRepository<Particular, Integer> {
	 Particular findByEmail(String email); //Sólo con hacer un findBy'nombreDelAtributo'(Tipo nombre); te busca los usuarios con ese campo
	 Particular findByDni(String dni);
	 Particular findById(int id);
}
