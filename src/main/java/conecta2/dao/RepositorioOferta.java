package conecta2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Oferta;

@Repository("repositorioOferta")
public interface RepositorioOferta extends JpaRepository<Oferta, Integer>{

	Oferta findById(int id);
	
}
