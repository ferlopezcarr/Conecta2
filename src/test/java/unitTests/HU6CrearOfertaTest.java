package unitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.Application;
import conecta2.dao.RepositorioOferta;
import conecta2.modelo.Contrato;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.transfer.TransferOferta;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DataJpaTest
public class HU6CrearOfertaTest {
	
	@Autowired
	private RepositorioOferta repOferta;

	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testInsertNull() {
		Oferta oferta = null;
		
		repOferta.save(oferta);
	}
	
	@Test
	public void testInsertNotNull() {
		Oferta oferta = new Oferta("oferta", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		
		Oferta ofertaGuardada = repOferta.save(oferta);
		
		assertEquals(oferta, ofertaGuardada);
	}
	
	@Test
	public void testNotFounded() {
		Oferta oferta = new Oferta("oferta", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		
		Oferta ofertaGuardada = repOferta.findById(oferta.getId());
		
		assertEquals(ofertaGuardada, null);
	}
	
	@Test
	public void testInsertFoundedById() {
		Oferta oferta = new Oferta("oferta", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		
		repOferta.save(oferta);
		
		Oferta ofertaGuardada = repOferta.findById(oferta.getId());
		
		assertEquals(oferta, ofertaGuardada);
	}
	
	@Test
	public void testDatosNumeradosIncorrectos() {
		
		TransferOferta transferOferta = new TransferOferta(
				"oferta", 
				JornadaLaboral.PorHoras,
				Contrato.Formación,
				1,
				200.0,
				"Madrid",
				"hola",
				true,
				false,
				null,
				new ArrayList<Particular>()
			);
		
		transferOferta.setAuxSalario("p");
		transferOferta.setAuxVacantes("p");
		Boolean equal1 = false;
		Boolean equal2 = false;
		if(transferOferta.getSalario() == null) equal1= true;
		if(transferOferta.getVacantes() == null) equal2 = true;	
		
		assertEquals(equal1 && equal2, true);
	}
	
	@Test
	public void testDatosNumeradosNegativos() {
		
		TransferOferta transferOferta = new TransferOferta(
				"oferta", 
				JornadaLaboral.PorHoras,
				Contrato.Formación,
				1,
				200.0,
				"Madrid",
				"hola",
				true,
				false,
				null,
				new ArrayList<Particular>()
			);
		
		transferOferta.setAuxSalario("-1");
		transferOferta.setAuxVacantes("-1");
		Boolean equal1 = false;
		Boolean equal2 = false;
		if(transferOferta.getSalario() == null) equal1= true;
		if(transferOferta.getVacantes() == null) equal2 = true;	
		
		assertEquals(equal1 && equal2, true);
	}
	
	@Test
	public void testVacantesDecimales() {
		
		TransferOferta transferOferta = new TransferOferta(
				"oferta", 
				JornadaLaboral.PorHoras,
				Contrato.Formación,
				1,
				200.0,
				"Madrid",
				"hola",
				true,
				false,
				null,
				new ArrayList<Particular>()
			);
		
		transferOferta.setAuxVacantes("1.5");
		Boolean equal1 = false;
		if(transferOferta.getVacantes() == null) equal1 = true;	
		
		assertEquals(equal1, true);
	}

}
