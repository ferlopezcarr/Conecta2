package conecta2.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Empresa;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Particular;

@Repository("repositorioNotificacion")
public interface RepositorioNotificacion extends JpaRepository<Notificacion, Integer> {


	 Notificacion findById(int id);
	 List<Notificacion> findByParticularAndLeidaFalse(Particular par);
	 List<Notificacion> findByEmpresaAndLeidaFalse(Empresa emp);
	 
}
