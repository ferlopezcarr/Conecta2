package sprint2_UnitTests;

import static org.junit.Assert.assertNotEquals;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU3EliminarOfertaTest {
	

	@Autowired
	private SAOferta saOferta;
	
	
	@Test
	public void testEliminarOferta() {
		
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java");
		
		oferta = saOferta.save(oferta);
		
		Oferta ofertaNoEliminada = new Oferta(oferta);
		
		oferta.setActivo(false);
		 
		Oferta ofertaEliminada = saOferta.save(oferta);
				
		assertNotEquals(ofertaEliminada.getActivo(), ofertaNoEliminada.getActivo());
		
		
	}
}
