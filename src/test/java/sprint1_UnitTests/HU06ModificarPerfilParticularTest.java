package sprint1_UnitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU06ModificarPerfilParticularTest {
	
	@Autowired
    private RepositorioParticular repositorioParticular;
	
	@Test
	public void testModifyExistingParticular() {
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		repositorioParticular.save(particular);
		
		particular.setNombre("nombreModificado");
		particular.setEmail("otroEmail@particular.com");
		
		Particular particularGuardado = repositorioParticular.save(particular);
		
		assertEquals(particular, particularGuardado);
	}

}
