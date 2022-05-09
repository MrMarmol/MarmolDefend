package procesos.lienzo;

import bean.unidades.Unidad;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.acciones.unidades.mover.AccionMover;
import procesos.acciones.unidades.mover.ControladorMovimiento;

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

	public void dibujar_unidad(AccionMover movimiento, Rectangle coordenadaImagen, double x, double y) {
	
		lienzo.drawImage(movimiento.getUnidad().getAnimacion().getSpriteSheet(), //la imagen con todos los movimientos
		coordenadaImagen.getX(), coordenadaImagen.getY(), coordenadaImagen.getWidth(), coordenadaImagen.getHeight(), //aisla el sprite en concreto del spriteSheet
		x, y,  //la coordenada para dibujar la imagen
		coordenadaImagen.getWidth(), coordenadaImagen.getHeight());	//el tamaño de la imagen	
	}
	public void dibujar_unidad(Unidad unidad, Rectangle coordenadaImagen, double x, double y) {
		
		lienzo.drawImage(unidad.getAnimacion().getSpriteSheet(), 
		coordenadaImagen.getX(), coordenadaImagen.getY(), coordenadaImagen.getWidth(), coordenadaImagen.getHeight(),
		x, y,  
		coordenadaImagen.getWidth(), coordenadaImagen.getHeight());		
	}
	
	public void dibujar_imagen(Image imagen, double x, double y) {
    	lienzo.drawImage(imagen, x, y);
	}
}
