package integrationTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import conecta2.transfer.TransferOferta;
import conecta2.transfer.TransferParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU9InscribirseEnOfertaTest {
	
	@Autowired
	private SAOferta saOferta;
	
	@Autowired
	private SAParticular saParticular;
	
	@Test
	public void testInscribirse() {
		
		TransferOferta transferOferta = new TransferOferta(
				"oferta2", 
				JornadaLaboral.PorHoras,
				Contrato.Formación,
				1,
				230.0,
				"Barcelona",
				"prueba",
				true,
				false,
				null,
				new ArrayList<Particular>()
			);
		
		Oferta oferta = saOferta.save(transferOferta);
		
		int contP1 = oferta.getParticulares().size();
		
		TransferParticular tParticular = new TransferParticular("nombre", "Apellido Apellido", "99999919Z", "123456789", "particularPruebaHU8@particularPruebaEmail.com", "Abc1111", true, 0, "ejemplo");
		
		saParticular.save(tParticular);
		
		Particular particular = saParticular.buscarPorEmail(tParticular.getEmail());
		
		int contO1 = particular.getOfertas().size();
		
		oferta.inscribirParticular(particular);
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		saOferta.actualizarOferta(oferta);
		saParticular.actualizarParticular(particular);
		
		int contP2 = oferta.getParticulares().size();
		
		int contO2 = particular.getOfertas().size();
		
		boolean eq1 = false;
		boolean eq2 = false;
		
		if(contP1 == 0 && contP2 == 1) eq1 = true;
		if(contO1 == 0 && contO2 == 1) eq2 = true;
		
		assertEquals(eq1 && eq2, true);

	}
}
