package conecta2.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "activacion",  uniqueConstraints= @UniqueConstraint(columnNames= {"Email", "Activacion", "Hora "}) )
/**
 * Entidad / Objeto de Negocio de Activación
 * Se utiliza para persistir la información de la activación de la cuenta
 */
public class Activacion {
	
	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String email;
	
	private String activacion;
	
	private Calendar hora;
	
	/**
	 * Constructora sin argumentos necesaria para JPA
	 */
	public Activacion() {}
	
	/**
	 * Constructora por defecto
	 * @param activacion
	 * @param email
	 * @param hora
	 */
	public Activacion(String activacion, String email) {
		this.activacion = activacion;
		this.email = email;
		this.hora = Calendar.getInstance();
		hora.add(Calendar.HOUR_OF_DAY,1);

	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActivacion() {
		return activacion;
	}

	public void setActivacion(String activacion) {
		this.activacion = activacion;
	}

	public Calendar getHora() {
		return hora;
	}

	public void setHora(Calendar hora) {
		this.hora = hora;
	}
	
	public boolean isCaducado(){
		
		if(hora.getTimeInMillis()>=Calendar.getInstance().getTimeInMillis()) {
			return true;
		}else
			return false;
	}
	
	
	

}
