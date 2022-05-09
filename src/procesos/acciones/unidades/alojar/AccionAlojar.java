package procesos.acciones.unidades.alojar;

import bean.edificios.Edificio;
import bean.unidades.Unidad;

public class AccionAlojar {
	
	private Unidad unidad;
	private Edificio edificio;
	public AccionAlojar(Unidad unidad, Edificio edificio) {
		super();
		this.unidad = unidad;
		this.edificio = edificio;
	}
	public Unidad getUnidad() {
		return unidad;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	public Edificio getEdificio() {
		return edificio;
	}
	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	public boolean alojar() {
		if(edificio.comprobar_alojamiento()) {
			edificio.alojar(this);
			return true;
		}
		else return false;
	}	

}
