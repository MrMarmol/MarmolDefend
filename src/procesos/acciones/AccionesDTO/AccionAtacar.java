package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;

public class AccionAtacar extends Accion implements Serializable{

	private double casilla_seleccionadaX;
	private double casilla_seleccionadaY;
	
	public AccionAtacar(Casilla casilla_actual, Casilla casilla_seleccionada) {
		super(casilla_actual, DatosGlobales.ACCION_ATACAR);
		this.casilla_seleccionadaX = casilla_seleccionada.getX();
		this.casilla_seleccionadaY = casilla_seleccionada.getY();

	}

	public double getCasillaSeleccionadaX() {
		return casilla_seleccionadaX;
	}
	public double getCasillaSeleccionadaY() {
		return casilla_seleccionadaY;
	}
}
