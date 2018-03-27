package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.Application;
import conecta2.dao.DAOEmpresa;
import conecta2.modelo.Empresa;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class HU1CrearEmpresa {

	@Autowired
    private DAOEmpresa daoEmpresa;
	
	@Test
	public void testInsertFoundedByEmail() {
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0);

		daoEmpresa.save(empresa);
		
		Empresa empresaBD = daoEmpresa.findByEmail(empresa.getEmail());
		
		boolean email = empresaBD.getEmail() == empresa.getEmail();
		boolean nombre = empresaBD.getNombre() == empresa.getNombre();
		boolean cif = empresaBD.getCif() == empresa.getCif();
		boolean password = empresaBD.getPassword() == empresa.getPassword();
		boolean activo = empresaBD.getActivo() == empresa.getActivo();
		boolean puntuacion = empresaBD.getPuntuacion() == empresa.getPuntuacion();
		
		assertEquals(true, (email && nombre && cif && password && activo && puntuacion));
	}
	
	@Test
	public void testInsertFoundedByCif() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0);
		
		daoEmpresa.save(empresa);
		
		Empresa empresaBD = daoEmpresa.findBycif(empresa.getCif());
		
		boolean email = empresaBD.getEmail() == empresa.getEmail();
		boolean nombre = empresaBD.getNombre() == empresa.getNombre();
		boolean cif = empresaBD.getCif() == empresa.getCif();
		boolean password = empresaBD.getPassword() == empresa.getPassword();
		boolean activo = empresaBD.getActivo() == empresa.getActivo();
		boolean puntuacion = empresaBD.getPuntuacion() == empresa.getPuntuacion();
		
		assertEquals(true, (email && nombre && cif && password && activo && puntuacion));
	}
	
}
