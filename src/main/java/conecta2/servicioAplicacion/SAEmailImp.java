package conecta2.servicioAplicacion;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.RandomStringUtils;
import javax.mail.PasswordAuthentication;
import java.util.Properties;




public class SAEmailImp implements SAEmail {

	@Override
	public boolean enviarCorreo(String texto,String subject, String destino) {
		// TODO Auto-generated method stub
		
		//Elementos del correo
		
		final String origen ="conecta2authentication@gmail.com"; 
		final String pass = "margin0Auto";
		
		//String direccion = " https://coneta2.herokuapp.com/authorization?val=";
		
		String direccion ="localhost:8080/authorization?val=";
		String direccionRandom = RandomStringUtils.random(112, true,true); 
		direccion=direccion+direccionRandom;
		Properties props= new Properties();
		
		//Que el sistema va autorizado
		props.setProperty("mail.stmp.auth", "true");
		//Que vamos a utilizar tls ( a ver estudiao redes)
		props.setProperty("mail.smtp.starttls.enable", "true");
		//servidor smtp de gmail
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		//el puerto std de correo electronico
		props.setProperty("mail.smtp.port", "587");
				
		//Creacion de la sesion en el servidor de google
		//Session sesion = Session.getDefaultInstance(props);
		Session sesion = Session.getInstance(props , new javax.mail.Authenticator() {
			//Este metodo sobreEscribe el metodo de la clase principal con los datos de nuestro servidor
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication("Conecta2", pass);
			}
		});
		
		try {
			Message mensaje = new MimeMessage(sesion);	
			mensaje.setFrom(new InternetAddress(origen));
			
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
			mensaje.setText(" Para continuar con el proceso de registro en Conecta2 acceda a la siguiente direccion" +direccion);
			mensaje.setSubject(subject);
			
			Transport transport = sesion.getTransport("smtp");
	        transport.connect("smtp.gmail.com", origen, pass);
	        transport.sendMessage(mensaje, mensaje.getAllRecipients());
	        transport.close();
	         
			System.out.println("Sent message successfully....");

		}catch(MessagingException e){
				System.out.println(e);
				return false;
		}
		return true;
	}

	
}
