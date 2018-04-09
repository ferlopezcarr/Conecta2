package conecta2.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
			transferEmpresa.setActivo(true);
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
			modelAndView = new ModelAndView("redirect:/login");
			String msg = "¡Código de confirmación incorrecto!";
			modelAndView.addObject("popup", msg);
		}
		else{
			modelAndView.setViewName("redirect:/login");
			String msg = "¡Se ha verificado la cuenta!";
			modelAndView.addObject("popup", msg);
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
				empresa.getEmail(), "0", "0", empresa.getDescripcion(), empresa.getActivo());
		
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
				empresa.getTelefono(), empresa.getEmail(), "0", "0", empresa.getDescripcion(), empresa.getActivo());
		
		modelAndView.addObject("transferEmpresa", transferEmpresa);
		modelAndView.setViewName("modificarEmpresa");
		
		return modelAndView;
    }
	
	@RequestMapping(value = "/empresa/modificar", method = RequestMethod.POST)
	public ModelAndView modificarPerfilEmpresa(@ModelAttribute("transferEmpresa") TransferEmpresa transferEmpresa,BindingResult bindingResult) {
		ModelAndView modelAndView = null;
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
			saEmpresa.save(transferEmpresa);
			modelAndView = new ModelAndView("redirect:/empresa/perfil");

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
				particular.getTelefono(), particular.getEmail(), "0", particular.getActivo(), particular.getPuntuacion(),particular.getDescripcion());
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
				particular.getTelefono(), particular.getEmail(), "0", particular.getActivo(), particular.getPuntuacion(),particular.getDescripcion());
		
		modelAndView.addObject("transferParticular", tParticular);
		modelAndView.setViewName("modificarParticular"); //Cambiar a la vista de modificar particular
		
		return modelAndView;
    }
	
	@RequestMapping(value ="/particular/modificar", method = RequestMethod.POST)
    public ModelAndView modificarPerfilParticular(@ModelAttribute("transferParticular") TransferParticular transferParticular, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		if (!transferParticular.getNombre().matches("^[a-zA-ZáéíóúñÁÉÍÓÚÑ ]{1,}$")) {		
			bindingResult.rejectValue("nombre", "error.transferParticular", "* Introduzca únicamente letras");
		}
		if (!transferParticular.getApellidos().matches("^[a-zA-ZáéíóúñÁÉÍÓÚÑ ]{1,}$")) {		
			bindingResult.rejectValue("apellidos", "error.transferParticular", "* Introduzca únicamente letras");
		}
		if(!transferParticular.getTelefono().matches("^([0-9]{9})$")) {
			bindingResult.rejectValue("telefono", "error.transferParticular", "* Introduzca un teléfono válido");
		}
		if (bindingResult.hasErrors()) {		
			modelAndView = new ModelAndView("modificarParticular", bindingResult.getModel());
			modelAndView.addObject("transferParticular", transferParticular);
		}	
		else {
			saParticular.save(transferParticular);
			modelAndView = new ModelAndView("redirect:/particular/perfil");
		}		
		return modelAndView;
    }
	
	
	
	
	
	// ------------- OFERTAS ------------- //
	
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
		}
		else if(par != null) {
			modelAndView.addObject("listaOfertas", saOferta.buscarOfertasParticularInscrito(par));
			String text = "";
			modelAndView.addObject("text", text);
		}
		
		modelAndView.setViewName("mostrarOfertas");
		
		return modelAndView;
    }
	
	@RequestMapping(value ="/buscar", method = RequestMethod.POST)
	public ModelAndView buscar(@ModelAttribute("texto") String texto) {	
		ModelAndView modelAndView = this.obtenerInstancia();
		
		String nombreMayusPrim = texto.substring(1).toUpperCase() + texto.substring(2, texto.length());
			
		List<Oferta> listaOfertas = saOferta.buscarOfertasPorNombreYNombreMayus(texto, nombreMayusPrim);
		
		modelAndView = new ModelAndView();
		modelAndView.addObject("listaOfertasBuscadas", listaOfertas);
		
		//de momento renderizamos en mostrarOfertas
		modelAndView.setViewName("mostrarOfertas");

		
		return modelAndView;
	}
	
	
	@RequestMapping(value ="/verOferta", method = RequestMethod.GET, params = {"id"})
    public ModelAndView mostrarOfertaEmpresa(@RequestParam("id") int id) {		
		
		ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		Particular par = (Particular)mod.get("particular");
		
		Empresa empresa = null;
		
		if (emp != null) {
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		} 
				
		Oferta oferta = null;
		
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
			Particular particular = saParticular.buscarPorEmail(par.getEmail());
			
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
					oferta.getActivo(), oferta.getFinalizada(), oferta.getEmpresa(), oferta.getParticulares());
			
			modelAndView.addObject("transferOferta", tOferta);
			modelAndView.setViewName("verOferta");
		}
		
		return modelAndView;
    }
	
	@RequestMapping(value="/crear-oferta", method = RequestMethod.GET)
	public ModelAndView crearOferta(){
		ModelAndView modelAndView = this.obtenerInstancia();
		
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		
		if(empresa == null) {//no es empresa
			modelAndView = new ModelAndView("redirect:/ofertas");
		}
		else {//es empresa
			modelAndView.addObject("transferOferta", new TransferOferta());
			modelAndView.addObject("jornadaValues", JornadaLaboral.values());
			modelAndView.addObject("contratoValues", Contrato.values());
			modelAndView.setViewName("crearOferta");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/crear-oferta", method = RequestMethod.POST)
	public ModelAndView crearOfertaPost(@ModelAttribute("transferOferta") @Valid TransferOferta transferOferta, BindingResult bindingResult){
		ModelAndView modelAndView = this.obtenerInstancia();
		modelAndView.addObject("transferOferta", new TransferOferta());
		modelAndView.setViewName("crearOferta");
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		Empresa empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		
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
			transferOferta.setActivo(true);
			transferOferta.setFinalizada(false);
			saOferta.save(transferOferta);
			modelAndView = new ModelAndView("redirect:/ofertas");
		}
		
		//modelAndView.addObject("roles", Rol.values());
		
		return modelAndView;
	}
	
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
				
				//Inscribimos al particular
				oferta.inscribirParticular(particular);
				particular.anadirOferta(oferta);
				//Guardamos la oferta
				Oferta ofResModificar = saOferta.actualizarOferta(oferta);
				Particular p = saParticular.actualizarParticular(particular);
				if(ofResModificar != null) {//si se consigue modificar
					modelAndView = new ModelAndView("redirect:/ofertas");
					/*
					String msg = "¡Te has inscrito en la oferta de "+oferta.getNombre()+'\n'
							+"de la empresa "+oferta.getEmpresa().getNombreEmpresa()+"!";
					modelAndView.addObject("popup", msg);
					*/
				}
				else {//si hay error en modificar
					modelAndView = new ModelAndView("redirect:/ofertas");
					String msg = "¡Error al modificar!";
					modelAndView.addObject("popup", msg);
				}
			}
			else {//si no se encuentra la oferta
				modelAndView = new ModelAndView("redirect:/ofertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
		}
		else {//si no es particular
			modelAndView = new ModelAndView("redirect:/ofertas");
			String msg = "¡No puedes inscribirte en una oferta!";
			modelAndView.addObject("popup", msg);
		}

		
		return modelAndView;
	}
	
	@RequestMapping(value ="/candidatos", method = RequestMethod.GET, params = {"id"})
    public ModelAndView mostrarCandidatos(@RequestParam("id") int id) {		
		
		ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		modelAndView = null;
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto Empresa emp de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}
		
		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(id);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView = new ModelAndView("redirect:/ofertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView = new ModelAndView("redirect:/ofertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
			}
		}
		else {//si no es empresa
			modelAndView = new ModelAndView("redirect:/ofertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}

		//Si no hay errores
		if(oferta != null) {
			modelAndView = new ModelAndView();
			modelAndView.addObject("listaCandidatos", oferta.getParticulares());
			modelAndView.setViewName("mostrarCandidatos");
		}
		
		return modelAndView;
    }
	
	//localhost:8080/verCandidato?idOferta=1&idCandidato=1
	@RequestMapping(value ="/verCandidato", method = RequestMethod.GET, params = {"idOferta", "idCandidato"})
    public ModelAndView verDetallesCandidato(@RequestParam("idOferta") int idOferta, @RequestParam("idCandidato") int idCandidato) {		
		
		ModelAndView modelAndView = this.obtenerInstancia();
	
		Map<String, Object> modelo = modelAndView.getModel();
		BindingAwareModelMap mod = (BindingAwareModelMap) modelo.get("modelo");
		Empresa emp = (Empresa)mod.get("empresa");
		
		modelAndView = null;
		Oferta oferta = null;
		Empresa empresa = null;
		
		if(emp != null) {//si el objeto de la vista no esta vacio
			empresa = saEmpresa.buscarPorEmail(emp.getEmail());
		}

		if(empresa != null) {//si es empresa
			oferta = saOferta.buscarPorId(idOferta);
			
			//Si no se encuentra la oferta
			if(oferta == null) {
				modelAndView = new ModelAndView("redirect:/ofertas");
				String msg = "¡Oferta no encontrada!";
				modelAndView.addObject("popup", msg);
			}
			else {
				//Si la oferta no es de la empresa de la sesion
				if(!oferta.getEmpresa().equals(empresa)) {
					modelAndView = new ModelAndView("redirect:/ofertas");
					String msg = "¡No puedes acceder a las ofertas de otras empresas!";
					modelAndView.addObject("popup", msg);
					
					oferta = null;
				}
				else {//Si la oferta es de la empresa de la sesion
					
					Particular candidato = saParticular.buscarPorId(idCandidato);
					
					if(candidato == null) {//Si no se encuentra al candidato
						modelAndView = new ModelAndView("redirect:/ofertas");
						String msg = "¡El candidato no existe!";
						modelAndView.addObject("popup", msg);
					}
					else {
						//Si se encuentra al candidato, comprobamos que el candidato
						//está en la lista de particulares de la oferta
						List<Particular> listaParticulares = oferta.getParticulares();
						
						if(listaParticulares.contains(candidato)) {//si se encuentra en la lista de ofertas
							
							TransferParticular transferParticular = new TransferParticular(
									candidato.getNombre(),
									candidato.getApellidos(),
									candidato.getDni(),
									candidato.getTelefono(),
									candidato.getEmail(),
									candidato.getPassword(),
									candidato.getActivo(),
									candidato.getPuntuacion(),
									candidato.getDescripcion()
								);
							
							modelAndView = new ModelAndView();
							modelAndView.addObject("transferParticular", transferParticular);
							modelAndView.setViewName("perfilParticular");
						}
						else {
							modelAndView = new ModelAndView("redirect:/ofertas");
							String msg = "¡El candidato introducido no pertenece a la oferta!";
							modelAndView.addObject("popup", msg);
						}
					}
				}
			}
		}
		else {//si no es empresa
			modelAndView = new ModelAndView("redirect:/ofertas");
			String msg = "¡No puedes ver a los candidatos de la oferta!";
			modelAndView.addObject("popup", msg);
		}
		
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
