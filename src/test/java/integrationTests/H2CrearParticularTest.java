package integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.Application;
import conecta2.modelo.Particular;
import conecta2.servicioAplicacion.SAParticular;
import conecta2.transfer.TransferParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class H2CrearParticularTest {
	
	@Autowired
	public SAParticular saParticular;
	
	@Test
	public void testBuscarPorEmail() {
		
		TransferParticular particular = new TransferParticular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0);
		
		saParticular.save(particular);

		Particular particularBD = saParticular.buscarPorEmail(particular.getEmail());
		
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
		
		TransferParticular particular = new TransferParticular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0);
		
		saParticular.save(particular);

		Particular particularBD = saParticular.buscarPorDni(particular.getDni());
		
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
