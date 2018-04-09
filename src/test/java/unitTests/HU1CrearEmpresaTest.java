package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.dao.InvalidDataAccessApiUsageException;

import conecta2.C2Aplicacion;
import conecta2.modelo.Empresa;
import conecta2.repositorio.RepositorioEmpresa;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU1CrearEmpresaTest {

	@Autowired
    private RepositorioEmpresa repositorioEmpresa;
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testInsertNull() {
		Empresa empresa = null;
		
		repositorioEmpresa.save(empresa);
	}
	
	@Test
	public void testInsertNotNull() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		Empresa empresaGuardada = repositorioEmpresa.save(empresa);
		
		assertEquals(empresa, empresaGuardada);
	}
	
	@Test
	public void testNotFounded() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		Empresa empresaBD = repositorioEmpresa.findByCif(empresa.getCif());
		
		assertEquals(empresaBD, null);
	}
	
	@Test
	public void testInsertFoundedById() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		repositorioEmpresa.save(empresa);
		
		Empresa empresaBD = repositorioEmpresa.findById(empresa.getId());
		
		assertEquals(empresa, empresaBD);
	}
	
	@Test
	public void testInsertFoundedByEmail() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		repositorioEmpresa.save(empresa);
		
		Empresa empresaBD = repositorioEmpresa.findByEmail(empresa.getEmail());
		
		assertEquals(empresa, empresaBD);
	}
	
	@Test
	public void testInsertFoundedByCif() {
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", true, 0, "");
		
		repositorioEmpresa.save(empresa);
		
		Empresa empresaBD = repositorioEmpresa.findByCif(empresa.getCif());
		
		assertEquals(empresa, empresaBD);
	}

}
