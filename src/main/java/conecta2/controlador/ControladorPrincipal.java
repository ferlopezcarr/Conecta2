package conecta2.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import conecta2.modelo.Empresa;
import conecta2.modelo.Particular;
import conecta2.modelo.Rol;
import conecta2.servicioAplicacion.SAEmpresa;
import conecta2.servicioAplicacion.SAParticular;
import conecta2.transfer.TransferParticular;
import conecta2.transfer.TransferEmpresa;

//Controlador de la aplicación, en él se mapean las diferentes peticiones (GET, POST...),
//se redirige entre vistas y se hace uso de los Servicios de Aplicación
@Controller
public class ControladorPrincipal {	
	@Autowired	
	private SAParticular saParticular;
	@Autowired
	private SAEmpresa saEmpresa;
	
	//La forma de mapear es con la anotación @RequestMapping, seguido de la url y la petición
	//asociada. El @RequestMapping afecta únicamente a la función que tiene inmediatamente debajo,
	//la cual se invoca automáticamente tras la petición a dicha url.
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	//Todas las vistas se devuelve mediante el objeto ModelAndView, que de forma resumida es un
	//"contenedor" de todo lo que va a llevar la vista (objetos, variables, el html...)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index"); //Aquí indicamos que insertaremos como vista el index.html al modelAndView (sin el '.html')
		return modelAndView;
	}
	
	//Una misma url puede mapearse para varios tipos de peticiones, en este caso '/registro' es GET, y en el siguiente es POST
	@RequestMapping(value="/crear-cuenta", method = RequestMethod.GET)
	public ModelAndView registration(){ //En este caso estamos generando el formulario de registro
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("transferParticular", new TransferParticular()); //Añadimos al modelAndView el objeto dtoUsuario, que se recogerá en el <form> como th:object="${dtoUsuario}"
		modelAndView.addObject("transferEmpresa", new TransferEmpresa());
		modelAndView.setViewName("crearCuenta"); //Agregamos como vista el registro.html
	
		return modelAndView;
	}
	
	@RequestMapping(value = "/crear-particular", method = RequestMethod.POST)
	//Recogemos el @ModelAttribute que se nos ha mandado por post y su binding
	public ModelAndView crearParticular(@ModelAttribute("transferParticular") @Valid TransferParticular transferParticular, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Particular particular = saParticular.buscarPorEmail(transferParticular.getEmail());
		
		//lo creamos para pasarselo vacio a la pagina html porque sino no compila
		TransferEmpresa transferEmpresa = new TransferEmpresa();
		
		//Si hay errores los binds muestran los fallos
		if (!transferParticular.getPassword().equals(transferParticular.getPasswordConfirmacion())) {
			bindingResult.rejectValue("password", "error.transferParticular", "* Las contraseñas no coinciden");
		}
		if (particular != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "* Ya existe un particular con este e-mail");		
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearCuenta", bindingResult.getModel());
			modelAndView.addObject("transferParticular", transferParticular);
			modelAndView.addObject("transferEmpresa", transferEmpresa);
		}			
		else {
			saParticular.crearParticular(transferParticular);
			modelAndView = new ModelAndView("redirect:/");
		}
		
		modelAndView.addObject("roles", Rol.values());
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/crear-empresa", method = RequestMethod.POST)
	//Recogemos el @ModelAttribute que se nos ha mandado por post y su binding
	public ModelAndView crearEmpresa (@ModelAttribute ("transferEmpresa") @Valid TransferEmpresa transferEmpresa, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Empresa empresa = saEmpresa.buscarPorEmail(transferEmpresa.getEmail());
		Empresa cif = saEmpresa.buscarPorCif(transferEmpresa.getCif());
		
		//lo creamos para pasarselo vacio a la pagina html porque sino no compila
		TransferParticular transferParticular = new TransferParticular();
		
		//Si hay errores los binds muestran los fallos
		if (!transferEmpresa.getPassword().equals(transferEmpresa.getPasswordConfirmacion())) {
			bindingResult.rejectValue("password", "error.transferEmpresa", "* Las contraseñas no coinciden");
		}
		if (empresa != null)
			bindingResult.rejectValue("email", "error.transferEmpresa", "* Ya existe una empresa con este e-mail");	
		
		if (cif != null)
			bindingResult.rejectValue("cif", "error.transferEmpresa", "* Ya existe una empresa con este CIF");
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearCuenta", bindingResult.getModel());
			modelAndView.addObject("transferEmpresa", transferEmpresa);
			modelAndView.addObject("transferParticular", transferParticular);
			
		}		
		else {
			saEmpresa.crearEmpresa(transferEmpresa);
			modelAndView = new ModelAndView("redirect:/menu");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/menu", method = RequestMethod.GET)
	public ModelAndView mostrarMenu(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("menu");
		return modelAndView;
	}
	
	@RequestMapping("/informacion")
    public String mostrarInformacion() { //Si queremos mostrar sólo una vista podemos devolver el String del nombre del fichero html en cuestión
        return "informacion";
    }
	
	//Esta anotación nos permite establecer variables permanentes para el modelo
	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Particular particular = saParticular.buscarPorEmail(auth.getName());
		model.addAttribute("particular", particular); //En este caso el objeto usuario estará permanentemente en todas las vistas por el @ModelAttribute 
	}
	
	/* --- IMPORTANTE --- THYMELEAF
	 * 
	 * Para recoger en la vista todos los objetos/variables que se lanzan desde el Controlador, se utiliza thymeleaf
	 * y se importa desde la vista, en la etiqueta html, asi:
	 * 
	 * <html lang="es" xmlns:th="http://www.thymeleaf.org">
	 * 
	 * Elementos importantes de thymeleaf:
	 * 
	 * th:object="${dtoUsuario}" -> Objeto que se mandará en el formulario
	 * th:field="*{apellidos}" -> Campo de un objeto que se mandará en el formulario
	 * th:action="@{/registro}" -> url a la que mandaremos el objeto del formulario
	 * th:if="${usuario != null}" -> Mostrará la etiqueta en la que se encuentre si cumple la condición
	 * th:href -> enlace a una url
	 * th:class -> definir una clase de html/css para una etiqueta
	 * th:text="${usuario.nombre}" -> Texto que mostrará en la etiqueta en la que se encuentre
	 * $ -> para representar variables
	 * @ -> para representar url's
	 * 
	 * Página con la documentación de thymeleaf, está muy bien explicado y trae ejemplos:
	 * https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html 
	 * 
	 * --- IMPORTANTE --- APPLICATION.PROPERTIES
	 * 
	 * El application.properties contiene toda la configuración del proyecto, configuración de la BD, JPA, etc
	 * 
	 * --- IMPORTANTE --- DATA-MYSQL.SQL
	 * 
	 * El data-mysql.sql lo carga automáticamente Spring por defecto tras crear la Base de Datos con las entidades JPA de cada
	 * clase, y ejecuta todas sus consultas
	 * 
	 * --- IMPORTANTE --- POM.XML
	 * 
	 * Este xml contiene todas las dependencias de Maven (mysql, jpa, springboot, springsecurity, etc) cuando se quiera añadir 
	 * una nueva dependencia se ha de insertar en este fichero. En teoría no haría falta ninguna más.	 * 
	 * 
	 */
}
