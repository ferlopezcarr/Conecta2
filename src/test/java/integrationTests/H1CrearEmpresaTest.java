package integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Empresa;
import conecta2.servicioAplicacion.SAEmpresa;
import conecta2.transfer.TransferEmpresa;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class H1CrearEmpresaTest {
	
	@Autowired
	public SAEmpresa saEmpresa;
	
	
	@Test
	public void testBuscarPorEmail() {
		
		TransferEmpresa empresa = new TransferEmpresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "Abc1111","",true);
		
		saEmpresa.save(empresa);

		Empresa empresaBD = saEmpresa.buscarPorEmail(empresa.getEmail());
		
		boolean email = empresaBD.getEmail() == empresa.getEmail();
		boolean nombre = empresaBD.getNombreEmpresa() == empresa.getNombreEmpresa();
		boolean cif = empresaBD.getCif() == empresa.getCif();
		boolean password = empresaBD.getPassword() == empresa.getPassword();
		boolean activo = empresaBD.getActivo() == empresa.getActivo();
		boolean puntuacion = empresaBD.getPuntuacion() == empresa.getPuntuacion();
		
		assertEquals(true, (email && nombre && cif && password && activo && puntuacion));
	}
	
	@Test
	public void testBuscarPorCif() {
		
		TransferEmpresa empresa = new TransferEmpresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "Abc1111","", true);
		
		saEmpresa.save(empresa);

		Empresa empresaBD = saEmpresa.buscarPorCif(empresa.getCif());
		
		boolean email = empresaBD.getEmail() == empresa.getEmail();
		boolean nombre = empresaBD.getNombreEmpresa() == empresa.getNombreEmpresa();
		boolean cif = empresaBD.getCif() == empresa.getCif();
		boolean password = empresaBD.getPassword() == empresa.getPassword();
		boolean activo = empresaBD.getActivo() == empresa.getActivo();
		boolean puntuacion = empresaBD.getPuntuacion() == empresa.getPuntuacion();
		
		assertEquals(true, (email && nombre && cif && password && activo && puntuacion));
	}
	
	
	

}
