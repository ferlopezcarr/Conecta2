package integrationTests;

import static org.junit.Assert.*;

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
public class HU5VerPerfilParticularTest {

	@Autowired
	public SAParticular saParticular;
	
	
	@Test
	public void testEmpresaFoundedByEmailYActivo() {
		
		TransferParticular particular = new TransferParticular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0,"ejemplo");
		
		saParticular.save(particular);

		Particular particularBD = saParticular.buscarPorEmail(particular.getEmail());
		
		boolean email = particularBD.getEmail() == particular.getEmail();
		boolean nombre = particularBD.getNombre() == particular.getNombre();
		boolean apellidos = particularBD.getApellidos() == particular.getApellidos();
		boolean dni = particularBD.getDni() == particular.getDni();
		boolean activo = (particularBD.getActivo() == particular.getActivo()) == true;
		
		assertEquals(true, (email && nombre && apellidos && dni && activo));
	}
	
	@Test
	public void testEmpresaFoundedByCifYActivo() {
		
		TransferParticular particular = new TransferParticular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0,"ejemplo");
		
		saParticular.save(particular);

		Particular particularBD = saParticular.buscarPorDni(particular.getDni());
		
		boolean email = particularBD.getEmail() == particular.getEmail();
		boolean nombre = particularBD.getNombre() == particular.getNombre();
		boolean apellidos = particularBD.getApellidos() == particular.getApellidos();
		boolean dni = particularBD.getDni() == particular.getDni();
		boolean activo = (particularBD.getActivo() == particular.getActivo()) == true;
		
		assertEquals(true, (email && nombre && apellidos && dni && activo));
	}

}
