package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.tablero.casillas.Casilla;

public class AccionAlojar extends Accion implements Serializable{

	private double casilla_seleccionadaX;
	private double casilla_seleccionadaY;
	
	public AccionAlojar(Casilla casilla_actual,  Casilla casillaSeleccionada, Edificio edificio) {
		super(casilla_actual, DatosGlobales.ACCION_ALOJAR);
		this.casilla_seleccionadaX = casillaSeleccionada.getX();
		this.casilla_seleccionadaY = casillaSeleccionada.getY();
	}
	public double getCasillaSeleccionadaX() {
		return casilla_seleccionadaX;
	}
	public double getCasillaSeleccionadaY() {
		return casilla_seleccionadaY;
	}

}
