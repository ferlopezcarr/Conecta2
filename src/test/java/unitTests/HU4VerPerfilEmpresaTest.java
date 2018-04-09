package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Empresa;
import conecta2.repositorio.RepositorioEmpresa;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU4VerPerfilEmpresaTest {

	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@Test
	public void testEmpresaFoundedByEmailYActivo() {
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");

		repositorioEmpresa.save(empresa);
		
		Empresa empresaBD = repositorioEmpresa.findByEmail(empresa.getEmail());
		
		boolean iguales = empresa.equals(empresaBD);
		boolean activo = (empresaBD.getActivo() == empresa.getActivo()) == true;
		
		assertEquals(true, iguales && activo);
	}
	
	@Test
	public void testEmpresaFoundedByCifYActivo() {
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		repositorioEmpresa.save(empresa);
		
		Empresa empresaBD = repositorioEmpresa.findByCif(empresa.getCif());
		
		boolean iguales = empresa.equals(empresaBD);
		boolean activo = (empresaBD.getActivo() == empresa.getActivo()) == true;
		
		assertEquals(true, iguales && activo);
	}

}
