package sprint2_IntegrationTests;

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
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.servicioAplicacion.SAEmpresa;
import conecta2.servicioAplicacion.SANotificacion;
import conecta2.servicioAplicacion.SAOferta;
import conecta2.servicioAplicacion.SAParticular;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
@ComponentScan(basePackages ="conecta2")
public class HU5NotificacionInscripcionTest {
	
	@Autowired
	SAEmpresa saEmpresa;
	
	@Autowired
	SAParticular saParticular;
	
	@Autowired
	SAOferta saOferta;
	
	@Autowired
	SANotificacion saNotificacion;

	@Test
	public void test() {
		
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java", 1);
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		particular = saParticular.save(particular);
		
		oferta.setEmpresa(empresa);
		
		oferta = saOferta.save(oferta);
		
		empresa = saEmpresa.save(empresa);
		
		//Inscribimos al particular
		oferta.inscribirParticular(particular);
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		oferta = saOferta.save(oferta);
		particular = saParticular.save(particular);
		
		saNotificacion.notificarEmpresaNuevaInscripcion(oferta);
		
		Notificacion notificacion = new Notificacion();
		notificacion.setEmpresa(oferta.getEmpresa());
		notificacion.setSiguiente("/candidatos?id=" + oferta.getId());
		notificacion.setDescripcion("!Hay nuevos candidatos en tu oferta:'" + oferta.getNombre() + "'¡");
		saNotificacion.save(notificacion);
		
		List<Notificacion> notsEmpresaBD = saNotificacion.buscarPorEmpresa(empresa);
		
		boolean hasParticular = oferta.getParticularesInscritos().contains(particular);
		boolean hasNotification = notsEmpresaBD.contains(notificacion);
		
		assertEquals(true, hasParticular && hasNotification);
	}

}
