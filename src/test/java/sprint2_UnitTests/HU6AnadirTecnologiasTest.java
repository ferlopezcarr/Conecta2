package sprint2_UnitTests;

import static org.junit.Assert.assertNotEquals;


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
import conecta2.repositorio.RepositorioOferta;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")


public class HU6AnadirTecnologiasTest {
	@Autowired
    private RepositorioOferta repositorioOferta;
	
	@Test
	public void testAnadirTecnologias() {
	     
        Oferta ofertaSinAnadir = new Oferta("ofertaNoFinalizada", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null, null);
		
		Oferta sinTecnologia = repositorioOferta.save(ofertaSinAnadir);
		
		Oferta ofertaAnadidaTec = new Oferta("ofertaNoFinalizada", JornadaLaboral.PorHoras, Contrato.Formación, 1, 200.0, "Madrid", "", true, false, null, null, "html css");
		 
		Oferta conTecnologia = repositorioOferta.save(ofertaAnadidaTec);
		
		assertNotEquals(sinTecnologia, conTecnologia);
		
		

	}
}
