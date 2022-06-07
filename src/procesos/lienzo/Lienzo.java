package procesos.lienzo;

import bean.unidades.unidades.Unidad;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.acciones.accionesPartida.mover.AccionMover;
import procesos.acciones.accionesPartida.mover.ControladorMovimiento;

public class Lienzo {
	
	private Canvas cuadro;
	private GraphicsContext lienzo;
	
	public Lienzo(Canvas cuadro) {
		super();
		this.cuadro = cuadro;
		this.lienzo = cuadro.getGraphicsContext2D();
	}
	public Canvas getCuadro() {
		return cuadro;
	}

	public void dibujar_unidad(Image spriteSheet, Rectangle coordenadaImagen, double x, double y) {
		
		lienzo.drawImage(spriteSheet, //La imagen con todos los sprites
		coordenadaImagen.getX(), coordenadaImagen.getY(), coordenadaImagen.getWidth(), coordenadaImagen.getHeight(), //aisla el sprite en concreto del spriteSheet
		x, y,  	//la coordenada donde dibujar la imagen
		coordenadaImagen.getWidth(), coordenadaImagen.getHeight());		//El tamaño de la imagen
	}
	
	
	public void dibujar_imagen(Image imagen, double x, double y) {
    	lienzo.drawImage(imagen, x, y);
	}
}
