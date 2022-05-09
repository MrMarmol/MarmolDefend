package bean.unidades;

import java.util.ArrayList;

import bean.unidades.interfaces.InUnidadProduccion;
import javafx.scene.image.Image;
import procesos.animacion.Animacion;

public class Aldeano extends Unidad implements InUnidadProduccion{

	public Aldeano(String nombre, String descripcion, Image cara, Image portada, Animacion animacion, int at, int def,
			int pg, ArrayList<String> habilidades, String bando) {
		super(nombre, descripcion, cara, portada, animacion, at, def, pg, habilidades, bando);
	}

}
