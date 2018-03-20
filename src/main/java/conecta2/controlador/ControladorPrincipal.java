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

import conecta2.modelo.Usuario;
import conecta2.servicioAplicacion.SARol;
import conecta2.servicioAplicacion.SAUsuario;
import conecta2.transfer.TransferUsuario;

//Controlador de la aplicación, en él se mapean las diferentes peticiones (GET, POST...),
//se redirige entre vistas y se hace uso de los Servicios de Aplicación
@Controller
public class ControladorPrincipal {	
	@Autowired	
	private SAUsuario saUsuario;
	@Autowired
	private SARol servicioRol;
	
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
	@RequestMapping(value="/registro", method = RequestMethod.GET)
	public ModelAndView registration(){ //En este caso estamos generando el formulario de registro
		ModelAndView modelAndView = new ModelAndView();
		TransferUsuario dtoUsuario = new TransferUsuario(); //Transfer de Usuario (Data Transfer Object)
		modelAndView.addObject("dtoUsuario", dtoUsuario); //Añadimos al modelAndView el objeto dtoUsuario, que se recogerá en el <form> como th:object="${dtoUsuario}"	
		modelAndView.addObject("roles", servicioRol.findAll()); //Añadimos los roles
		modelAndView.addObject("usuarioRegistrado", false); //Añadimos la variable encargada de mostrar el mensaje de éxito a falso
		modelAndView.setViewName("registro"); //Agregamos como vista el registro.html
		return modelAndView;
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	//Recogemos el @ModelAttribute que se nos ha mandado por post y su binding
	public ModelAndView createNewUser(@Valid @ModelAttribute("dtoUsuario") TransferUsuario dtoUsuario, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Usuario userExists = saUsuario.findUserByEmail(dtoUsuario.getEmail());
		
		//Si hay errores los binds muestran los fallos
		if (!dtoUsuario.getPassword().equals(dtoUsuario.getConfirmarPassword())) {
			bindingResult.rejectValue("password", "error.dtoUsuario", "* Las contraseñas no coinciden");
		}
		if (userExists != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "* Ya existe un usuario con este e-mail");		
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("registro", bindingResult.getModel());
			modelAndView.addObject("dtoUsuario", dtoUsuario);
		}			
		else {
			saUsuario.guardarUsuario(dtoUsuario);
			modelAndView = new ModelAndView("registro");
			modelAndView.addObject("dtoUsuario", new TransferUsuario());
			modelAndView.addObject("usuarioRegistrado", true);
		}
		
		modelAndView.addObject("roles", servicioRol.findAll());
		
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
		Usuario usuario = saUsuario.findUserByEmail(auth.getName());
		model.addAttribute("usuario", usuario); //En este caso el objeto usuario estará permanentemente en todas las vistas por el @ModelAttribute 
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
