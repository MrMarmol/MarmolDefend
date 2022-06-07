package bean.tablero.casillas;

import java.io.Serializable;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.unidades.unidades.Unidad;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Casilla implements Serializable{
	
	private Button boton;
	private Unidad unidad;
	private Edificio edificio;
	private boolean seleccionada;
	private boolean camino;
	private boolean obstaculo;
	
	public Casilla(Button boton, Unidad unidad) {
		super();
		this.boton = boton;
		this.unidad = unidad;
		edificio = null;
		camino = false;
	}
	
	public Casilla(double x, double y) {
		this.boton = new Button();

		boton.setLayoutX(x);
		boton.setLayoutY(y);
		boton.setPrefSize(32,32);
		boton.setStyle("-fx-padding: 0");
        boton.setBackground(null);
		unidad = null;		
		edificio = null;
		seleccionada = false;
		
	}
	
	
	public boolean isObstaculo() {
		return obstaculo;
	}

	public void setObstaculo(boolean obstaculo) {
		this.obstaculo = obstaculo;
	}

	public Button getBoton() {
		return boton;
	}
	
	public Unidad getUnidad() {
		return unidad;
	}
	
	public Edificio getEdificio() {
		return edificio;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	public double getX() {
		return boton.getLayoutX();
	}
	public double getY() {
		return boton.getLayoutY();
	}
	public boolean getSeleccionada() {
		return seleccionada;
	}
	public void setSeleccionada(boolean seleccionada) {
		this.seleccionada = seleccionada;
	}
	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	public boolean getCamino() {
		return camino;
	}
	public void setCamino(boolean camino) {
		this.camino = camino;
	}
	

	
}
