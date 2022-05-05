package bean.edificios;

import java.util.ArrayList;

import app.BotonBestiario;
import bean.tablero.casillas.Casilla;
import javafx.scene.image.Image;

public class Edificio{
	
	private String nombre;
	public ArrayList<Casilla> casillas;
	public ArrayList<BotonBestiario> unidades;
	public Image imagen;
	public double x;
	public double y;
	
	//El coste para producirlo, el botonEdificio accederá a estos datos. El oro solo para comprar y la comida solo para producir
	private int metal;
	private int madera;
	

	public Edificio(Image imagen, double x, double y,String nombre) {
		this.imagen = imagen;
		this.x = x;
		this.y = y;
		casillas = new ArrayList<Casilla>();
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	
	public boolean contacto(double x, double y, double w, double h) {
				
			if(x > this.x + imagen.getWidth())
				return false;
			if(x+w < this.x)
				return false;
			if(y > this.y+imagen.getHeight())
				return false;
			if(y+h < this.y)
				return false;

			return true;			
	}			
}
