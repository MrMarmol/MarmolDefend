package bean.edificios;

import bean.edificios.interfaces.InEdificioCombate;
import javafx.scene.image.Image;

public class Castillo extends Edificio implements InEdificioCombate{

	public Castillo(Image imagen, double x, double y, String nombre) {
		super(imagen, x, y, nombre);
		// TODO Auto-generated constructor stub
	}

}
