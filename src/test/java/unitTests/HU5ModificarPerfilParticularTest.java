package unitTests;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class HU5ModificarPerfilParticularTest {
	
	@Autowired
    private DAOParticular daoParticular;
	
	@Test
	public void testModifyExistingParticular() {
		
		Particular particular = new Particular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "descripcion");
		
		daoParticular.save(particular);
		
		particular.setNombre("nombreModificado");
		particular.setEmail("otroEmail@particular.com");
		
		Particular particularGuardado = daoParticular.save(particular);
		
		assertEquals(particular, particularGuardado);
	}

}
