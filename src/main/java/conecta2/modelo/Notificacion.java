package conecta2.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String descripcion;
	
	private boolean leida;
	
	private String siguiente;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Particular particular;
	
	public Notificacion() {
		
		this.leida = false;
	}
	
	public Notificacion(String descripcion, boolean leida, Empresa empresa, Particular particular) {
		
		this.descripcion = descripcion;
		this.leida = leida;
		this.empresa = empresa;
		this.particular = particular;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isLeida() {
		return leida;
	}

	public void setLeida(boolean leida) {
		this.leida = leida;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Particular getParticular() {
		return particular;
	}

	public void setParticular(Particular particular) {
		this.particular = particular;
	}

	public String getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(String siguiente) {
		this.siguiente = siguiente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + id;
		result = prime * result + (leida ? 1231 : 1237);
		result = prime * result + ((particular == null) ? 0 : particular.hashCode());
		result = prime * result + ((siguiente == null) ? 0 : siguiente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notificacion other = (Notificacion) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id != other.id)
			return false;
		if (leida != other.leida)
			return false;
		if (particular == null) {
			if (other.particular != null)
				return false;
		} else if (!particular.equals(other.particular))
			return false;
		if (siguiente == null) {
			if (other.siguiente != null)
				return false;
		} else if (!siguiente.equals(other.siguiente))
			return false;
		return true;
	}
	
	
	
}
