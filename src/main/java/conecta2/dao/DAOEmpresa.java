package conecta2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import conecta2.modelo.Empresa;

@Repository("daoEmpresa")
public interface DAOEmpresa extends JpaRepository<Empresa, Integer> {
	 Empresa findByEmail(String email); //Sólo con hacer un findBy'nombreDelAtributo'(Tipo nombre); te busca los usuarios con ese campo
	 Empresa findBycif(String email);
}
