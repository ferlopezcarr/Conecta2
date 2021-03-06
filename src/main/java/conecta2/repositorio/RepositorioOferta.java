package conecta2.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import conecta2.modelo.Empresa;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;

import java.util.List;

/**
 * Clase que proporciona el acceso  a la base de datos, implementado con JPA
 * Para que sea implementado con JPA debe extender de JpaRepository, mapeado mediante
 * el par Oferta, Integer
 */
@Repository("repositorioOferta")
public interface RepositorioOferta extends JpaRepository<Oferta, Integer>{
	Oferta findById(int id);
	
	Oferta findByIdAndEmpresa(int id, Empresa empresa);
	
	List<Oferta> findByEmpresaAndActivoTrueAndFinalizadaFalse(Empresa empresa);
	
	List<Oferta> findByEmpresaAndFinalizadaTrueAndActivoTrue(Empresa empresa);
	
	List<Oferta> findByFinalizadaFalseAndActivoTrueAndNombreContainingOrNombreContainingAndFinalizadaFalseAndActivoTrue(String nombre, String nombreMayusPrimero);
	
    List<Oferta> findOfertasParticularInscrito(Particular part);
}
