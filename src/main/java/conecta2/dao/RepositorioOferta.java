package conecta2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Oferta;

import java.util.List;

@Repository("repositorioOferta")
public interface RepositorioOferta extends JpaRepository<Oferta, Integer>{

	Oferta findById(int id);
	Oferta findByIdAndEmpresa(int id, Empresa empresa);
	Oferta findByNombreAndJornadaAndContratoAndEmpresa(String nombre, JornadaLaboral jornada, Contrato contrato, Empresa empresa);
	//@Query(value="SELECT FROM ofertas WHERE empresa_id =?1")
	List<Oferta> findByIdAndActivoTrue(int id);
}
