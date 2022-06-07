package procesos.acciones.AccionesDTO;

import java.io.Serializable;
import java.util.ArrayList;

import bean.tablero.casillas.Casilla;
import procesos.habilidades.Habilidad;
import procesos.habilidades.HabilidadArea;

public class AccionHabilidadArea extends AccionHabilidad implements Serializable{
	
	
	public AccionHabilidadArea(Casilla casilla_actual, String accion, HabilidadArea habilidad) {
		super(casilla_actual, accion, habilidad);
	
	}

	
}
