package procesos.acciones.accionesPartida.construir;

import java.util.ArrayList;

import bean.edificios.Edificio;
import bean.edificios.EdificioProduccion;
import bean.recurso.Recurso;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;

public class ControladorEdificios {

	private ArrayList<Edificio> edificios;
	
	public ControladorEdificios() {
		edificios = new ArrayList<Edificio>();
	}
	
	public ArrayList<Edificio> getEdificios() {
		return edificios;
	}

	public void add(Edificio edificio) {
		edificios.add(edificio);
	}
	
	public ArrayList<Recurso> getGanancias(){
		ArrayList<Recurso> ganancias = new ArrayList<Recurso>();
		
		for(Edificio edificio : edificios) {
			if(edificio instanceof EdificioProduccion) 
				ganancias.add(((EdificioProduccion)edificio).getGanancia());			
		}
		return ganancias;
	}
	public ArrayList<Recurso> getGanancias(String bando){
		ArrayList<Recurso> ganancias = new ArrayList<Recurso>();
		
		for(Edificio edificio : edificios) {
			if(edificio instanceof EdificioProduccion && edificio.getBando().equals(bando)) 
				ganancias.add(((EdificioProduccion)edificio).getGanancia());			
		}
		return ganancias;
	}
	public void eliminar_edificio(Edificio edificio) {
		for(Casilla casilla : edificio.getCasillas()) 
			casilla.setEdificio(null);
		edificios.remove(edificio);
		
	}
}
