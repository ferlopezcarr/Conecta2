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
public class HU4VerPerfilEmpresaTest {

	@Autowired
	public SAEmpresa saEmpresa;
	
	
	@Test
	public void testEmpresaFoundedByEmailYActivo() {
		
		TransferEmpresa empresa = new TransferEmpresa("empresaPruebaNombre", "A28599033", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "Abc1111",true);
		
		saEmpresa.save(empresa);

		Empresa empresaBD = saEmpresa.buscarPorEmail(empresa.getEmail());
		
		boolean email = empresaBD.getEmail() == empresa.getEmail();
		boolean nombre = empresaBD.getNombreEmpresa() == empresa.getNombreEmpresa();
		boolean cif = empresaBD.getCif() == empresa.getCif();
		boolean activo = (empresaBD.getActivo() == empresa.getActivo()) == true;
		
		assertEquals(true, (email && nombre && cif && activo));
	}
	
	@Test
	public void testEmpresaFoundedByCifYActivo() {
		
		TransferEmpresa empresa = new TransferEmpresa("empresaPruebaNombre", "A28599033", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "Abc1111",true);
		
		saEmpresa.save(empresa);

		Empresa empresaBD = saEmpresa.buscarPorCif(empresa.getCif());
		
		boolean email = empresaBD.getEmail() == empresa.getEmail();
		boolean nombre = empresaBD.getNombreEmpresa() == empresa.getNombreEmpresa();
		boolean cif = empresaBD.getCif() == empresa.getCif();
		boolean activo = (empresaBD.getActivo() == empresa.getActivo()) == true;
		
		assertEquals(true, (email && nombre && cif && activo));
	}

}
