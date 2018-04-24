package unitTests;

import static org.junit.Assert.*;

import java.util.List;

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
import conecta2.repositorio.RepositorioOferta;
import conecta2.repositorio.RepositorioParticular;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU09InscribirseEnOfertaTest {
	
	@Autowired
	private RepositorioOferta repOferta;
	@Autowired
    private RepositorioParticular repParticular;

	@Test
	public void test0Candidatos() {
		Oferta oferta = new Oferta("oferta", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		
		repOferta.save(oferta);
		
		List<Particular> particulares = oferta.getParticularesInscritos();
		
		assertEquals(particulares.isEmpty(), true);
	}
	
	@Test
	public void test0Inscritos() {
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", 0, true, null, null);

		repParticular.save(particular);
		List<Oferta> ofertas = particular.getOfertas();
		assertEquals(ofertas.isEmpty(), true);
	}
	


}

