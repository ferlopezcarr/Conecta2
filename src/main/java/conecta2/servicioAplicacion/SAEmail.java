package conecta2.servicioAplicacion;

/**
 * Interfaz que define los casos de uso
 * Interfaz que define los métodos o funciones que se pueden realizar con el email
 */
public interface SAEmail{

		public void enviarCorreo(String texto,String subject, String destino);
		
		public Object validaUsuario(String urlValida);
		
}
