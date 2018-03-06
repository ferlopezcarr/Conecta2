package conecta2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Anotación principal de Spring Boot, relativa a la clase que contiene el main()
//En su ejecución se hace una invocación a la clase con la anotación @Controller
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
