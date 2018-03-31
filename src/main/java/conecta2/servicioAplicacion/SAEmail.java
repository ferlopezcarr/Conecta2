package conecta2.servicioAplicacion;

import org.springframework.stereotype.Service;

@Service
public interface SAEmail{

		public void enviarCorreo(String texto,String subject, String destino);
		
		public Object validaUsuario(String urlValida);
}
