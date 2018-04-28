package sprint2_IntegrationTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Empresa;
import conecta2.modelo.Particular;
import conecta2.modelo.Puntuacion;
import conecta2.servicioAplicacion.SAParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU7PuntuarParticularContratadoTest {
	
	@Autowired
	public SAParticular saParticular;

	@Test
	public void testParticularFoundedByEmailYActivo() {
		
		Empresa empresa1 = new Empresa("empresaPrueba1", "A28599031", "123456789", "empresaPruebaEmail1@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Empresa empresa2 = new Empresa("empresaPrueba2", "A28599032", "123456789", "empresaPruebaEmail2@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Empresa empresa3 = new Empresa("empresaPrueba3", "A28599033", "123456789", "empresaPruebaEmail3@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		particular.aniadirPuntuacion(new Puntuacion(3.5, empresa1));
		particular.aniadirPuntuacion(new Puntuacion(2.5, empresa2));
		particular.aniadirPuntuacion(new Puntuacion(1.5, empresa3));
		
		particular = saParticular.save(particular);
		
		//(3.5 + 2.5 + 1.5) / 3 = 2.5
		
		Particular particularBD = saParticular.buscarPorEmail(particular.getEmail());
		
		boolean equals = particular.equals(particularBD);
		
		double puntuacionMedia = particular.getPuntuacionMedia();
		
		assertEquals(true, equals && (puntuacionMedia == 2.5) && (particular.getPuntuaciones().size() == 3));
	}

}
