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

import conecta2.DTO.DTOUsuario;
import conecta2.modelo.Usuario;
import conecta2.servicioAplicacion.SARol;
import conecta2.servicioAplicacion.SAUsuario;

@Controller
public class ControladorPrincipal {
	
	@Autowired	
	private SAUsuario saUsuario;
	@Autowired
	private SARol servicioRol;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value="/registro", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		DTOUsuario dtoUsuario = new DTOUsuario();
		modelAndView.addObject("dtoUsuario", dtoUsuario);		
		modelAndView.addObject("roles", servicioRol.findAll());
		modelAndView.addObject("usuarioRegistrado", false);
		modelAndView.setViewName("registro");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute("dtoUsuario") DTOUsuario dtoUsuario, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		Usuario userExists = saUsuario.findUserByEmail(dtoUsuario.getEmail());

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
			modelAndView.addObject("dtoUsuario", new DTOUsuario());
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
    public String mostrarInformacion() {
        return "informacion";
    }
	
	@ModelAttribute
	public void addAttributes(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = saUsuario.findUserByEmail(auth.getName());
		model.addAttribute("usuario", usuario);
	}
}
