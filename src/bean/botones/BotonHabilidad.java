package bean.botones;

import javafx.scene.control.Button;
import procesos.habilidades.Habilidad;

public class BotonHabilidad {
	
	private Button boton;
	private Habilidad habilidad;
	
	public BotonHabilidad(Button boton, Habilidad habilidad) {
		super();
		this.boton = boton;
		this.habilidad = habilidad;
		boton.setText(habilidad.getNombre());
	}
	public Button getBoton() {
		return boton;
	}
	public Habilidad getHabilidad() {
		return habilidad;
	}

}
