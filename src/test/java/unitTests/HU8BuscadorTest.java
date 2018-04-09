package unitTests;

import static org.junit.Assert.*;

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
import conecta2.servicioAplicacion.SAOferta;
import conecta2.transfer.TransferOferta;




@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU8BuscadorTest {
	

	
	@Autowired
	private SAOferta saOferta;
	
	@Test
	public void testCorrectSearch() {
		Oferta oferta = new Oferta("oferta", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		TransferOferta transferOferta = new TransferOferta(oferta.getNombre(),
				oferta.getJornadaLaboral(),
				oferta.getContrato(),
				oferta.getVacantes(),
				oferta.getSalario(),
				oferta.getCiudad(),
				oferta.getDescripcion(),
				oferta.getActivo(),
				oferta.getFinalizada(),
				oferta.getEmpresa(),
				oferta.getParticulares());
		saOferta.save(transferOferta);
		
		String letraMayus = oferta.getNombre().substring(0, 1).toUpperCase();
		String nombreMayusPrim = letraMayus + oferta.getNombre().substring(1, oferta.getNombre().length());
		List<Oferta> listaOfertas = saOferta.buscarOfertasPorNombreYNombreMayus(oferta.getNombre(), nombreMayusPrim);
		
		assertEquals(listaOfertas.get(0).getNombre(), oferta.getNombre());
		
	}

}
