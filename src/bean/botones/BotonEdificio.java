package bean.botones;

import bean.edificios.Edificio;
import javafx.scene.control.Button;

public class BotonEdificio {
	
	private Edificio edificio;
	private Button boton;
	
	public BotonEdificio(Edificio edificio, Button boton)
	{
		super();
		this.edificio = edificio;
		this.boton = boton;
		this.boton.setPrefWidth(100);
		agregar_nombre(edificio);
	}
	private void agregar_nombre(Edificio edificio)
	{
		boton.setText(edificio.getNombre());
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