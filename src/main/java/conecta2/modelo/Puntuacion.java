package conecta2.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "puntuaciones")
public class Puntuacion {

	/**
	 * Id que genera la base de datos automáticamente, no se debe asignar manualmente
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private double puntuacion;
	
	@ManyToOne (fetch=FetchType.EAGER)
	private Particular particular;

	@ManyToOne (fetch=FetchType.EAGER)
	private Empresa empresa;
	
	public Puntuacion() {}
	
	public Puntuacion(double puntuacion, Particular particular, Empresa empresa) {
		this.puntuacion = puntuacion;
		this.particular = particular;
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(double puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Particular getParticular() {
		return particular;
	}

	public void setParticular(Particular particular) {
		this.particular = particular;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + id;
		result = prime * result + ((particular == null) ? 0 : particular.hashCode());
		long temp;
		temp = Double.doubleToLongBits(puntuacion);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Puntuacion other = (Puntuacion) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id != other.id)
			return false;
		if (particular == null) {
			if (other.particular != null)
				return false;
		} else if (!particular.equals(other.particular))
			return false;
		if (Double.doubleToLongBits(puntuacion) != Double.doubleToLongBits(other.puntuacion))
			return false;
		return true;
	}

	
}
