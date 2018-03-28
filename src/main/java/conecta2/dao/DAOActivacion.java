package conecta2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import conecta2.modelo.Activacion;


@Repository("daoActivacion")
public interface DAOActivacion extends JpaRepository<Activacion, Integer> {
	 Activacion	findByActivacion (String activacion);
	 Activacion findByEmail(String email);
}
