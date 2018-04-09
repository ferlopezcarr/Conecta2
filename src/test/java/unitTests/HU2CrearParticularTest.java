package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU2CrearParticularTest {
	
	@Autowired
    private RepositorioParticular daoParticular;
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testInsertNull() {
		
		Particular particular = null;
		
		daoParticular.save(particular);
	}
	
	@Test
	public void testInsertNotNull() {
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "descripcion");

		Particular particularGuardado = daoParticular.save(particular);
		
		assertEquals(particular, particularGuardado);
	}
	
	@Test
	public void testInsertNotFounded() {
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "descripcion");

		Particular particularGuardado = daoParticular.findByDni(particular.getDni());
		
		assertEquals(particularGuardado, null);
	}
	
	@Test
	public void testInsertFoundedById() {
		
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "descripcion");

		daoParticular.save(particular);
		
		Particular particularBD = daoParticular.findById(particular.getId());
		
		assertEquals(particular, particularBD);
	}
	
	@Test
	public void testInsertFoundedByEmail() {
		
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "descripcion");

		daoParticular.save(particular);
		
		Particular particularBD = daoParticular.findByEmail(particular.getEmail());
		
		assertEquals(particular, particularBD);
	}
	
	@Test
	public void testInsertFoundedByDni() {
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "descripcion");

		daoParticular.save(particular);
		
		Particular particularBD = daoParticular.findByDni(particular.getDni());
		
		assertEquals(particular, particularBD);
	}

}
