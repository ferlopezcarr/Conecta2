package conecta2.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import conecta2.modelo.Empresa;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Particular;

public interface RepositorioNotificacion extends JpaRepository<Notificacion, Integer> {


	 Notificacion findById(int id);
	 List<Notificacion> findAllParticular(Particular par);
	 List<Notificacion> findAllEmpresa(Empresa emp);
	 
}
