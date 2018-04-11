package conecta2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuración del Modelo Vista Controlador, que será usado en
 * SecurityConfiguration
 * 
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * Método utilizado para establecer la codificación de las contraseñas de usuario
	 * insertadas en la aplicación
	 * @return objeto encargado de la codificación de la contraseña
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
}