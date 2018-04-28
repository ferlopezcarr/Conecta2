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
import conecta2.modelo.Particular;
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
		int numValoraciones = 3;
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", 0.0, 0, true, null, null);
		
		particular.setPuntuacion(puntuacion);
		particular.setNumValoraciones(numValoraciones);
		
		particular = repoParticular.save(particular);
		
		Particular particularBD = repoParticular.findByEmail(particular.getEmail());
		
		boolean equals = particular.equals(particularBD);
		
		assertEquals(true, equals && (particular.getPuntuacion() == puntuacion) && (particular.getNumValoraciones() == numValoraciones));
	}

}
