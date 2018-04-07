package conecta2.controlador;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.modelo.Rol;
import conecta2.servicioAplicacion.SAEmail;
import conecta2.servicioAplicacion.SAEmpresa;
import conecta2.servicioAplicacion.SAOferta;
import conecta2.servicioAplicacion.SAParticular;
import conecta2.transfer.TransferParticular;
import conecta2.transfer.TransferEmpresa;
import conecta2.transfer.TransferOferta;
/**
 * Controlador de la aplicación, en él se mapean las diferentes peticiones (GET, POST...),
 * @author ferlo
 * Se redirige entre vistas y hace uso de los Servicios de Aplicación
 */
@Controller
public class ControladorPrincipal {	
	@Autowired	
	private SAParticular saParticular;
	@Autowired
	private SAEmpresa saEmpresa;
	@Autowired
	private SAEmail saEmail;
	@Autowired
	private SAOferta saOferta;
	
	private ModelAndView modeloyVista;
	
	public  ModelAndView obtenerInstancia() {

		if (modeloyVista == null) {
			
			modeloyVista = new ModelAndView();
		}

		return modeloyVista;
	}
	
	/**
	 * Método que captura las peticiones GET de /login
	 * @return devuelve la vista de Inicio de sesion
	 */
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	/**
	 * Método que captura las peticiones GET de /crear-cuenta
	 * @return devuelve la vista para Crear una cuenta, mostrando por defecto el registro de empresa
	 */
	@RequestMapping(value="/crear-cuenta", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.addObject("transferParticular", new TransferParticular());
		modelAndView.addObject("transferEmpresa", new TransferEmpresa());
		modelAndView.setViewName("crearCuenta");
		return modelAndView;
	}
	
	/**
	 * Método que captura las peticiones POST de /crear-empresa
	 * @param transferEmpresa que recibe para insertar la empresa con los datos
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return redirige a inicio si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value = "/crear-empresa", method = RequestMethod.POST)
	public ModelAndView crearEmpresa (@ModelAttribute ("transferEmpresa") @Valid TransferEmpresa transferEmpresa, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Empresa empresa = saEmpresa.buscarPorEmail(transferEmpresa.getEmail());
		Empresa cif = saEmpresa.buscarPorCif(transferEmpresa.getCif());
		
		TransferParticular transferParticular = new TransferParticular();
		
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
			modelAndView = new ModelAndView("redirect:/");
		}
		
		return modelAndView;
	}
	
	/**
	 * Método que captura las peticiones POST de /crear-particular
	 * @param transferParticular que recibe para insertar el particular con los datos
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return redirige a inicio si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value = "/crear-particular", method = RequestMethod.POST)
	public ModelAndView crearParticular(@ModelAttribute("transferParticular") @Valid TransferParticular transferParticular, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Particular particular = saParticular.buscarPorEmail(transferParticular.getEmail());
		Particular DNI = saParticular.buscarPorDni(transferParticular.getDni());
		TransferEmpresa transferEmpresa = new TransferEmpresa();
		
		if (!transferParticular.getPassword().equals(transferParticular.getPasswordConfirmacion())) {
			bindingResult.rejectValue("password", "error.transferParticular", "* Las contraseñas no coinciden");
		}
		if (particular != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "* Ya existe un particular con este e-mail");		
		if(DNI != null)
			bindingResult.rejectValue("dni", "error.dtoUsuario", "* Ya existe un particular con este DNI");
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
	
	
	
	/**
	 * Método que autentica al usuario capturando la petición GET de /authorization
	 * @param val usuario que se autentica
	 * @return redirige a la página principal si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value="/authorization", method = RequestMethod.GET, params = {"val"})
	public ModelAndView autorizacion(@RequestParam("val") String val){ 
		Object obj = saEmail.validaUsuario(val);
		ModelAndView modelAndView = obtenerInstancia();	

		if(obj==null) {
			//MOSTRAR MENSAJE DE ERROR
			modelAndView = new ModelAndView("redirect:/login");
			String msg = "Código de confirmación incorrecto";
			modelAndView.addObject("errorPopUp", msg);
		}
		else{
					modelAndView.setViewName("redirect:/login");
			}			
		
		return modelAndView;
	}
	
	/**
	 * Método que captura la petición GET de /empresa/perfil
	 * @param val usuario que se autentica
	 * @return redirige a la página que muestra el perfil si no ha habido fallos,
	 *  en caso contrario redirige al login y notifica
	 */
	@RequestMapping(value ="/empresa/perfil", method = RequestMethod.GET)
    public ModelAndView mostrarPerfilEmpresa() {		
			
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa  emp = (Empresa)mod.get("empresa");
		
		
		Empresa empresa = saEmpresa.buscarPorId(emp.getId());	
		TransferEmpresa transferEmpresa = new TransferEmpresa(empresa.getNombreEmpresa(), empresa.getCif(), empresa.getTelefono(), 
				empresa.getEmail(), "0", "0", empresa.getDescripcion(), true);
		
		modelAndView.addObject("transferEmpresa", transferEmpresa);
		modelAndView.setViewName("perfilEmpresa");
		
		return modelAndView;
    }
	
	@RequestMapping(value ="/empresa/modificar", method = RequestMethod.GET)
    public ModelAndView modificarPerfilEmpresa() {		
				
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa  emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = saEmpresa.buscarPorId(emp.getId());
		
		TransferEmpresa transferEmpresa = new TransferEmpresa(empresa.getNombreEmpresa(), empresa.getCif(), 
				empresa.getTelefono(), empresa.getEmail(), "0", "0", empresa.getDescripcion(), true);
		
		modelAndView.addObject("transferEmpresa", transferEmpresa);
		modelAndView.setViewName("modificarEmpresa");
		
		return modelAndView;
    }
	
	@RequestMapping(value = "/empresa/modificar", method = RequestMethod.POST)
	public ModelAndView modificarPerfilEmpresa(@ModelAttribute("transferEmpresa") TransferEmpresa transferEmpresa,BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Empresa empresa = saEmpresa.buscarPorEmail(transferEmpresa.getEmail());
		if(!transferEmpresa.getNombreEmpresa().matches("^(?!\\s*$).+")) { //Patron not empty
			bindingResult.rejectValue("nombreEmpresa", "error.transferEmpresa", "* Introduzca un nombre");
		}
		if(!transferEmpresa.getTelefono().matches("^([0-9]{9})*$")) {
			bindingResult.rejectValue("telefono", "error.transferEmpresa", "* Introduzca un teléfono válido");
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("modificarEmpresa", bindingResult.getModel());
			modelAndView.addObject("transferEmpresa", transferEmpresa);
		}else {
			saEmpresa.save(transferEmpresa);
			modelAndView = new ModelAndView("redirect:/empresa/perfil?id="+ empresa.getId());

		}

		return modelAndView;
	}
	
	
	
	@RequestMapping(value ="/particular/perfil", method = RequestMethod.GET)
    public ModelAndView mostrarPerfilParticular() {		
		
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Particular par = (Particular)mod.get("particular");
		
		Particular particular = saParticular.buscarPorId(par.getId());				
		TransferParticular tParticular = new TransferParticular(particular.getNombre(), particular.getApellidos(), particular.getDni(),
				particular.getTelefono(), particular.getEmail(), "0", true, particular.getPuntuacion(),particular.getDescripcion());
		modelAndView.addObject("transferParticular", tParticular);
		modelAndView.setViewName("perfilParticular");
		
		return modelAndView;
    }
	
	@RequestMapping(value ="/particular/modificar", method = RequestMethod.GET)
    public ModelAndView modificarPerfilParticular() {		
ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Particular par = (Particular)mod.get("particular");
		
		Particular particular = saParticular.buscarPorId(par.getId());	
		TransferParticular tParticular = new TransferParticular(particular.getNombre(), particular.getApellidos(), particular.getDni(),
				particular.getTelefono(), particular.getEmail(), "0", true, particular.getPuntuacion(),particular.getDescripcion());
		
		modelAndView.addObject("transferParticular", tParticular);
		modelAndView.setViewName("modificarParticular"); //Cambiar a la vista de modificar particular
		
		return modelAndView;
    }
	
	@RequestMapping(value ="/particular/modificar", method = RequestMethod.POST)
    public ModelAndView modificarPerfilParticular(@ModelAttribute("transferParticular") TransferParticular transferParticular, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Particular particular = saParticular.buscarPorEmail(transferParticular.getEmail());
		if (!transferParticular.getNombre().matches("^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$")) {		
			bindingResult.rejectValue("nombre", "error.transferParticular", "* Introduzca únicamente letras");
		}
		if (!transferParticular.getApellidos().matches("^([a-zA-ZáéíóúñÁÉÍÓÚÑ ])*$")) {		
			bindingResult.rejectValue("apellidos", "error.transferParticular", "* Introduzca únicamente letras");
		}
		if(!transferParticular.getTelefono().matches("^([0-9]{9})*$")) {
			bindingResult.rejectValue("telefono", "error.transferParticular", "* Introduzca un teléfono válido");
		}
		if (bindingResult.hasErrors()) {		
			modelAndView = new ModelAndView("modificarParticular", bindingResult.getModel());
			modelAndView.addObject("transferParticular", transferParticular);
		}	
		else {
			saParticular.save(transferParticular);
			modelAndView = new ModelAndView("redirect:/particular/perfil?id=" + particular.getId());
		}		
		return modelAndView;
    }
	
	
	
	
	
	// ------------- OFERTAS ------------- //
	
	@RequestMapping("/paginaMenuEmpresa")
	public String paginaMenuEmpresa(){
		return "misOfertasEmpresa";
	}
	
	@RequestMapping(value ="/ofertas", method = RequestMethod.GET)
    public ModelAndView mostrarOfertas() {			
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.addObject("listaOfertas", saOferta.buscarTodas());
		modelAndView.setViewName("mostrarOfertas"); //Cambiar a la vista de modificar particular
		
		return modelAndView;
    }
	
	/*@RequestMapping("/verOferta")
	public String verOferta(){
		return "verOferta";
	}*/
	
	//FALTA TERMINARLO!!!!!!!
	@RequestMapping(value ="/verOferta", method = RequestMethod.GET, params = {"id"})
    public ModelAndView mostrarOfertaEmpresa(@RequestParam("id") int id) {		
		Oferta oferta = saOferta.buscarPorId(id);
		
	
		ModelAndView modelAndView = new ModelAndView();
		TransferOferta tOferta = new TransferOferta(oferta.getNombre(),oferta.getJornadaLaboral(), oferta.getContrato(), oferta.getVacantes(), oferta.getSalario(), oferta.getCiudad(), oferta.getDescripcion(),
				true, oferta.getEmpresa(), oferta.getParticulares());
		
		
		
		modelAndView.addObject("transferOferta", tOferta);
		modelAndView.setViewName("verOferta");
		
		return modelAndView;
    }
	
	
	
	
	@RequestMapping(value="/crear-oferta", method = RequestMethod.GET)
	public ModelAndView crearOferta(){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.addObject("transferOferta", new TransferOferta());
		modelAndView.addObject("jornadaValues", JornadaLaboral.values());
		modelAndView.addObject("contratoValues", Contrato.values());
		modelAndView.setViewName("crearOferta");
		return modelAndView;
	}
	
	@RequestMapping(value="/crear-oferta", method = RequestMethod.POST)
	public ModelAndView crearOfertaPost(@ModelAttribute("transferOferta") @Valid TransferOferta transferOferta, BindingResult bindingResult){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.addObject("transferOferta", new TransferOferta());
		modelAndView.setViewName("crearOferta");
	
		//Oferta oferta = saOferta.buscarPorId(transferOferta.getId());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Empresa empresa = saEmpresa.buscarPorEmail(auth.getName());

		transferOferta.getJornada();
		
		/*
		if (transferOferta.containsJornada(transferOferta.getJornadaLaboral().toString()))
			bindingResult.rejectValue("jornada", "error.transferOferta", "* Jornada laboral no válida");
		if (transferOferta.containsContrato(transferOferta.getContrato().toString()))
			bindingResult.rejectValue("contrato", "error.transferOferta", "* Tipo de contrato no válido");
			*/
		if(transferOferta.getVacantes() == null) {
			bindingResult.rejectValue("vacantes", "error.transferOferta", "* Debes introducir un número");
		}
			
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearOferta", bindingResult.getModel());
			modelAndView.addObject("transferOferta", transferOferta);
		}			
		else {
			transferOferta.setEmpresa(empresa);
			saOferta.crearOferta(transferOferta);
			Oferta oferta = saOferta.buscarPorNombreAndJornadaAndContratoAndEmpresa(transferOferta.getNombre(), transferOferta.getJornada(), transferOferta.getContrato(), transferOferta.getEmpresa());
			modelAndView = new ModelAndView("redirect:/verOferta?id=" + oferta.getId());
		}
		
		//modelAndView.addObject("roles", Rol.values());
		
		return modelAndView;
	}
	
	
	/**
	 * Método que añade al particular como variable permanente para el modelo
	 * @param model modelo al que se le inserta el particular
	 */
	//Esta anotación nos permite establecer variables permanentes para el modelo
	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Particular particular = saParticular.buscarPorEmail(auth.getName());
		Empresa empresa = saEmpresa.buscarPorEmail(auth.getName());
		
		model.addAttribute("particular", particular);
		model.addAttribute("empresa", empresa);//En este caso el objeto usuario estará permanentemente en todas las vistas por el @ModelAttribute 
		this.modeloyVista = new ModelAndView("/ofertas","modelo", model);
	}
	
}
