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
public class HU10VerDetallesCandidatoTest {
	
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
		
		TransferParticular tParticular = new TransferParticular("nombre", "Apellido Apellido", "99999919E", "123456789", "particularPruebaHU9@particularPruebaEmail.com", "Abc1111", true, 0, "ejemplo");
		
		saParticular.save(tParticular);
		
		Particular particular = saParticular.buscarPorEmail(tParticular.getEmail());
		
		oferta.inscribirParticular(particular);
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		saOferta.actualizarOferta(oferta);
		Particular pGuardado = saParticular.actualizarParticular(particular);
		
		List<Particular> particulares = oferta.getParticulares();
		
		assertEquals(particulares.get(0), pGuardado);

	}
}
