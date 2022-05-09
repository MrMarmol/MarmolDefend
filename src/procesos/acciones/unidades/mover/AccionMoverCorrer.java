package procesos.acciones.unidades.mover;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.Unidad;

public class AccionMoverCorrer extends AccionMover{
	private ArrayList<String> direcciones;
	private ArrayList<Casilla> camino;
	private int posicion_actual;


	
	public AccionMoverCorrer(Casilla casillaOrigen, Casilla casillaDestino, String direccion, int paso,
			ArrayList<String> direcciones, ArrayList<Casilla> camino ) {
		super(casillaOrigen, casillaDestino, direccion, paso);
		this.direcciones = direcciones;
		this.camino = camino;
		posicion_actual = 0;
	}
	public ArrayList<String> getDirecciones() {
		return direcciones;
	}
	public ArrayList<Casilla> getCamino() {
		return camino;
	}
	public void sumar_casilla() {
		posicion_actual++;
	}
	public int getPosicionActual() {
		return posicion_actual;
	}
	public void avanzar_casilla() {
		casillaOrigen = camino.get(posicion_actual);
		casillaDestino = camino.get(posicion_actual+1);
		direccion = direcciones.get(posicion_actual);
		x = casillaOrigen.getX();
		y = casillaOrigen.getY();
		pasos = 0;
		casillaOrigen.setUnidad(null);
	}
	public String toString() {
		return camino.size()+""+direcciones.size();
	}

}
