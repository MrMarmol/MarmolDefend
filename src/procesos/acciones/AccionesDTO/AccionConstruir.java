package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.edificios.Edificio;
import bean.tablero.casillas.Casilla;

public class AccionConstruir extends Accion implements Serializable{

	private String nombreEdificio;
	
	public AccionConstruir(Casilla casilla_actual, String accion, Edificio edificio) {
		super(casilla_actual, accion);
		this.nombreEdificio = edificio.getNombre();
	}
	public String getNombreEdificio() {
		return nombreEdificio;
	}


}
