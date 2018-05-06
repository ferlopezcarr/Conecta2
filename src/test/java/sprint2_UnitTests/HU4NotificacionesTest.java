package sprint2_UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import conecta2.C2Aplicacion;
import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.repositorio.RepositorioEmpresa;
import conecta2.repositorio.RepositorioNotificacion;
import conecta2.repositorio.RepositorioOferta;
import conecta2.repositorio.RepositorioParticular;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = C2Aplicacion.class)
@DataJpaTest
public class HU4NotificacionesTest {
	
	@Autowired
    private RepositorioOferta repositorioOferta;
	
	@Autowired
    private RepositorioParticular repositorioParticular;
	
	@Autowired
    private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
    private RepositorioNotificacion repositorioNotificacion;
	
	/* Oferta finalizada, actualizar oferta, oferta eliminada */
	
	@Test
	public void testFinalizarOferta() {
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java", 1);
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		particular = repositorioParticular.save(particular);
		
		oferta.setEmpresa(empresa);
		
		oferta = repositorioOferta.save(oferta);
		
		empresa = repositorioEmpresa.save(empresa);
		
		//Inscribimos al particular
		oferta.inscribirParticular(particular);
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		oferta = repositorioOferta.save(oferta);
		particular = repositorioParticular.save(particular);
		
		//Finalizamos la oferta
		oferta.setFinalizada(true);
		oferta = repositorioOferta.save(oferta);
		
		//Guardamos la notificación
		Notificacion notificacion = new Notificacion();
		notificacion.setParticular(particular);
		notificacion.setSiguiente("/verOferta?id=" + oferta.getId());
		notificacion.setDescripcion("Oferta finalizada");
		repositorioNotificacion.save(notificacion);
		
		//Guardamos las notificaciones del particular
		List<Notificacion> notificacionesParticular = repositorioNotificacion.findByParticularAndLeidaFalse(particular);
		
		//Comprobamos si la oferta está finalizada y el particular tiene su notificación
		boolean isFinished = oferta.getFinalizada();
		boolean hasNotification = notificacionesParticular.contains(notificacion);
		
		assertEquals(true, isFinished && hasNotification);
	}
	
	@Test
	public void testActualizarOferta() {
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java", 1);
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		particular = repositorioParticular.save(particular);
		
		oferta.setEmpresa(empresa);
		
		oferta = repositorioOferta.save(oferta);
		
		empresa = repositorioEmpresa.save(empresa);
		
		//Inscribimos al particular
		oferta.inscribirParticular(particular);
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		oferta = repositorioOferta.save(oferta);
		particular = repositorioParticular.save(particular);
		
		//Modificamos la oferta
		Oferta ofertaModificada = oferta;
		ofertaModificada.setNombre("ofertaModificada");
		ofertaModificada = repositorioOferta.save(ofertaModificada);
		
		//Guardamos la notificación
		Notificacion notificacion = new Notificacion();
		notificacion.setParticular(particular);
		notificacion.setSiguiente("/verOferta?id=" + ofertaModificada.getId());
		notificacion.setDescripcion("Oferta modificada");
		repositorioNotificacion.save(notificacion);
		
		//Guardamos las notificaciones del particular
		List<Notificacion> notificacionesParticular = repositorioNotificacion.findByParticularAndLeidaFalse(particular);
		
		//Comprobamos si la oferta está modificada y el particular tiene su notificación
		boolean areDifferent = !oferta.equals(ofertaModificada);
		boolean hasNotification = notificacionesParticular.contains(notificacion);
		
		assertEquals(true, areDifferent && hasNotification);
	}
	
	@Test
	public void testEliminarOferta() {
		Oferta oferta = new Oferta("oferta2", JornadaLaboral.PorHoras, Contrato.Formación, 1, 230.0, "Barcelona", "prueba", true, false, null, new ArrayList<Particular>(), "html java", 1);
		
		Empresa empresa = new Empresa("empresaPruebaNombre", "A28599033", "123456789", "empresaPruebaEmail@empresaPruebaEmail.com", "Abc1111", "", 0, true, null, null);
		
		Particular particular = new Particular("particularPruebaNombre", "Apellido Apellido", "99999999Z", "123456789", "particularPruebaEmail@particularPruebaEmail.com", "Abc1111", "", true, null, null);
		
		particular = repositorioParticular.save(particular);
		
		oferta.setEmpresa(empresa);
		
		oferta = repositorioOferta.save(oferta);
		
		empresa = repositorioEmpresa.save(empresa);
		
		//Inscribimos al particular
		oferta.inscribirParticular(particular);
		particular.anadirOferta(oferta);
		
		//Guardamos la oferta
		oferta = repositorioOferta.save(oferta);
		particular = repositorioParticular.save(particular);
		
		//Eliminamos la oferta
		oferta.setActivo(false);
		oferta = repositorioOferta.save(oferta);
		
		//Guardamos la notificación
		Notificacion notificacion = new Notificacion();
		notificacion.setParticular(particular);
		notificacion.setSiguiente("/verOferta?id=" + oferta.getId());
		notificacion.setDescripcion("Oferta eliminada");
		repositorioNotificacion.save(notificacion);
		
		//Guardamos las notificaciones del particular
		List<Notificacion> notificacionesParticular = repositorioNotificacion.findByParticularAndLeidaFalse(particular);
		
		//Comprobamos si la oferta está eliminada y el particular tiene su notificación
		boolean isNotActive = !oferta.getActivo();
		boolean hasNotification = notificacionesParticular.contains(notificacion);
		
		assertEquals(true, isNotActive && hasNotification);
	}

}
