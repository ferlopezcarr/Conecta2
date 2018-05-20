package conecta2.controlador;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import conecta2.modelo.Contrato;
import conecta2.modelo.Empresa;
import conecta2.modelo.JornadaLaboral;
import conecta2.modelo.Notificacion;
import conecta2.modelo.Oferta;
import conecta2.modelo.Particular;
import conecta2.modelo.Rol;
import conecta2.servicioAplicacion.SAEmail;
import conecta2.servicioAplicacion.SAEmpresa;
import conecta2.servicioAplicacion.SANotificacion;
import conecta2.servicioAplicacion.SAOferta;
import conecta2.servicioAplicacion.SAParticular;
import conecta2.transfer.TransferParticular;
import conecta2.transfer.TransferEmpresa;
import conecta2.transfer.TransferOferta;
/**
 * Controlador de la aplicación, en él se mapean las diferentes peticiones (GET, POST...),
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
	
	@Autowired
	private SANotificacion saNotificacion;
	
	private ModelAndView modeloyVista;
	
	/**
	* Metodo que implementa el patron singleton para el atributo que maneja el modelo y la vista de la aplicacion
	* @return devuelve una nueva instancia en caso de que no haya una previamente instanciada
	**/
	public  ModelAndView obtenerInstancia() {

		if (modeloyVista == null) {
			modeloyVista = new ModelAndView();
		}

		return modeloyVista;
	}
	
	/**
	 * Método que captura las peticiones GET de /login y /
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
		Particular particular = saParticular.buscarPorEmail(transferEmpresa.getEmail());
		
		TransferParticular transferParticular = new TransferParticular();
		
		if (!transferEmpresa.getPassword().equals(transferEmpresa.getPasswordConfirmacion())) {
			bindingResult.rejectValue("password", "error.transferEmpresa", "* Las contraseñas no coinciden");
		}
		if (empresa != null)
			bindingResult.rejectValue("email", "error.transferEmpresa", "* Ya existe una empresa con este e-mail");	
		if (particular != null)
			bindingResult.rejectValue("email", "error.transferEmpresa", "* Ya existe un particular con este e-mail");
		if (cif != null)
			bindingResult.rejectValue("cif", "error.transferEmpresa", "* Ya existe una empresa con este CIF");
		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearCuenta", bindingResult.getModel());
			modelAndView.addObject("transferEmpresa", transferEmpresa);
			modelAndView.addObject("transferParticular", transferParticular);
		}		
		else {
			transferEmpresa.setActivo(true);
			saEmpresa.crearEmpresa(transferEmpresa);
			modelAndView = new ModelAndView("redirect:/");
		}
		
		return modelAndView;
	}
	
	/**
	 * Método que captura las peticiones POST de /crear-particular
	 * @param transferParticular objeto que recibe para insertar el particular con los datos
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return redirige a inicio si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value = "/crear-particular", method = RequestMethod.POST)
	public ModelAndView crearParticular(@ModelAttribute("transferParticular") @Valid TransferParticular transferParticular, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Particular particular = saParticular.buscarPorEmail(transferParticular.getEmail());
		Particular DNI = saParticular.buscarPorDni(transferParticular.getDni());
		Empresa empresa = saEmpresa.buscarPorEmail(transferParticular.getEmail());
		
		TransferEmpresa transferEmpresa = new TransferEmpresa();
		
		if (!transferParticular.getPassword().equals(transferParticular.getPasswordConfirmacion())) {
			bindingResult.rejectValue("password", "error.transferParticular", "* Las contraseñas no coinciden");
		}
		if (particular != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "* Ya existe un particular con este e-mail");
		if (empresa != null)
			bindingResult.rejectValue("email", "error.dtoUsuario", "* Ya existe una empresa con este e-mail");
		if(DNI != null)
			bindingResult.rejectValue("dni", "error.dtoUsuario", "* Ya existe un particular con este DNI");
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearCuenta", bindingResult.getModel());
			modelAndView.addObject("transferParticular", transferParticular);
			modelAndView.addObject("transferEmpresa", transferEmpresa);
			boolean done=true;
			modelAndView.addObject("msg", done);
		}			
		else {
			transferParticular.setActivo(true);
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

		if(obj==null) {//error validacion
			modelAndView = new ModelAndView("index");
			String msg = "¡Código de confirmación incorrecto!";
			modelAndView.addObject("popup", msg);
		}
		else{
			modelAndView.setViewName("index");
			String msg = "¡Cuenta verificada!";
			modelAndView.addObject("popup", msg);
		}	
		return modelAndView;
	}
	
	
	
	// ------------ LOGUEADO ------------  //
	
	
	// PERFIL EMPRESA
	
	/**
	 * Método que captura la petición GET de /empresa/perfil
	 * @return En caso de que la petición sea de un particular notifica el error y redirige a la vista mostrarOfertas,
	 * si todo ha sido correcto, muestra la vista de perfilEmpresa
	 */
	@RequestMapping(value ="/empresa/perfil", method = RequestMethod.GET)
    public ModelAndView mostrarPerfilEmpresa() {		
			
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa  emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = null;
		if(emp != null) {
			empresa = saEmpresa.buscarPorId(emp.getId());	
		}
		if(empresa != null) {
			modelAndView.addObject("transferEmpresa", TransferEmpresa.EntityToTransfer(empresa));
			modelAndView.setViewName("perfilEmpresa");
		}
		else {//particular
			String msg = "¡Su perfil es de particular!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		
		return modelAndView;
    }
	
	
	
	/**
	 * Método que captura la petición GET de /empresa/modificar
	 *  
	 * @return redirige a la vista modificarEmpresa, que muestra los atributos editables de la empresa en modo edición,
	 * en caso de que la petición la haga un particular, redirige a la vista mostrarOfertas y lo notifica
	 */
	@RequestMapping(value ="/empresa/modificar", method = RequestMethod.GET)
    public ModelAndView modificarPerfilEmpresa() {		
				
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa  emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = null;
		if(emp != null) {
			empresa = saEmpresa.buscarPorId(emp.getId());	
		}
		
		if(empresa != null) {
			modelAndView.addObject("transferEmpresa", TransferEmpresa.EntityToTransfer(empresa));
			modelAndView.setViewName("modificarEmpresa");
		}
		else {//particular
			String msg = "¡Su perfil es de particular!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		
		return modelAndView;
    }
	
	/**
	 * Método que captura la petición POST de /empresa/modificar
	 * @param transferEmpresa Objeto que recibe los datos de la empresa
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return si hay algún error al introducir los datos para modificar el perfil de empresa se notifica según corresponda 
	 * en caso de que todo sea correcto, se guardan en la base de datos los cambios realizados y se redirige al perfil de empresa
	 */
	@RequestMapping(value = "/empresa/modificar", method = RequestMethod.POST)
	public ModelAndView modificarPerfilEmpresa(@ModelAttribute("transferEmpresa") TransferEmpresa transferEmpresa,BindingResult bindingResult) {

		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa  emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = null;
		if(emp != null) {
			empresa = saEmpresa.buscarPorId(emp.getId());	
		}
		
		if(empresa != null) {
			if(!transferEmpresa.getNombreEmpresa().matches("^(?!\\s*$).+")) { //Patron not empty
				bindingResult.rejectValue("nombreEmpresa", "error.transferEmpresa", "* Introduzca un nombre");
			}
			if(!transferEmpresa.getTelefono().matches("^([0-9]{9})$")) {
				bindingResult.rejectValue("telefono", "error.transferEmpresa", "* Introduzca un teléfono válido");
			}
			
			if (bindingResult.hasErrors()) {
				modelAndView = new ModelAndView("modificarEmpresa", bindingResult.getModel());
				modelAndView.addObject("transferEmpresa", transferEmpresa);
			}else {
				//Cambios posibles
				empresa.setNombreEmpresa(transferEmpresa.getNombreEmpresa());
		 		empresa.setTelefono(transferEmpresa.getTelefono());
		 		empresa.setDescripcion(transferEmpresa.getDescripcion());
				
				saEmpresa.save(empresa);
				String msg = "¡Perfil Actualizado!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("perfilEmpresa");
			}
		}
		else {//particular
			String msg = "¡Su perfil es de particular!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}

		return modelAndView;
	}
	
	
	
	// PERFIL PARTICULAR
	
	/**
	 * Método que captura la petición GET de /particular/perfil
	 * @return En caso de que sea una empresa la que haga la petición se notifica el error y se redirige a mostrarOfertas
	 * En caso de que todo sea correcto, se redirige a la vista perfilParticular
	 */
	@RequestMapping(value ="/particular/perfil", method = RequestMethod.GET)
    public ModelAndView mostrarPerfilParticular() {		
		
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Particular par = (Particular)mod.get("particular");
		
		Particular particular = null;
		if(par != null) {
			particular = saParticular.buscarPorId(par.getId());	
		}
		
		if(particular != null) {
			modelAndView.addObject("transferParticular", TransferParticular.EntityToTransfer(particular));
			double puntuacionMedia = particular.getPuntuacionMedia();
			int numPuntuaciones = particular.getPuntuaciones().size();
			modelAndView.addObject("puntuacionMedia", puntuacionMedia);
			modelAndView.addObject("numValoraciones", numPuntuaciones);
			modelAndView.setViewName("perfilParticular");
		}
		else {//empresa
			String msg = "¡Su perfil es de empresa!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		
		return modelAndView;
    }
	
	
	/**
	 * Método que captura la petición GET de /particular/modificar
	 * @return En caso de que sea una empresa la que haga la petición se notifica el error y se redirige a mostrarOfertas
	 * En caso de que todo sea correcto, se redirige a la vista modificarParticular
	 */
	@RequestMapping(value ="/particular/modificar", method = RequestMethod.GET)
    public ModelAndView modificarPerfilParticular() {		
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Particular par = (Particular)mod.get("particular");
		
		Particular particular = null;
		if(par != null) {
			particular = saParticular.buscarPorId(par.getId());	
		}
		
		if(particular != null) {
			modelAndView.addObject("transferParticular", TransferParticular.EntityToTransfer(particular));
			modelAndView.setViewName("modificarParticular");
		}
		else {//empresa
			String msg = "¡Su perfil es de empresa!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		
		return modelAndView;
    }
	
	/**
	 * Método que captura la petición POST de /particular/modificar
	 * @param transferParticular Objeto que recibe los datos del particular
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return si hay algún error al introducir los datos para modificar el perfil de particular se notifica según corresponda 
	 * en caso de que todo sea correcto, se guardan en la base de datos los cambios realizados y se redirige al perfil de particular
	 */
	@RequestMapping(value ="/particular/modificar", method = RequestMethod.POST)
    public ModelAndView modificarPerfilParticular(@ModelAttribute("transferParticular") TransferParticular transferParticular, BindingResult bindingResult) {
		
		ModelAndView modelAndView = obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Particular par = (Particular)mod.get("particular");
		
		Particular particular = null;
		if(par != null) {
			particular = saParticular.buscarPorId(par.getId());	
		}
		
		if(particular != null) {
			if (!transferParticular.getNombre().matches("^[a-zA-ZáéíóúñÁÉÍÓÚÑ ]{1,}$")) {		
				bindingResult.rejectValue("nombre", "error.transferParticular", "* Introduzca únicamente letras");
			}
			if (!transferParticular.getApellidos().matches("^[a-zA-ZáéíóúñÁÉÍÓÚÑ ]{1,}$")) {		
				bindingResult.rejectValue("apellidos", "error.transferParticular", "* Introduzca únicamente letras");
			}
			if(!transferParticular.getTelefono().matches("^([0-9]{9})$")) {
				bindingResult.rejectValue("telefono", "error.transferParticular", "* Introduzca un teléfono válido");
			}
			
			if (bindingResult.hasErrors()) {//si hay errores
				modelAndView = new ModelAndView("modificarParticular", bindingResult.getModel());
				modelAndView.addObject("transferParticular", transferParticular);
			}	
			else {
				//Cambios posibles
				particular.setNombre(transferParticular.getNombre());
				particular.setApellidos(transferParticular.getApellidos());
				particular.setTelefono(transferParticular.getTelefono());
				particular.setDescripcion(transferParticular.getDescripcion());
				
				saParticular.save(particular);
				String msg = "¡Perfil Actualizado!";
				modelAndView.addObject("popup", msg);
				modelAndView.addObject("puntuacionMedia", particular.getPuntuacionMedia());
				modelAndView.addObject("numValoraciones", particular.getPuntuaciones().size());
				modelAndView.setViewName("perfilParticular");
			}
		}
		else {//empresa
			String msg = "¡Su perfil es de empresa!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
				
		return modelAndView;
    }
	
	
	
	
	
	// ------------- OFERTAS ------------- //
	
	
	/**
	 * Método que controla la petición GET de /ofertas
	 * @return se redirige a la vista mostrarOfertas, con todas las ofertas en la que uno está inscrito en caso de que el que 
	 * haya hecho la petición sea un particular
	 */
	@RequestMapping(value ="/ofertas", method = RequestMethod.GET)
    public ModelAndView mostrarOfertas() {			
		ModelAndView modelAndView = obtenerInstancia();		
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		Particular par = (Particular)mod.get("particular");
		modelAndView.addObject("emp", emp);
		modelAndView.addObject("par", par);
		modelAndView.addObject("listaOfertasBuscadas", null);
		
		if(emp != null) {// si es empresa
			modelAndView.addObject("listaOfertas", saOferta.buscarOfertasPorEmpresa(emp));
			modelAndView.addObject("listaOfertasFinalizadas", saOferta.buscarOfertasFinalizadas(emp));
		}
		else if(par != null) {
			modelAndView.addObject("listaOfertas", saOferta.buscarOfertasParticularInscrito(par));
			String text = "";
			modelAndView.addObject("text", text);
		}
		
		modelAndView.setViewName("mostrarOfertas");
		
		return modelAndView;
    }
	
	
	/**
	 * Método que controla la petición GET de /buscar
	 * @param texto, título de la oferta introducido en el buscador, y que se desea buscar
	 * @return si hay algún error se notifica según corresponda y se redirige a la vista mostrarOfertas, en caso de que todo sea correcto,
	 * se muestra la lista de ofertas encontradas que coincidan con el texto introducido
	 */
	@RequestMapping(value ="/buscar", method = RequestMethod.GET, params = {"texto"})
	public ModelAndView buscar(@RequestParam("texto") String texto) {	
		ModelAndView modelAndView = this.obtenerInstancia();

		if(texto != null && texto != "") {
			
			Map<String, Object> modelo = modelAndView.getModel();
			BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
			Particular par = (Particular)mod.get("particular");
			
			Particular particular = null;
			if (par != null) {
				particular = saParticular.buscarPorEmail(par.getEmail());
			}
			
			if(particular != null) {//si se encuentra el particular
				String letraMayus = texto.substring(0, 1).toUpperCase();
				String nombreMayusPrim = letraMayus + texto.substring(1, texto.length());
					
				List<Oferta> listaOfertas = saOferta.buscarOfertasPorNombreYNombreMayus(texto, nombreMayusPrim);
				
				modelAndView = new ModelAndView();
				modelAndView.addObject("listaOfertasBuscadas", listaOfertas);
	
				modelAndView.setViewName("mostrarOfertas");
			}
			else {
				String msg = "¡Particular no encontrado!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");
			}
		}
		else {//si no ha introducido nada
			String msg = "Para buscar, introduzca caracteres";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}

		return modelAndView;
	}
	
	/**
	 * Método que captura la petición GET de /verOferta
	 * @param id, id de la oferta a la que se quiere acceder
	 * @return si hay algún error se notifica según corresponda y se redirige a la vista mostrarOfertas, en caso de que
	 * todo sea correcto, se permite acceder a los detalles de la oferta mediante la redirección a la vista /verOferta
	 */
	@RequestMapping(value ="/verOferta", method = RequestMethod.GET, params = {"id"})
    public ModelAndView mostrarOfertasEmpresa(@RequestParam("id") int id) {		
		
		ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		Particular par = (Particular)mod.get("particular");
		
		Empresa empresa = null;
		Particular particular = null;
		Oferta oferta = null;
		
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		else if(par != null) {
			particular = saParticular.buscarPorEmail(par.getEmail());
		}
		
		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(id);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");	
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
			}
		}
		else {
			if(particular != null) {//si es particular
				//Se deja acceder a cualquier oferta
				oferta = saOferta.buscarPorId(id);
				
				if(oferta == null) {
					String msg = "¡Oferta no encontrada!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
				}
			}
		}

		//Si no hay errores
		if(oferta != null) {
			modelAndView = new ModelAndView();
			TransferOferta tOferta = new TransferOferta(oferta.getNombre(),oferta.getJornadaLaboral(), oferta.getContrato(), oferta.getVacantes(), oferta.getSalario(), oferta.getCiudad(), oferta.getDescripcion(),
					oferta.getActivo(), oferta.getFinalizada(), oferta.getEmpresa(), oferta.getParticularesInscritos(), oferta.getTecnologias(), oferta.getAniosExperiencia());
			
			modelAndView.addObject("transferOferta", tOferta);
			modelAndView.setViewName("verOferta");
		}
		
		return modelAndView;
    }
	
	
	/**
	 * Método que captura la petición GET de crear-oferta
	 * 
	 * @return si el que ha hehco la petición no es una empresa, se redirige a la lista de ofertas,
	 * si todo es correcto, se redirige a la vista crearOferta
	 */
	@RequestMapping(value="/crear-oferta", method = RequestMethod.GET)
	public ModelAndView crearOferta(){
		ModelAndView modelAndView = this.obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = null;
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		
		if(empresa == null) {//no es empresa
			String msg = "¡Un particular no puede crear ofertas!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		else {//es empresa
			modelAndView.addObject("transferOferta", new TransferOferta());
			modelAndView.addObject("jornadaValues", JornadaLaboral.values());
			modelAndView.addObject("contratoValues", Contrato.values());
			modelAndView.setViewName("crearOferta");
		}
		return modelAndView;
	}
	/**
	 * Método que captura la petición POST de /crear-oferta
	 * @param transferOferta objeto que va a recoger los datos del formulario para guardarlo en la base de datos
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return si hay algún error no se crea la oferta, si todo es correcto, guarda la oferta en la base de datos y se redirige
	 * a la vista ofertas
	 */
	@RequestMapping(value="/crear-oferta", method = RequestMethod.POST)
	public ModelAndView crearOfertaPost(@ModelAttribute("transferOferta") @Valid TransferOferta transferOferta, BindingResult bindingResult){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.addObject("transferOferta", new TransferOferta());
		modelAndView.setViewName("crearOferta");
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = null;
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		/*Vacantes es null si no ha podido parsearse en el set de auxVacantes o si es un número
		negativo*/
		if(transferOferta.getVacantes() != null) {
			if(transferOferta.getVacantes() == 0) 
				bindingResult.rejectValue("auxVacantes", "error.transferOferta", "* Por favor, debe haber al menos una vacante");
			if(transferOferta.getVacantes() > 999)
				bindingResult.rejectValue("auxVacantes", "error.transferOferta", "* Por favor, sólo puede haber como máximo 999 vacantes");
		}		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("crearOferta", bindingResult.getModel());
			modelAndView.addObject("jornadaValues", JornadaLaboral.values());
			modelAndView.addObject("contratoValues", Contrato.values());
			modelAndView.addObject("transferOferta", transferOferta);
		}			
		else {
			transferOferta.setEmpresa(empresa);
			Oferta oferta = Oferta.TranferToEntity(transferOferta);
			oferta.setActivo(true);
			saOferta.save(oferta);
			String msg = "¡Oferta creada!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		
		return modelAndView;
	}
	
	/**
	 * Método que captura la petición GET de modificar-oferta
	 * @return si el que ha hecho la petición no es la empresa que creó la oferta o es un particular,
	 * se redirige a la lista de ofertas, si todo es correcto, se redirige a la vista modificarOferta
	 */
	@RequestMapping(value="/modificar-oferta", method = RequestMethod.GET, params = {"id"})
	public ModelAndView modificarOferta(@RequestParam("id") int id){
		
	
	    ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		Particular par = (Particular)mod.get("particular");
		
		Empresa empresa = null;
		Particular particular = null;
		Oferta oferta = null;
		
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		else if(par != null) {
			particular = saParticular.buscarPorEmail(par.getEmail());
		}
		
		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(id);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");	
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
				else if(!oferta.getActivo()) {
					String msg = "¡No puedes modificar una oferta desactivada!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
				else if(oferta.getFinalizada()) {
					String msg = "¡No puedes modificar una oferta finalizada!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
			}
		}
		else {
			if(particular != null) {//si es particular
					String msg = "¡No puedes modificar las ofertas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
			}
		}
		
		//Si no hay errores
		if(oferta != null) {
			modelAndView = new ModelAndView();
			TransferOferta tOferta = TransferOferta.EntityToTransfer(oferta);
			tOferta.setId(id); //Pasarle el id
			tOferta.setAuxSalario(tOferta.getSalario().toString()); //Convertir Salario a string (si no se hace no se puede mostrar en la vista)
			tOferta.setAuxVacantes(tOferta.getVacantes().toString()); //Convertir Vacantes a string (si no se hace no se puede mostrar en la vista)
			modelAndView.addObject("transferOferta", tOferta);
			modelAndView.addObject("jornadaValues", JornadaLaboral.values());
			modelAndView.addObject("contratoValues", Contrato.values());
			modelAndView.setViewName("modificarOferta");
		}
		
		return modelAndView;
	}
	
	/**
	 * Método que captura la petición POST de /modificar-oferta
	 * @param transferOferta objeto que va a recoger los datos del formulario para guardarlo en la base de datos
	 * @param bindingResult clase para controlar los errores producidos al introducir los datos
	 * @return si hay algún error no se modifica la oferta, si todo es correcto, guarda la oferta en la base de datos y se redirige
	 * a la vista ofertas
	 */
	@RequestMapping(value="/modificar-oferta", method = RequestMethod.POST)
	public ModelAndView modificarOfertaPost(@ModelAttribute("transferOferta") @Valid TransferOferta transferOferta, BindingResult bindingResult){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.addObject("transferOferta", new TransferOferta());
		modelAndView.setViewName("modificarOferta");

		/*Vacantes es null si no ha podido parsearse en el set de auxVacantes o si es un número
		negativo*/
		if(transferOferta.getVacantes() != null) {
			if(transferOferta.getVacantes() == 0) 
				bindingResult.rejectValue("auxVacantes", "error.transferOferta", "* Por favor, debe haber al menos una vacante");
			if(transferOferta.getVacantes() > 999)
				bindingResult.rejectValue("auxVacantes", "error.transferOferta", "* Por favor, sólo puede haber como máximo 999 vacantes");
		}		
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView("modificarOferta", bindingResult.getModel());
			modelAndView.addObject("jornadaValues", JornadaLaboral.values());
			modelAndView.addObject("contratoValues", Contrato.values());
			modelAndView.addObject("transferOferta", transferOferta);
		}			
		else {
			Oferta oferta = saOferta.buscarPorId(transferOferta.getId());
			
			if(oferta == null) {//si no se encuentra la oferta que se ha modificado
				String msg = "¡La oferta que has modificado ya no existe!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");
			}
			else {//ACTUALIZACIÓN
				oferta.setNombre(transferOferta.getNombre());
				oferta.setJornadaLaboral(transferOferta.getJornada());
				oferta.setContrato(transferOferta.getContrato());
				oferta.setVacantes(transferOferta.getVacantes());
				oferta.setSalario(transferOferta.getSalario());
				oferta.setCiudad(transferOferta.getCiudad());
				oferta.setDescripcion(transferOferta.getDescripcion());
				oferta.setTecnologias(transferOferta.getTecnologias());
				oferta.setAniosExperiencia(transferOferta.getAniosExperiencia());
				
				saOferta.save(oferta);
				saNotificacion.notificarParticularActualizarOferta(oferta);
				String msg = "¡Oferta actualizada!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");
			}
		}
		
		return modelAndView;
	}
	
	/**
	 * Método que captura la petición GET de eliminarOferta
	 * @param id id de la oferta
	 * @return vuelve a mostrarOfertas
	 */
	@RequestMapping(value="/eliminarOferta", method = RequestMethod.GET, params = {"id"})
	public ModelAndView eliminarOferta(@RequestParam("id") int id){
		ModelAndView modelAndView = this.obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		Particular par = (Particular)mod.get("particular");
		
		Empresa empresa = null;
		Particular particular = null;
		Oferta oferta = null;
		
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		else if(par != null) {
			particular = saParticular.buscarPorEmail(par.getEmail());
		}
		
		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(id);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");	
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				} //Si la oferta ya está eliminada
				else if(!oferta.getActivo()) {
					String msg = "¡No puedes eliminar una oferta ya desactivada!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
			}
		}
		else {
			if(particular != null) {//si es particular
					String msg = "¡No puedes eliminar las ofertas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
			}
		}
		
		//Si no hay errores
		if(oferta != null) {
			saOferta.eliminarOferta(id);
			if (!oferta.getFinalizada()) saNotificacion.notificarParticularOfertaEliminada(oferta);
			String msg = "¡Oferta eliminada!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}		
		
		return modelAndView;
	}
	
	/**
	 * Método que captura la peticion POST de /inscribir
	 * @param id_oferta id de la oferta a la que un particular se desea inscribir
	 * @return si hay algún error se notifica según corresponda y se redirige a mostrarOfertas
	 * si todo es correcto, se inscribe al particular en la oferta, se actualiza esta y se informa al usuario
	 * que el proceso de inscripción se ha llevado a cabo correctamente
	 */
	@RequestMapping(value="/inscribir", method = RequestMethod.POST)
	public ModelAndView inscribirseEnOferta(@ModelAttribute("id_oferta") @Valid String id_oferta){
		ModelAndView modelAndView = this.obtenerInstancia();
	
		int id = Integer.parseInt(id_oferta);
			
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Particular par = (Particular)mod.get("particular");
		
		Particular particular = null;
		if(par != null) {
			particular = saParticular.buscarPorEmail(par.getEmail());
		}
		
		if(particular != null) {//si se encuentra el particular
			//Busco la oferta
			Oferta oferta = saOferta.buscarPorId(id);
			
			if(oferta != null) {// si se encuentra la oferta
				
				if(oferta.getParticularesInscritos().contains(particular)) {//si ya está inscrito
					modelAndView.setViewName("mostrarOfertas");
					String msg = "¡Ya estás inscrito!";
					modelAndView.addObject("popup", msg);
				}
				else {
					//Inscribimos al particular
					oferta.inscribirParticular(particular);
					particular.anadirOferta(oferta);
					
					//Guardamos la oferta
					Oferta ofResModificar = saOferta.save(oferta);
					Particular p = saParticular.save(particular);
					saNotificacion.notificarEmpresaNuevaInscripcion(ofResModificar);
					if(ofResModificar != null && p != null) {//si se consiguen modificar
						modelAndView.setViewName("mostrarOfertas");
						String msg = "¡Te has inscrito en la oferta '"+oferta.getNombre()+"'"+'\n'
								+"de la empresa "+oferta.getEmpresa().getNombreEmpresa()+"!";
						modelAndView.addObject("popup", msg);
						
					}
					else {//si hay error en modificar
						modelAndView.setViewName("mostrarOfertas");
						String msg = "¡Error al modificar!";
						modelAndView.addObject("popup", msg);
					}
				}
			}
			else {//si no se encuentra la oferta
				modelAndView.setViewName("mostrarOfertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
		}
		else {//si no es particular
			modelAndView.setViewName("mostrarOfertas");
			String msg = "¡No puedes inscribirte en una oferta!";
			modelAndView.addObject("popup", msg);
		}

		
		return modelAndView;
	}
	
	
	/**
	 * Método que captura la petición GET de /candidatos
	 * @param id de la oferta en la que se quiere mostrar la lista de particulares inscritos
	 * @return si hay algún error se notifica según corresponda y se redirige a mostrarOfertas
	 * si todo es correcto, se muestra la lista de candidatos de dicha oferta, redirigiendo a mostrarCandidatos
	 */
	@RequestMapping(value ="/candidatos", method = RequestMethod.GET, params = {"id"})
    public ModelAndView mostrarCandidatos(@RequestParam("id") int id) {		
		

		ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto Empresa emp de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		
		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(id);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView.setViewName("mostrarOfertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView.setViewName("mostrarOfertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
			}
		}
		else {//si no es empresa
			modelAndView.setViewName("mostrarOfertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}

		//Si no hay errores
		if(oferta != null) {
			modelAndView = new ModelAndView();
			
			List<Particular> particulares = oferta.getParticularesSeleccionados();
			List<Particular> listasSeleccionados = new ArrayList<Particular>();
			
			for(int i = 0; i < particulares.size(); i++) {
				
				listasSeleccionados.add(particulares.get(i));
			}
			particulares= oferta.getParticularesInscritos();
		
			
			modelAndView.addObject("listaCandidatos", particulares);
			modelAndView.addObject("listaSeleccionados", listasSeleccionados);
			modelAndView.addObject("oferta", oferta);
			modelAndView.setViewName("mostrarCandidatos");
		}
		
		return modelAndView;
    }
	
	//localhost:8080/verCandidato?idOferta=1&idCandidato=1
	/**
	 * Método que captura la petición GET de /verCandidato
	 * @param idOferta id de la oferta en la que está el particular cuyo perfil se quiere ver
	 * @param idCandidato id del particular cuyo perfil se quiere ver
	 * 
	 * @return Si todas las comprobaciones son correctas muestra el perfil del particular, en caso contrario notifica el error
	 * correspondiente y redirige a mostrarOfertas
	 */
	@RequestMapping(value ="/verCandidato", method = RequestMethod.GET, params = {"idOferta", "idCandidato"})
    public ModelAndView verDetallesCandidato(@RequestParam("idOferta") int idOferta, @RequestParam("idCandidato") int idCandidato) {		
		
		ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}

		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(idOferta);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView.setViewName("mostrarOfertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView.setViewName("mostrarOfertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
				else {//Si la oferta es de la empresa de la sesion
					
					Particular candidato = saParticular.buscarPorId(idCandidato);
					
					if(candidato == null) {//Si no se encuentra al candidato
						modelAndView.setViewName("mostrarOfertas");
						String msg = "¡El candidato no existe!";
						modelAndView.addObject("popup", msg);
					}
					else {
						//Si se encuentra al candidato, comprobamos que el candidato
						//está en la lista de particulares de la oferta
						List<Particular> listaParticulares = oferta.getParticularesInscritos();
						
						if(listaParticulares.contains(candidato)) {//si se encuentra en la lista de ofertas
							modelAndView = new ModelAndView();
							modelAndView.addObject("transferParticular", TransferParticular.EntityToTransfer(candidato));
							modelAndView.addObject("puntuacionMedia", candidato.getPuntuacionMedia());
							modelAndView.addObject("numValoraciones", candidato.getPuntuaciones().size());
							modelAndView.setViewName("perfilParticular");
						}
						else {
							modelAndView.setViewName("mostrarOfertas");
							String msg = "¡El candidato introducido no pertenece a la oferta!";
							modelAndView.addObject("popup", msg);
						}
					}
				}
			}
		}
		else {//si no es empresa
			modelAndView.setViewName("mostrarOfertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}
		
		return modelAndView;
    }

	/**
	 * Método que captura la petición GET de finalizar-oferta
	 * @param id id de la oferta
	 * @return vuelve a mostrarOfertas
	 */
	@RequestMapping(value="/finalizar-oferta", method = RequestMethod.GET, params = {"id"})
	public ModelAndView finalizarOferta(@RequestParam("id") int id){
		
	    ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		Particular par = (Particular)mod.get("particular");
		
		Empresa empresa = null;
		Particular particular = null;
		Oferta oferta = null;
		
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		else if(par != null) {
			particular = saParticular.buscarPorEmail(par.getEmail());
		}
		
		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(id);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
				modelAndView.setViewName("mostrarOfertas");	
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
				else if(!oferta.getActivo()) {
					String msg = "¡No puedes finalizar una oferta desactivada!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
				else if(oferta.getFinalizada()) {
					String msg = "¡No puedes finalizar una oferta finalizada!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
				}
			}
		}
		else {
			if(particular != null) {//si es particular
					String msg = "¡No puedes finalizar las ofertas!";
					modelAndView.addObject("popup", msg);
					modelAndView.setViewName("mostrarOfertas");
					oferta = null;
			}
		}
		
		//Si no hay errores
		if(oferta != null) {
			oferta.setFinalizada(true);
			saOferta.save(oferta);
			saNotificacion.notificarParticularesOfertaFinalizada(oferta);			
			String msg = "¡Oferta finalizada!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("mostrarOfertas");
		}
		
		return modelAndView;
	}
	
	/**
	 * Método que captura la petición GET de Seleccionar-Candidato
	 * @param idOferta id de la oferta
	 * @param idCandidato id del candidato
	 * @return si las comprobaciones son correctas, va a mostrarCandidatos. En caso contrario, vuelve a mostrarOfertas
	 */
	@RequestMapping(value ="/Seleccionar-Candidato", method = RequestMethod.GET, params = {"idOferta", "idCandidato"})
    public ModelAndView guardarContratados(@RequestParam("idOferta") int idOferta, @RequestParam("idCandidato") int idCandidato) {
		
		ModelAndView modelAndView = this.obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}

		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(idOferta);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView.setViewName("mostrarOfertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView.setViewName("mostrarOfertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
				else {//Si la oferta es de la empresa de la sesion
					
					Particular candidato = saParticular.buscarPorId(idCandidato);
					
					if(candidato == null) {//Si no se encuentra al candidato
						modelAndView.setViewName("mostrarOfertas");
						String msg = "¡El candidato no existe!";
						modelAndView.addObject("popup", msg);
					}
					else {
						
						oferta.getParticularesSeleccionados().add(candidato);
						candidato.getOfertasSeleccionados().add(oferta);
						saOferta.save(oferta);
						saParticular.save(candidato);
						
						//Si se encuentra al candidato, comprobamos que el candidato
						//está en la lista de particulares de la oferta
						List<Particular> listaParticulares = oferta.getParticularesInscritos();
						
						if(listaParticulares.contains(candidato)) {//si se encuentra en la lista de ofertas
							List<Particular> particulares =  oferta.getParticularesInscritos();
							List<Particular> listasSeleccionados = oferta.getParticularesSeleccionados();
							
							modelAndView.addObject("listaCandidatos", particulares);
							modelAndView.addObject("listaSeleccionados", listasSeleccionados);
							modelAndView.addObject("oferta", oferta);
							modelAndView.setViewName("mostrarCandidatos");
						}
						else {
							modelAndView.setViewName("mostrarOfertas");
							String msg = "¡El candidato introducido no pertenece a la oferta!";
							modelAndView.addObject("popup", msg);
						}
					}
				}
			}
		}
		else {//si no es empresa
			modelAndView.setViewName("mostrarOfertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}
		
		
		return modelAndView;		
	}
	
	@RequestMapping(value ="/Puntuar-Contratado", method = RequestMethod.GET, params = {"idOferta", "idCandidato"})
    public ModelAndView puntuarContratado(@RequestParam("idOferta") int idOferta, @RequestParam("idCandidato") int idCandidato) {
		
		ModelAndView modelAndView = this.obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}

		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(idOferta);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView.setViewName("mostrarOfertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView.setViewName("mostrarOfertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
				else {//Si la oferta es de la empresa de la sesion
					
					Particular contratado = saParticular.buscarPorId(idCandidato);
					
					if(contratado == null) {//Si no se encuentra al candidato
						modelAndView.setViewName("mostrarOfertas");
						String msg = "¡El candidato no existe!";
						modelAndView.addObject("popup", msg);
					}
					else {//el candidato existe en la bd
						
						if(!oferta.getFinalizada()) {//si la oferta NO esta finalizada
							modelAndView.setViewName("mostrarOfertas");
							String msg = "¡La oferta no esta finalizada!";
							modelAndView.addObject("popup", msg);
						}
						else {//si la oferta está finalizada
							
							if(!oferta.getParticularesInscritos().contains(contratado)) {//si el candidato no esta inscrito
								modelAndView.setViewName("mostrarOfertas");
								String msg = "¡El candidato introducido no pertenece a la oferta!";
								modelAndView.addObject("popup", msg);
							}
							else {//inscrito
								if(!oferta.getParticularesSeleccionados().contains(contratado)) {//Si no contiene al candidato contratado
									modelAndView.setViewName("mostrarOfertas");
									String msg = "¡El candidato no ha sido contratado todavía!";
									modelAndView.addObject("popup", msg);
								}
								else {//contratado
									
									if(contratado.estaParticularValorado(empresa)) {//la empresa ya habia valorado al particular
										modelAndView.setViewName("mostrarOfertas");
										String msg = "¡Ya ha valorado a "+contratado.getNombre()+" "+contratado.getApellidos()+"!";
										modelAndView.addObject("popup", msg);
									}
									else {//si la empresa nunca habia valorado al particular
										modelAndView.addObject("transferParticular", TransferParticular.EntityToTransfer(contratado));
										modelAndView.addObject("oferta", oferta);
										modelAndView.addObject("idParticular", (Integer)contratado.getId());
										modelAndView.addObject("puntuacionMedia", contratado.getPuntuacionMedia());
										modelAndView.addObject("numValoraciones", contratado.getPuntuaciones().size());
										modelAndView.setViewName("perfilParticular");
									}
								}
							}
						}
					}
				}
			}
		}
		else {//si no es empresa
			modelAndView.setViewName("mostrarOfertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}
		
		
		return modelAndView;		
	}
	
	@RequestMapping(value="/Puntuar-Contratado", method = RequestMethod.POST)
	public ModelAndView puntuarContratadoPost(@ModelAttribute String aux, String id_oferta, String id_contratado, String puntuacion){
		ModelAndView modelAndView = this.obtenerInstancia();
	
		int idOferta = Integer.parseInt(id_oferta);
		int idContratado = Integer.parseInt(id_contratado);
		double valoracion = 0;
		if(puntuacion != null)
			valoracion = Double.parseDouble(puntuacion);

		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}

		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(idOferta);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView.setViewName("mostrarOfertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView.setViewName("mostrarOfertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
				else {//Si la oferta es de la empresa de la sesion
					
					Particular contratado = saParticular.buscarPorId(idContratado);
					
					if(contratado == null) {//Si no se encuentra al candidato
						modelAndView.setViewName("mostrarOfertas");
						String msg = "¡El candidato no existe!";
						modelAndView.addObject("popup", msg);
					}
					else {//el candidato existe en la bd
						if(!oferta.getParticularesInscritos().contains(contratado)) {//si el candidato no esta inscrito
							modelAndView.setViewName("mostrarOfertas");
							String msg = "¡El candidato introducido no pertenece a la oferta!";
							modelAndView.addObject("popup", msg);
						}
						else {//inscrito
							if(!oferta.getParticularesSeleccionados().contains(contratado)) {//Si no contiene al candidato contratado
								modelAndView.setViewName("mostrarOfertas");
								String msg = "¡El candidato no ha sido contratado todavía!";
								modelAndView.addObject("popup", msg);
							}
							else {//contratado
								
								if(!saParticular.addValoracion(empresa, contratado, valoracion)) {//la empresa ya habia valorado al particular
									modelAndView.setViewName("mostrarOfertas");
									String msg = "¡Ya ha valorado a "+contratado.getNombre()+" "+contratado.getApellidos()+"!";
									modelAndView.addObject("popup", msg);
								}
								else {//si la empresa nunca habia valorado al particular
									modelAndView.addObject("transferParticular", TransferParticular.EntityToTransfer(contratado));
									modelAndView.addObject("oferta", oferta);
									//modelAndView.addObject("idParticular", null);
									int numPuntuaciones = contratado.getPuntuaciones().size();
									modelAndView.addObject("numValoraciones", numPuntuaciones);
									modelAndView.addObject("puntuacionMedia", contratado.getPuntuacionMedia());
									modelAndView.setViewName("perfilParticular");
								}
							}
						}
					}
				}
			}
		}
		else {//si no es empresa
			modelAndView.setViewName("mostrarOfertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}

		return modelAndView;
	}
	
	// ------------- AVISOS ------------- //
	
	@RequestMapping(value="/avisos", method = RequestMethod.GET, params = {"id", "tipo"})
	public ModelAndView aviso(@RequestParam("id") int id, @RequestParam("tipo") String tipo){
		
	    ModelAndView modelAndView = this.obtenerInstancia();
	    if (tipo.equals("eliminar")) {
			modelAndView.setViewName("avisos");
			String msg = "¿Está seguro de que quiere eliminar la oferta? Esta acción no se podrá deshacer posteriormente";
			modelAndView.addObject("avisoEliminar", msg);
			modelAndView.addObject("id", id);
	    }
	    else if (tipo.equals("finalizar")) {
			modelAndView.setViewName("avisos");
			String msg = "¿Está seguro de que quiere finalizar la oferta? No podrá seleccionar más candidatos para esta oferta";
			modelAndView.addObject("avisoFinalizar", msg);
			modelAndView.addObject("id", id);
	    }

		return modelAndView;
	}
	
	//-------------Notificaciones-----------------//
	@RequestMapping(value="/notificacionLeida", method = RequestMethod.GET, params = {"id"})
	public ModelAndView notificacionLeida(@RequestParam("id") int id){
		
	    ModelAndView modelAndView = this.obtenerInstancia();
	    
	    Notificacion not = saNotificacion.buscarPorId(id);
	    
	    not.setLeida(true);
	    
	    saNotificacion.crearNotificacion(not);
	    
	    modelAndView.setViewName("redirect:/ofertas");

		return modelAndView;
	}
	
	
	// -------------Recuperacion Contraseña ------------- //
	
	/**
	 * Método que capturando la petición POST de /recuperar-contrasenia permite enviar los datos para recuperar la contraseña
	 * @param emailRecupera
	 * @return muestra un mensaje para redirigir a la página principal si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value="/recuperar-contrasenia", method = RequestMethod.POST)
	public ModelAndView recuperaContrasnia(@Valid String emailRecupera){
		
	    ModelAndView modelAndView = this.obtenerInstancia();
	    
	    Empresa miEmpresa =saEmpresa.buscarPorEmail(emailRecupera);
		Particular miParticular = saParticular.buscarPorEmail(emailRecupera);

	    if(miEmpresa!=null || miParticular!=null){
			saEmail.recuerdaPass("Siga el siguiente enlace para recuperar la contraseña de Conecta2 ", "Recuperacióon de contraseña", emailRecupera);
		    modelAndView.setViewName("redirect:/");

	    }else {
			//MENSAJE DE ERROR
			String msg = "¡El correo introducido no está registrado !";
			
			modelAndView.addObject("popup", msg);
		    modelAndView.setViewName("index");

		}
	   
		return modelAndView;
	}
		
	/**
	 * Método que capturando la petición GET de /nuevaPass da la posibilidad de cambiar la contraseña
	 * @param val url permite al usuario cambiar la contraseña
	 * @return redirige a la página principal si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value="/nuevaPass", method = RequestMethod.GET, params = {"val"})
	public ModelAndView nuevaContrasenia(@RequestParam("val") String val){ 

		ModelAndView modelAndView = obtenerInstancia();	
		Object obj = saEmail.resetPass(val);
		if(obj==null) {//error validacion
			modelAndView = new ModelAndView("index");
			String msg = "¡Código de recuperacion incorrecto, o caducado!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("index");			

		}
		else{
			//Enviar correo a la vista de reestablecer contraseña
			TransferParticular aux = new TransferParticular();
			aux.setEmail((String) obj);
			modelAndView.addObject("transferParticular", aux);
			modelAndView.addObject("codigo",val);
			modelAndView.setViewName("restaurarPass");			
		}	
		return modelAndView;
	}
		
		
	/**
	 * Método que capturando la petición GET de /nuevaPass da la posibilidad de cambiar la contraseña
	 * @param val url permite al usuario cambiar la contraseña
	 * @return redirige a la página principal si no ha habido fallos, en caso contrario notifica sin cambiar de pagina
	 */
	@RequestMapping(value="/cambiaContraseniaa", method = RequestMethod.POST)
	public ModelAndView cambiaContraseniaa(@ModelAttribute ("transferParticular") @Valid TransferParticular aux, BindingResult bindingResult, String val,String email ){ 

		ModelAndView modelAndView = this.obtenerInstancia();
	    Empresa miEmpresa =saEmpresa.buscarPorEmail(email);
		Particular miParticular = saParticular.buscarPorEmail(email);

	    if(miEmpresa!=null || miParticular!=null){
	    	//EL CORREO HA SIDO ENCONTRADO Y LA PASS ES LA MISMA
	    	if (!aux.getPassword().equals(aux.getPasswordConfirmacion())) {
				bindingResult.rejectValue("password", "error.transferParticular", "* Las contraseñas no coinciden");

				
			}
	    	if (bindingResult.hasErrors()) {
	    		List<ObjectError> array = bindingResult.getAllErrors();
	    		boolean encontrado=false;
	    		
	    		for (ObjectError error : array){
	    			if(error.getCode().compareTo("Pattern")==0 ||error.getCode().compareTo("error.transferParticular")==0)
	    				encontrado=true;
	    				
	    		}
	    		if(encontrado) {
	    			modelAndView = new ModelAndView("restaurarPass", bindingResult.getModel());
					modelAndView.addObject("transferParticular", aux);
					modelAndView.addObject("codigo", val);
				}else{
					String valido = saEmail.resetPass(val);
					if(valido.compareTo(email)==0) {
						//LA PETICION NO HA CADUCADO Y EL CORREO ES EL MISMO
						if(miEmpresa!=null) {
							miEmpresa.setPassword(aux.getPassword());
							saEmpresa.cifraPass(miEmpresa);
							String msg = "¡Su contraseña se ha cambiado!";
			    			modelAndView.addObject("popup", msg);
							modelAndView.setViewName("index");			

							
						} else {
							miParticular.setPassword(aux.getPassword());
							saParticular.cifraPass(miParticular);
							String msg = "¡Su contraseña se ha cambiado!";
			    			modelAndView.addObject("popup", msg);
							modelAndView.setViewName("index");			

						}
						
					} else{
						String msg = "¡La peticion de cambio de contraseña ha caducado!";
		    			modelAndView.addObject("popup", msg);
						modelAndView.setViewName("index");			

					}
				}

	    	}				    	
	    }else {
	    	//EMAIL NO ENCONTRADO
	    	//MENSAJE DE ERROR
			String msg = "¡El correo no ha sido encontrado!";
			modelAndView.addObject("popup", msg);
			modelAndView.setViewName("index");			
	    }
			
		return modelAndView;
	}
		
	

}
