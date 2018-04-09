package conecta2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Anotación principal de Spring Boot, relativa a la clase que contiene el main()
//En su ejecución se hace una invocación a la clase con la anotación @Controller
@SpringBootApplication
public class C2Aplicacion {

	public static void main(String[] args) {
		SpringApplication.run(C2Aplicacion.class, args);
	}
}
