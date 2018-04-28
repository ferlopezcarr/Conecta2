package sprint2_UnitTests;

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
import conecta2.repositorio.RepositorioParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU7PuntuarParticularContratadoTest {
	
	@Autowired
	public RepositorioParticular repoParticular;

	@Test
	public void testParticularContratadoFoundedAndPuntuarCorrect() {
		
		double puntuacion = 2.5;
		
		Empresa empresa = new Empresa("empresaPrueba1", "A28599031", "123456789", "empresaPruebaEmail1@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		particular.aniadirPuntuacion(new Puntuacion(puntuacion, empresa));
		
		particular = repoParticular.save(particular);
		
		Particular particularBD = repoParticular.findByEmail(particular.getEmail());
		
		boolean equals = particular.equals(particularBD);
		
		assertEquals(true, equals && (particular.getPuntuaciones().get(0).getPuntuacion() == puntuacion) && (particular.getPuntuaciones().size() == 1));
	}

}
