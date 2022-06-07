package bean.botones;

import bean.unidades.unidades.Unidad;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BotonBestiario extends Button{
	
	private Unidad unidad;
	private Button boton;
	private int posicion;
	public BotonBestiario(Unidad unidad, Button boton)
	{
		super();
		this.unidad = unidad;
		this.boton = boton;
		agregar_imagen(unidad);
	}
	
	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	private void agregar_imagen(Unidad unidad)
	{
		boton.setGraphic(new ImageView(unidad.getPortada()));
	}
	public Unidad getUnidad() {
		return unidad;
	}
	public void setUnidad (Unidad unidad)
	{
		this.unidad = unidad;
	}
	public Button getBoton() {
		return boton;
	}
}
