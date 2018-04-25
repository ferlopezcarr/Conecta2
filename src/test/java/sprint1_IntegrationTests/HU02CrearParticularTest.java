package sprint1_IntegrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Particular;
import conecta2.servicioAplicacion.SAParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU02CrearParticularTest {
	
	@Autowired
	public SAParticular saParticular;
	
	@Test
	public void testBuscarPorEmail() {
		
		boolean ok = false;
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particularGuardado = saParticular.save(particular);
		
		Particular particularBD = saParticular.buscarPorEmail(particular.getEmail());
		
		//Comprobamos si son las tres iguales
		boolean equals = (particular.equals(particularGuardado));
		
		if(equals)
			ok = particular.equals(particularBD);
		
		assertEquals(true, ok);
	}
	
	@Test
	public void testBuscarPorDni() {
		
		boolean ok = false;
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particularGuardado = saParticular.save(particular);
		
		Particular particularBD = saParticular.buscarPorDni(particular.getDni());
		
		//Comprobamos si son las tres iguales
		boolean equals = (particular.equals(particularGuardado));
		
		if(equals)
			ok = particular.equals(particularBD);
		
		assertEquals(true, ok);
	}

}
