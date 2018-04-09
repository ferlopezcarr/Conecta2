package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Empresa;

/**
 * Clase que proporciona el acceso  a la base de datos, implementado con JPA
 * @author ferlo
 * Para que sea implementado con JPA debe extender de JpaRepository, mapeado mediante
 * el par Empresa, Integer
 */
@Repository("repositorioEmpresa")
public interface RepositorioEmpresa extends JpaRepository<Empresa, Integer> {
	 Empresa findByEmail(String email); //Sólo con hacer un findBy'nombreDelAtributo'(Tipo nombre); te busca los usuarios con ese campo
	 Empresa findByCif(String cif);
	 Empresa findById(int id);
}
