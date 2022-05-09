package bean.edificios;

import bean.edificios.interfaces.InEdificioProduccion;
import javafx.scene.image.Image;

public class Aserradero extends Edificio implements InEdificioProduccion {

	public Aserradero(Image imagen, double x, double y, String nombre) {
		super(imagen, x, y, nombre);
	}

}
