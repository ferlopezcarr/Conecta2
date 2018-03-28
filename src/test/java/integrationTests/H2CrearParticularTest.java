package integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.Application;
import conecta2.dao.DAOParticular;
import conecta2.modelo.Particular;
import conecta2.servicioAplicacion.SAParticularImp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest

public class TestH2CrearParticular {
	@Autowired
    private DAOParticular daoParticular;
	
	
	@Test
	public void testBuscarPorEmail() {
		
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0);
		
		SAParticularImp SA = new SAParticularImp(daoParticular);
		
		SA.guardarParticular(particular);

		Particular particularBD = SA.buscarPorEmail(particular.getEmail());
		
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
	public void testBuscarPorDni() {
		
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0);
		
		SAParticularImp SA = new SAParticularImp(daoParticular);
		
		SA.guardarParticular(particular);

		Particular particularBD = SA.buscarPorDni(particular.getDni());
		
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
