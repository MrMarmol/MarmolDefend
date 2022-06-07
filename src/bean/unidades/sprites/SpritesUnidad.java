package bean.unidades.sprites;

import java.util.ArrayList;
import java.util.HashMap;

import bean.datos_globales.DatosGlobales;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.acciones.accionesPartida.mover.AccionMover;
import procesos.lienzo.Lienzo;

//Bean que almacenalos sprites de las unidades
public class SpritesUnidad {

	private int duracion;
	private HashMap<String,ArrayList<Rectangle>> sprite_direcciones;
	private Image spriteSheet;
	private String direccion_actual;
	
	public SpritesUnidad(int duracion, HashMap<String,ArrayList<Rectangle>> sprite_direcciones, Image spriteSheet) {
		super();
		this.duracion = duracion;
		this.sprite_direcciones = sprite_direcciones;
		this.spriteSheet = spriteSheet;
		this.direccion_actual=DatosGlobales.ABAJO;
	}
	public SpritesUnidad(SpritesUnidad sprites) {
		this.duracion = sprites.getDuracion();
		this.sprite_direcciones = sprites.getDirecciones();
		this.spriteSheet = sprites.getSpriteSheet();
		this.direccion_actual = DatosGlobales.ABAJO;
	}
	public HashMap<String,ArrayList<Rectangle>> getDirecciones(){
		return sprite_direcciones;
	}
	public int getDuracion() {
		return duracion;
	}
	public ArrayList<Rectangle> getSprites(String direccion) {
		return sprite_direcciones.get(direccion);
	}
	public Image getSpriteSheet() {
		return spriteSheet;
	}

	public ArrayList<Rectangle> getDireccionActual(){
		return sprite_direcciones.get(direccion_actual);
	}
	public void setDireccionActual(String movimiento) {
		direccion_actual=movimiento;
	}
}
