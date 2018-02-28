package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Usuario;

@Repository("repositorioUsuario")
public interface RepositorioUsuario extends JpaRepository<Usuario, Integer> {
	 Usuario findByEmail(String email);
}
