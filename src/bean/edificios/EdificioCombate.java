package bean.edificios;

import java.util.ArrayList;

import bean.recurso.Recurso;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;

public class EdificioCombate extends Edificio {

	private ArrayList<Unidad> produccion_unidades;

	public EdificioCombate(Image imagen, String nombre, Image portada, int at, int def, int pg, int num_alojamientos,ArrayList<Recurso> coste,
			ArrayList<Unidad> produccion_unidades) {
		super(imagen, nombre, portada, at, def, pg, num_alojamientos, coste);
		this.produccion_unidades = produccion_unidades;
	}
	public EdificioCombate(Edificio edificio) {
		super(edificio);
		this.produccion_unidades = ((EdificioCombate)edificio).getProduccion_unidades();

	}


	public ArrayList<Unidad> getProduccion_unidades() {
		return produccion_unidades;
	}

	
}
