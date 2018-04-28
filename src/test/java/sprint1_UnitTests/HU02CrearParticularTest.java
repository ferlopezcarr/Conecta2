package sprint1_UnitTests;

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
public class HU02CrearParticularTest {
	
	@Autowired
    private RepositorioParticular repositorioParticular;
	
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testInsertNull() {
		
		Particular particular = null;
		
		repositorioParticular.save(particular);
	}
	
	@Test
	public void testInsertNotNull() {
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);

		Particular particularGuardado = repositorioParticular.save(particular);
		
		assertEquals(particular, particularGuardado);
	}
	
	@Test
	public void testInsertNotFounded() {
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);

		Particular particularGuardado = repositorioParticular.findByDni(particular.getDni());
		
		assertEquals(particularGuardado, null);
	}
	
	@Test
	public void testInsertFoundedById() {
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);

		repositorioParticular.save(particular);
		
		Particular particularBD = repositorioParticular.findById(particular.getId());
		
		assertEquals(particular, particularBD);
	}
	
	@Test
	public void testInsertFoundedByEmail() {
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);

		repositorioParticular.save(particular);
		
		Particular particularBD = repositorioParticular.findByEmail(particular.getEmail());
		
		assertEquals(particular, particularBD);
	}
	
	@Test
	public void testInsertFoundedByDni() {
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);

		repositorioParticular.save(particular);
		
		Particular particularBD = repositorioParticular.findByDni(particular.getDni());
		
		assertEquals(particular, particularBD);
	}

}
