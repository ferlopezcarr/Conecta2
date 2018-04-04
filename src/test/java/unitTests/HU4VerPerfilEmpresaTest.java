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
public class HU4VerPerfilEmpresaTest {

	@Autowired
	private DAOEmpresa daoEmpresa;
	
	@Test
	public void testEmpresaFoundedByEmailYActivo() {
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0);

		daoEmpresa.save(empresa);
		
		Empresa empresaBD = daoEmpresa.findByEmail(empresa.getEmail());
		
		boolean iguales = empresa.equals(empresaBD);
		boolean activo = (empresaBD.getActivo() == empresa.getActivo()) == true;
		
		assertEquals(true, iguales && activo);
	}
	
	@Test
	public void testEmpresaFoundedByCifYActivo() {
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0);
		
		daoEmpresa.save(empresa);
		
		Empresa empresaBD = daoEmpresa.findByCif(empresa.getCif());
		
		boolean iguales = empresa.equals(empresaBD);
		boolean activo = (empresaBD.getActivo() == empresa.getActivo()) == true;
		
		assertEquals(true, iguales && activo);
	}

}
