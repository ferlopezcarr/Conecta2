package sprint2_IntegrationTests;

import static org.junit.Assert.assertEquals;

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
public class HU4NotificacionesTest {

	@Autowired
	SAEmpresa saEmpresa;
	
	@Autowired
	SAParticular saParticular;
	
	@Autowired
	SAOferta saOferta;
	
	@Autowired
	SANotificacion saNotificacion;
	
	/* Oferta finalizada, actualizar oferta, oferta eliminada */
	
	@Test
	public void testFinalizarOferta() {
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
		
		//Finalizamos la oferta
		oferta.setFinalizada(true);
		oferta = saOferta.save(oferta);
		
		//Guardamos la notificación
		saNotificacion.notificarParticularesOfertaFinalizada(oferta);
		
		//Guardamos las notificaciones del particular
		List<Notificacion> notificacionesParticular = saNotificacion.buscarPorParticular(particular);
		
		//Comprobamos si la oferta está finalizada y el particular tiene su notificación
		boolean isFinished = oferta.getFinalizada();
		boolean hasNotification = !notificacionesParticular.isEmpty();
		
		assertEquals(true, isFinished && hasNotification);
	}
	
	@Test
	public void testActualizarOferta() {
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
		
		//Modificamos la oferta
		Oferta ofertaNoModificada = new Oferta(oferta);
		oferta.setNombre("ofertaModificada");
		Oferta ofertaModificada = saOferta.save(oferta);
		
		//Guardamos la notificación
		saNotificacion.notificarParticularActualizarOferta(oferta);
		
		//Guardamos las notificaciones del particular
		List<Notificacion> notificacionesParticular = saNotificacion.buscarPorParticular(particular);
		
		//Comprobamos si la oferta está modificada y el particular tiene su notificación
		boolean areDifferent = !ofertaNoModificada.equals(ofertaModificada);
		boolean hasNotification = !notificacionesParticular.isEmpty();
		
		assertEquals(true, areDifferent && hasNotification);
	}
	
	@Test
	public void testEliminarOferta() {
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
		
		//Eliminamos la oferta
		saOferta.eliminarOferta(oferta.getId());
		oferta = saOferta.buscarPorId(oferta.getId());
		
		//Guardamos la notificación
		saNotificacion.notificarParticularOfertaEliminada(oferta);
		
		//Guardamos las notificaciones del particular
		List<Notificacion> notificacionesParticular = saNotificacion.buscarPorParticular(particular);
		
		//Comprobamos si la oferta está eliminada y el particular tiene su notificación
		boolean isNotActive = !oferta.getActivo();
		boolean hasNotification = !notificacionesParticular.isEmpty();
		
		assertEquals(true, isNotActive && hasNotification);
	}
	
}
