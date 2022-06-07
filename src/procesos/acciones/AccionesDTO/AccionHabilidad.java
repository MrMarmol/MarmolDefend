package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.tablero.casillas.Casilla;
import procesos.habilidades.Habilidad;

public class AccionHabilidad extends Accion implements Serializable{
	
	private String habilidad;

	public AccionHabilidad(Casilla casilla_actual, String accion, Habilidad habilidad) {
		super(casilla_actual, accion);
		this.habilidad = habilidad.getNombre();
	}

	public String getHabilidad() {
		return habilidad;
	}

	
}
