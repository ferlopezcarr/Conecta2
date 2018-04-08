package unitTests;

import static org.junit.Assert.*;

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

}
