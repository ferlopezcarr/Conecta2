	package sprint2_IntegrationTests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Contrato;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.servicioAplicacion.SAOferta;
import conecta2.servicioAplicacion.SAParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU1FinalizarOfertaTest {

	@Autowired
	private SAOferta saOferta;
	
	@Autowired
	private SAParticular saParticular;
	
	@Test
	public void testSeleccionarCandidato() {
		Oferta oferta = new Oferta("ofertaNoFinalizada", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null, "html java", 1);
		
		oferta = saOferta.save(oferta);
		
		Oferta ofertaSinCandidatos = new Oferta(oferta);
		
		Particular candidato = saParticular.buscarPorId(1);
		oferta.getParticularesSeleccionados().add(candidato);
		candidato.getOfertasSeleccionados().add(oferta);
		Oferta ofertaConCandidatos= saOferta.save(oferta);
		saParticular.save(candidato);
		
		assertNotEquals(ofertaSinCandidatos, ofertaConCandidatos);
	}
	
	@Test
	public void testFinalizarOferta() {

		//Creamos las entidades
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java", 1);
		
		oferta = saOferta.save(oferta);
		
		saOferta.eliminarOferta(oferta.getId());
		
		Oferta ofertaFinalizada = saOferta.buscarPorId(oferta.getId());
		 
		assertEquals(false, ofertaFinalizada.getFinalizada());
	}
}