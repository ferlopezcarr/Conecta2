package conecta2.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuaración de Spring Security, donde se definen todos los aspectos
 * de seguridad de la aplicación web. *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	/**
	 * Método encargado de la configuración básica de usuarios, roles, contraseñas, base de datos, etc.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	
	/**
	 * Método usado para definir las urls válidas, las direcciones de inicio de sesion, cerrar sesión, páginas de errores, etc.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(
						"/", "/login",
						"/crear-cuenta", "/crear-particular", "/crear-empresa",
						"/authorization","/recuperar-contrasenia","/nuevaPass","/cambiaContraseniaa", "/h2"
						).permitAll()
				.antMatchers("/admin/**").hasAuthority("ADMINISTRADOR").anyRequest()
				.authenticated().and().csrf().disable()
			.formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/ofertas")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
	
	/**
	 * Método que establece las direcciones estáticas de las vistas
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}