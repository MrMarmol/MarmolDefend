package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.tablero.casillas.Casilla;

public class Accion implements Serializable{

	//Las casillas contienen botones fx, que no pueden serializarse
	//Las coordenadas de la casillas actuan como claves para el tablero
	protected double casilla_actualX;
	protected double casilla_actualY;
	protected String accion;
	
	public Accion(Casilla casilla_actual, String accion) {
		super();
		if(casilla_actual!=null) {
			this.casilla_actualX = casilla_actual.getX();
			this.casilla_actualY = casilla_actual.getY();
		}
		this.accion = accion;
	}
	public double getCasillaActualX() {
		return casilla_actualX;
	}
	public double getCasillaActualY() {
		return casilla_actualY;
	}
	public String getAccion() {
		return accion;
	}

}
