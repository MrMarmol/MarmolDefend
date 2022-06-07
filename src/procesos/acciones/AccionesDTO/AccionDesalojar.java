package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.tablero.casillas.Casilla;

public class AccionDesalojar extends Accion{
	
	private double casillaSeleccionadaX;
	private double casillaSeleccionadaY;
	private int numUnidad;
	public AccionDesalojar(Casilla casilla_actual, Casilla casillaSeleccionada, int numUnidad) {
		super(casilla_actual, DatosGlobales.ACCION_DESALOJAR);
		this.casillaSeleccionadaX = casillaSeleccionada.getX();
		this.casillaSeleccionadaY = casillaSeleccionada.getY();
		this.numUnidad = numUnidad;
	}

	public double getCasillaSeleccionadaX() {
		return casillaSeleccionadaX;
	}

	public double getCasillaSeleccionadaY() {
		return casillaSeleccionadaY;
	}
	public int getNumUnidad() {
		return numUnidad;
	}

	
}
