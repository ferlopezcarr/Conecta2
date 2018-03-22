package conecta2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Particular;

//Anotación para indicar un repositorio
@Repository("daoParticular")
public interface DAOParticular extends JpaRepository<Particular, Integer> {
	 Particular findByEmail(String email); //Sólo con hacer un findBy'nombreDelAtributo'(Tipo nombre); te busca los usuarios con ese campo
}
