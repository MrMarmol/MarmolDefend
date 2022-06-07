package procesos.acciones.AccionesDTO;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import procesos.habilidades.Habilidad;

public class AccionHabilidadLineal extends AccionHabilidad {
	
	private String direccion;

	
	public AccionHabilidadLineal(Casilla casilla_actual, Habilidad habilidad, String direccion) {
		super(casilla_actual, DatosGlobales.ACCION_HABILIDAD, habilidad);
		this.direccion = direccion;
	}


	public String getDireccion() {
		return direccion;
	}


}
