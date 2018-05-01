package sprint1_IntegrationTests;

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
public class HU10VerDetallesCandidatoTest {
	
	@Autowired
	private SAOferta saOferta;
	
	@Autowired
	private SAParticular saParticular;
	
	@Test
	public void testInscribirse() {
		
		//Creamos las entidades
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java", 1);
		oferta = saOferta.save(oferta);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		particular = saParticular.save(particular);
		
		//Asignamos el particular a la oferta y viceversa
		oferta.inscribirParticular(particular);
		
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		oferta = saOferta.save(oferta);
		
		particular = saParticular.save(particular);
		
		assertEquals(true, oferta.getParticularesInscritos().contains(particular));

	}
}
