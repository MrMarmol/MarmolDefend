package procesos.acciones.AccionesDTO;

import java.io.Serializable;

import bean.tablero.casillas.Casilla;
import procesos.habilidades.Habilidad;

public class AccionHabilidadDiana extends AccionHabilidad implements Serializable{
		
	private double casillaSeleccionadaX;
	private double casillaSeleccionadaY;
	private String direccion;
	
	public AccionHabilidadDiana(Casilla casilla_actual, String accion, Habilidad habilidad, Casilla casillaSeleccionada, String direccion) {
		super(casilla_actual, accion, habilidad);
		this.casillaSeleccionadaX = casillaSeleccionada.getX();
		this.casillaSeleccionadaY = casillaSeleccionada.getY();	
		this.direccion = direccion;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public double getCasillaSeleccionadaX() {
		return casillaSeleccionadaX;
	}

	public double getCasillaSeleccionadaY() {
		return casillaSeleccionadaY;
	}
}
