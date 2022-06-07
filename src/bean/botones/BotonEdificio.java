package bean.botones;

import bean.edificios.Edificio;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;

public class BotonEdificio {
	
	private Edificio edificio;
	private Button boton;
	
	public BotonEdificio(Edificio edificio, Button boton)
	{
		super();
		this.edificio = edificio;
		this.boton = boton;
		agregar_imagen(edificio);
	}
	private void agregar_imagen(Edificio edificio)
	{
		ImageView imagen = new ImageView(edificio.getPortada());

		  boton.setGraphic(imagen);
		
		//  boton.setContentDisplay(ContentDisplay.TOP);
		  imagen.fitWidthProperty().bind(boton.widthProperty().divide(2));
		  imagen.fitHeightProperty().bind(boton.heightProperty().divide(2));

		  imagen.setPreserveRatio(false);

	}
	public Edificio getEdificio() {
		return edificio;
	}
	public void setEdificio (Edificio edificio)
	{
		this.edificio = edificio;
	}
	public Button getBoton() {
		return boton;
	}
}