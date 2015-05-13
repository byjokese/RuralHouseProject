package domain;

import java.io.Serializable;
import java.util.Date;


public class ExtraActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	private Owner owner;
	private String lugar;
	private String nombre;
	private Date fecha;

	public ExtraActivity(Owner owner, String nombre, String lugar, Date fecha, String description) {
		this.owner = owner;
		this.nombre = nombre;
		this.lugar = lugar;
		this.fecha = fecha;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return (nombre);
	}
	
	

}
