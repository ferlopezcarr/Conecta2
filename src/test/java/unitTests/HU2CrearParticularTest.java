package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.Application;
import conecta2.dao.DAOParticular;
import conecta2.modelo.Particular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class HU2CrearParticularTest {
	
	@Autowired
    private DAOParticular daoParticular;
	
	@Test
	public void testInsertFoundedByEmail() {
		
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0);

		daoParticular.save(particular);
		
		Particular particularBD = daoParticular.findByEmail(particular.getEmail());
		
		boolean email = particularBD.getEmail() == particular.getEmail();
		boolean nombre = particularBD.getNombre() == particular.getNombre();
		boolean apellidos = particularBD.getApellidos() == particular.getApellidos();
		boolean dni = particularBD.getDni() == particular.getDni();
		boolean password = particularBD.getPassword() == particular.getPassword();
		boolean activo = particularBD.getActivo() == particular.getActivo();
		boolean puntuacion = particularBD.getPuntuacion() == particular.getPuntuacion();
		
		assertEquals(true, (email && nombre && apellidos && dni && password && activo && puntuacion));
	}
	
	@Test
	public void testInsertFoundedByDni() {
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0);

		daoParticular.save(particular);
		
		Particular particularBD = daoParticular.findByDni(particular.getDni());
		
		boolean email = particularBD.getEmail() == particular.getEmail();
		boolean nombre = particularBD.getNombre() == particular.getNombre();
		boolean apellidos = particularBD.getApellidos() == particular.getApellidos();
		boolean dni = particularBD.getDni() == particular.getDni();
		boolean password = particularBD.getPassword() == particular.getPassword();
		boolean activo = particularBD.getActivo() == particular.getActivo();
		boolean puntuacion = particularBD.getPuntuacion() == particular.getPuntuacion();
		
		assertEquals(true, (email && nombre && apellidos && dni && password && activo && puntuacion));
	}

}
