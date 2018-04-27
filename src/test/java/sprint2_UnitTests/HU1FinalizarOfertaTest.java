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
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioOferta;
import conecta2.repositorio.RepositorioParticular;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU1FinalizarOfertaTest {

	@Autowired
    private RepositorioOferta repositorioOferta;
	
	@Autowired
    private RepositorioParticular repositorioParticular;
	
	@Test
	public void testSeleccionarCandidato() {
		Oferta oferta = new Oferta("ofertaNoFinalizada", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		
		repositorioOferta.save(oferta);
		
		Oferta ofertaSinCandidatos = new Oferta(oferta);
		
		Particular candidato = repositorioParticular.findById(1);
		oferta.getParticularesSeleccionados().add(candidato);
		candidato.getOfertasSeleccionados().add(oferta);
		Oferta ofertaConCandidatos= repositorioOferta.save(oferta);
		repositorioParticular.save(candidato);
		
		assertNotEquals(ofertaSinCandidatos, ofertaConCandidatos);
	}
	
	@Test
	public void testFinalizarOferta() {
		Oferta oferta = new Oferta("ofertaNoFinalizada", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null);
		
		repositorioOferta.save(oferta);
		
		Oferta ofertaNoFinalizada = new Oferta(oferta);
		
		oferta.setFinalizada(true);
		 
		Oferta ofertaFinalizada = repositorioOferta.save(oferta);
		
		assertNotEquals(ofertaNoFinalizada, ofertaFinalizada);
	}
}
