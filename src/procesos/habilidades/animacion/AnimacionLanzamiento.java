package procesos.habilidades.animacion;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import javafx.scene.image.Image;

//Clase encargada de animar los sprites de una habilidad y notificar cuando la animación ha terminado
public class AnimacionLanzamiento {
	
	//La animación de la habilidad aun no ha terminado
	private Image imagen;
	private Casilla casillaDestino;
	private String direccion;
	private int avance;
	
	private double x;
	private double y;
	
	public AnimacionLanzamiento(Casilla casillaOrigen, Casilla casillaDestino, String direccion, int avance) {
		super();
		this.casillaDestino = casillaDestino;
		this.direccion = direccion;
		this.avance = avance;
		this.x = casillaOrigen.getX();
		this.y = casillaOrigen.getY();
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Image getImagen() {
		return imagen;
	}
	public void mover() {
		
		switch(direccion) {
		case DatosGlobales.DERECHA:
			x+=avance;
			break;
		case DatosGlobales.IZQUIERDA :
			x -= avance;
			break;
		case DatosGlobales.ABAJO:
			y += avance;
			break;
		case DatosGlobales.ARRIBA :
			y -= avance;
			break;		
		case DatosGlobales.ARRIBA_DERECHA :
			x+=avance;
			y-= avance;
			if(x >= casillaDestino.getX())
				direccion = DatosGlobales.ARRIBA;
			if(y <= casillaDestino.getY())
				direccion = DatosGlobales.DERECHA;
			break;	
		case DatosGlobales.ARRIBA_IZQUIERDA :
			x-=avance;
			y -= avance;
			if(x <= casillaDestino.getX())
				direccion = DatosGlobales.ARRIBA;
			if(y <= casillaDestino.getY())
				direccion = DatosGlobales.IZQUIERDA;
			break;	
		case DatosGlobales.ABAJO_DERECHA :
			x+=avance;
			y += avance;
			if(x >= casillaDestino.getX())
				direccion = DatosGlobales.ABAJO;
			if(y >= casillaDestino.getY())
				direccion = DatosGlobales.DERECHA;
			break;	
		case DatosGlobales.ABAJO_IZQUIERDA :
			x-=avance;
			y += avance;
			if(x <= casillaDestino.getX())
				direccion = DatosGlobales.ABAJO;
			if(y >= casillaDestino.getY())
				direccion = DatosGlobales.IZQUIERDA;
			break;	
		}
		
		
		//Si tuviera un sheetSprite, cambiaria la imagen cada x avance y el lienzo solo cogeria la imagen ;
	}
	public boolean comprobar_animacion_terminada() {
			
			switch(direccion) {
			case DatosGlobales.DERECHA:
				if(x >= casillaDestino.getX()+DatosGlobales.CASILLA_SIZE/2)
					return true;
			case DatosGlobales.IZQUIERDA :
				if(x <= casillaDestino.getX()-DatosGlobales.CASILLA_SIZE/2)
					return true;
			case DatosGlobales.ABAJO:
				if(y >= casillaDestino.getY()+DatosGlobales.CASILLA_SIZE/2)
					return true;
			case DatosGlobales.ARRIBA :
				if(y <= casillaDestino.getY()-DatosGlobales.CASILLA_SIZE/2)
					return true;	
			case DatosGlobales.ARRIBA_DERECHA :
				if(x >= casillaDestino.getX()+DatosGlobales.CASILLA_SIZE/2 && y <= casillaDestino.getY()-DatosGlobales.CASILLA_SIZE/2)
					return true;
				break;	
			case DatosGlobales.ARRIBA_IZQUIERDA :
				if(x <= casillaDestino.getX()-DatosGlobales.CASILLA_SIZE/2 && y <= casillaDestino.getY()-DatosGlobales.CASILLA_SIZE/2)
					return true;
				break;	
			case DatosGlobales.ABAJO_DERECHA :
				if(x >= casillaDestino.getX()+DatosGlobales.CASILLA_SIZE/2 && y >= casillaDestino.getY()+DatosGlobales.CASILLA_SIZE/2)
					return true;
				break;	
			case DatosGlobales.ABAJO_IZQUIERDA :
				if(x <= casillaDestino.getX()-DatosGlobales.CASILLA_SIZE/2 && y >= casillaDestino.getY()+DatosGlobales.CASILLA_SIZE/2)
					return true;
				break;
			}
			return false;
	}

}
