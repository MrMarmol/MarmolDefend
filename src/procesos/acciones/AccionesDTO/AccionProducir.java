package procesos.acciones.AccionesDTO;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;

public class AccionProducir extends Accion{

	private double casilla_seleccionadaX;
	private double casilla_seleccionadaY;
	
	private String nombreUnidad;
	public AccionProducir(Casilla casilla_seleccionada, Casilla casilla_actual,Unidad unidad) {
		super(casilla_actual, DatosGlobales.ACCION_PRODUCIR);
		this.casilla_seleccionadaX = casilla_seleccionada.getX();
		this.casilla_seleccionadaY = casilla_seleccionada.getY();
		this.nombreUnidad = unidad.getNombre();
	}
	public String getNombreUnidad() {
		return nombreUnidad;
	}
	public double getCasilla_seleccionadaX() {
		return casilla_seleccionadaX;
	}
	public double getCasilla_seleccionadaY() {
		return casilla_seleccionadaY;
	}

	
	
}
