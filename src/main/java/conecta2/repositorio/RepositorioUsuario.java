package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Usuario;

//Anotación para indicar un repositorio
@Repository("repositorioUsuario")
public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {
	 Usuario findByEmail(String email); //Sólo con hacer un findBy'nombreDelAtributo'(Tipo nombre); te busca los usuarios con ese campo
}
