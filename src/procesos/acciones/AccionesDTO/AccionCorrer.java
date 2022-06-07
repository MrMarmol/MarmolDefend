package procesos.acciones.AccionesDTO;

import java.io.Serializable;
import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;

public class AccionCorrer extends Accion implements Serializable{

	private ArrayList<String>direcciones;
	private ArrayList<Double[]>camino;
	
	public AccionCorrer(Casilla casilla_actual, ArrayList<String> direcciones,
			ArrayList<Casilla> camino) {
		super(casilla_actual, DatosGlobales.ACCION_CORRER);
		this.direcciones = direcciones;
		this.camino = new ArrayList<Double[]>();
		for(Casilla casilla : camino)
		this.camino.add(new Double[] {casilla.getX(),casilla.getY()});
	}

	public ArrayList<String> getDirecciones() {
		return direcciones;
	}

	public ArrayList<Double[]> getCamino() {
		return camino;
	}
	
	
}
