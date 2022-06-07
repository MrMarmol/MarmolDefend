package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.tablero.casillas.Casilla;

public class AccionAndar extends Accion {

	private double casilla_seleccionadaX;
	private double casilla_seleccionadaY;	
	private String direccion;
	
	public AccionAndar(Casilla casilla_actual, String accion, Casilla casillaSeleccionada, String direccion) {
		super(casilla_actual, accion);
		this.casilla_seleccionadaX = casillaSeleccionada.getX();
		this.casilla_seleccionadaY = casillaSeleccionada.getY();
		this.direccion = direccion;
	}

	public double getCasillaSeleccionadaX() {
		return casilla_seleccionadaX;
	}
	public double getCasillaSeleccionadaY() {
		return casilla_seleccionadaY;
	}
	public String getDireccion() {
		return direccion;
	}

	
}
