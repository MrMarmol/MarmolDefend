package procesos.acciones.unidades.construir;

import java.util.ArrayList;

import bean.edificios.Edificio;
import bean.unidades.Unidad;

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
}
