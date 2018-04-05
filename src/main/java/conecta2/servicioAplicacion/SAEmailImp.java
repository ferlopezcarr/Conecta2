package conecta2.servicioAplicacion;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;

import conecta2.dao.DAOActivacion;
import conecta2.modelo.Activacion;
import conecta2.modelo.Empresa;
import conecta2.modelo.Particular;
import conecta2.transfer.TransferEmpresa;
import conecta2.transfer.TransferParticular;

import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa las funciones de la interfaz SAEmail
 * @author ferlo
 * Clase que se desarrolla la funcionalidad de la entidad Activacion
 */
@Service //("saEmail")
public class SAEmailImp  implements SAEmail {


	/**
	 * DAO que proporciona el acceso a la base de datos
	 */
	@Autowired
	private DAOActivacion miDao;
	
	/**
	 * SA del particular utilizado para comprobar que existe
	 */
	@Autowired	
	private SAParticular saParticular;
	
	/**
	 * SA de la empresa utilizado para comprobar que existe
	 */
	@Autowired
	private SAEmpresa saEmpresa;
	
	/**
	 * Método que envía un correo a un 'destino', con asunto 'subject' y un 'texto'
	 */
    @Transactional
	@Override
	public void enviarCorreo(String texto,String subject, String destino ){

		//Elementos del correo
		final String origen ="conecta2authentication@gmail.com"; 
		final String pass = "margin0Auto";
		
		//String direccion = " https://coneta2.herokuapp.com/authorization?val=";
		
		String direccion ="localhost:8080/authorization?val=";
		String direccionRandom = RandomStringUtils.random(64, true,true); 
		direccion=direccion+direccionRandom;
		Properties props= new Properties();
		
		//Que el sistema va autorizado
		props.setProperty("mail.stmp.auth", "true");
		//Que vamos a utilizar tls pra que sea seguro
		props.setProperty("mail.smtp.starttls.enable", "true");
		//servidor smtp de gmail
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		//el puerto std de correo electronico
		props.setProperty("mail.smtp.port", "587");
				
		//Creacion de la sesion en el servidor de google
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
			mensaje.setText(texto+direccion);
			mensaje.setSubject(subject);
			
			Transport transport = sesion.getTransport("smtp");
	        transport.connect("smtp.gmail.com", origen, pass);
	        transport.sendMessage(mensaje, mensaje.getAllRecipients());
	        transport.close();
	    

		}catch(MessagingException e){
				System.out.println(e);
	
		}
		
		Activacion miAct = new Activacion();
		
	    miAct.setActivacion(direccionRandom);
        miAct.setEmail(destino);

	    	miDao.save(miAct);

	}
    
    /**
     * Método que valida a un usuario mediante una url
     */
	@Override
	public Object validaUsuario(String urlValida) {
		
		Activacion aux = miDao.findByActivacion(urlValida);
		
		if(aux!=null) {
			
			Empresa empresa = saEmpresa.buscarPorEmail(aux.getEmail());
			
			if(empresa!=null){//Se comprueba que es una empresa
				empresa.setActivo(true);
				TransferEmpresa transEmpresa = new TransferEmpresa();
				transEmpresa.setCif(empresa.getCif());
				transEmpresa.setEmail(empresa.getEmail());
				transEmpresa.setNombreEmpresa(empresa.getNombreEmpresa());
				transEmpresa.setPassword(empresa.getPassword());
				transEmpresa.setActivo(empresa.getActivo());
				saEmpresa.save(transEmpresa);
				
				return transEmpresa;
				
			} else {//No se encuentra la empresa
				
				Particular particular = saParticular.buscarPorEmail(aux.getEmail()); 
				
				if(particular!=null) {//Se comprueba que es particular
					particular.setActivo(true);
			
					TransferParticular transParticular = new TransferParticular();
					transParticular.setNombre(particular.getNombre());
					transParticular.setApellidos(particular.getApellidos());
					transParticular.setDni(particular.getDni());
					transParticular.setEmail(particular.getEmail());
					transParticular.setPassword(particular.getPassword());
					transParticular.setActivo(particular.getActivo());
					
					saParticular.save(transParticular);
					
					return transParticular;
					
				} else {//No se encuentra el particular
					return null;
				}
			}
		} else {//aux == null
			return null;
		}
	}//validaUsuario


}
