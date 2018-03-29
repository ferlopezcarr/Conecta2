package conecta2.servicioAplicacion;


public interface SAEmail{

		public void enviarCorreo(String texto,String subject, String destino);
		
		public Object validaUsuario(String urlValida);
}
