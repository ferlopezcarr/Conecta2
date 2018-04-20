package integrationTests;
import static org.junit.Assert.*;

import java.util.ArrayList;

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
import conecta2.modelo.Particular;
import conecta2.servicioAplicacion.SAOferta;
import conecta2.transfer.TransferOferta;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU11FinalizarOferta {

	@Autowired
	private SAOferta saOferta;
	
	@Test
	public void testModifyExistingCompany() {

		TransferOferta transferOferta = new TransferOferta(
				"oferta", 
				JornadaLaboral.PorHoras,
				Contrato.Formación,
				1,
				230.0,
				"Madrid",
				"prueba",
				true,
				false,
				null,
				new ArrayList<Particular>()
			);
		
		Oferta ofertaNoFinalizada = saOferta.save(transferOferta);
		
		Oferta oferta = ofertaNoFinalizada;
		 		
		oferta.setFinalizada(true);
		 
		oferta = saOferta.actualizarOferta(oferta);
				
		assertNotEquals(oferta.getFinalizada(), ofertaNoFinalizada.getFinalizada());
	}
}