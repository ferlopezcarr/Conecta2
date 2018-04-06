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
import conecta2.modelo.Empresa;
import conecta2.servicioAplicacion.SAEmpresa;
import conecta2.transfer.TransferEmpresa;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU5ModificarPerfilEmpresaTest {

	@Autowired
	public SAEmpresa saEmpresa;
	
	@Test
	public void testEmpresaModifyName() {
		TransferEmpresa transferEmpresa = new TransferEmpresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "Abc1111","",true);
		
		saEmpresa.save(transferEmpresa);
		
		Empresa empresaGuardada = saEmpresa.buscarPorEmail(transferEmpresa.getEmail());
		
		transferEmpresa.setNombreEmpresa("empresaPruebaModificado");
		
		saEmpresa.save(transferEmpresa);
		
		TransferEmpresa transferEmpresaGuardada = new TransferEmpresa(
				empresaGuardada.getNombreEmpresa(),
				empresaGuardada.getCif(),
				empresaGuardada.getTelefono(),
				empresaGuardada.getEmail(),
				empresaGuardada.getPassword(),
				empresaGuardada.getPassword(),
				empresaGuardada.getDescripcion(),
				empresaGuardada.getActivo()
			);
		
		assertNotEquals(transferEmpresa, transferEmpresaGuardada);
	}

}
