package conecta2.servicioAplicacion;

import org.springframework.stereotype.Service;

/**
 * Interfaz que define los casos de uso
 * @author ferlo
 * Interfaz que define los métodos o funciones que se pueden realizar con el email
 */
@Service
public interface SAEmail{

		public void enviarCorreo(String texto,String subject, String destino);
		
		public Object validaUsuario(String urlValida);
}
