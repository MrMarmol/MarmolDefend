package procesos.acciones.habilidades;

import java.util.ArrayList;

import bean.tablero.casillas.Casilla;

public class Habilidad {
	
	//Las casillas sobre las que la habilidad desatará su efecto
	private ArrayList<Casilla> casillas_afectadas;
	private Efecto efecto;
	
	public void aplicar_efecto() {
		for(Casilla casilla : casillas_afectadas)
			efecto.aplicar_efecto(casilla);
	}

}
