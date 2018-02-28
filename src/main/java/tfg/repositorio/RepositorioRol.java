package tfg.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tfg.modelo.Rol;

@Repository("repositorioRol")
public interface RepositorioRol extends JpaRepository<Rol, Integer>{
	Rol findByNombre(String nombre);
	List<Rol> findAll();
}
