package integrationTests;

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
import conecta2.servicioAplicacion.SAParticular;
import conecta2.transfer.TransferParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU6ModificarPerfilParticularTest {

	@Autowired
	public SAParticular saParticular;
	
	@Test
	public void testEmpresaModifyName() {
		TransferParticular transferParticular = new TransferParticular("nombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", true, 0, "ejemplo");
		
		saParticular.save(transferParticular);
		
		Particular particularGuardado = saParticular.buscarPorEmail(transferParticular.getEmail());
		
		transferParticular.setNombre("otroNombre");
		
		saParticular.save(transferParticular);
		
		TransferParticular transferParticularGuardado = new TransferParticular(
				particularGuardado.getNombre(),
				particularGuardado.getApellidos(),
				particularGuardado.getDni(),
				particularGuardado.getTelefono(),
				particularGuardado.getEmail(),
				particularGuardado.getPassword(),
				particularGuardado.getActivo(),
				particularGuardado.getPuntuacion(),
				particularGuardado.getDescripcion()
			);
		
		assertNotEquals(transferParticular, transferParticularGuardado);
	}

}
