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
public class HU6ModificarPerfilEmpresaTest {

	
	@Autowired
    private RepositorioEmpresa repositorioEmpresa;
	
	@Test
	public void testModifyExistingCompany() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A11223344", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		repositorioEmpresa.save(empresa);
		
		empresa.setNombreEmpresa("empresaPruebaModificado");
		empresa.setEmail("emailModificado@empresa.com");
		
		Empresa empresaGuardada = repositorioEmpresa.save(empresa);
		
		assertEquals(empresa, empresaGuardada);
	}
}
