package sprint2_UnitTests;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Contrato;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Oferta;
import conecta2.repositorio.RepositorioOferta;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU2ModificarOfertaTest {

	@Autowired
    private RepositorioOferta repositorioOferta;
	
	@Test
	public void testModificarOferta() {
		Oferta oferta = new Oferta("ofertaNoFinalizada", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null, "html java", 1);
		
		repositorioOferta.save(oferta);
		
		Oferta ofertaNoModificada = new Oferta(oferta);
		
		oferta.setNombre("OfertaModificada");
		 
		Oferta ofertaModificada = repositorioOferta.save(oferta);
		
		assertNotEquals(ofertaNoModificada, ofertaModificada);
	}
}
