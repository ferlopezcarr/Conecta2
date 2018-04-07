package integrationTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.Application;
import conecta2.modelo.Contrato;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;

import conecta2.servicioAplicacion.SAOferta;
import conecta2.transfer.TransferOferta;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU6CrearOfertaTest {

	@Autowired
	private SAOferta saOferta;
	
	@Test
	public void testBuscarPorId() {
		
		TransferOferta transferOferta = new TransferOferta(
				"oferta", 
				JornadaLaboral.PorHoras,
				Contrato.Formación,
				1,
				200.0,
				"Madrid",
				"hola",
				true,
				null,
				new ArrayList<Particular>()
			);
		
		saOferta.save(transferOferta);
		
		Oferta ofertaGuardada = saOferta.buscarPorNombreAndJornadaAndContratoAndEmpresa(
				transferOferta.getNombre(),
				transferOferta.getJornada(),
				transferOferta.getContrato(),
				transferOferta.getEmpresa()
				);
		
		Oferta oferta = new Oferta(
				transferOferta.getNombre(), 
				transferOferta.getJornada(),
				transferOferta.getContrato(),
				transferOferta.getVacantes(),
				transferOferta.getSalario(),
				transferOferta.getCiudad(),
				transferOferta.getDescripcion(),
				transferOferta.getActivo(),
				transferOferta.getEmpresa(),
				transferOferta.getParticulares()
			);
		oferta.setId(ofertaGuardada.getId());
		
		assertEquals(oferta, ofertaGuardada);
	}

}