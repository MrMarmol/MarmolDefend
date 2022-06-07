package procesos.acciones.accionesPartida.mover;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import procesos.lienzo.Lienzo;

//Clase encargada del movimiento de las unidades. Desplaza sus coordenadas, dibuja la unidad en su nueva coordenada y comprueba si ha llegado a la casilla
public class AccionMover {
	
	protected Casilla casillaOrigen;
	protected Casilla casillaDestino;
	protected Unidad unidad;
	protected int pasos;
	protected int paso;
	protected String direccion;
	
	protected double x;
	protected double y;

	public AccionMover(Casilla casillaOrigen, Casilla casillaDestino, String direccion, int paso) {
		super();
		this.casillaOrigen = casillaOrigen;
		this.casillaDestino = casillaDestino;
		this.unidad = casillaOrigen.getUnidad();
		this.direccion = direccion;
		this.paso = paso;
		this.pasos = 0;
		this.x = casillaOrigen.getX();
		this.y = casillaOrigen.getY();
		this.casillaOrigen.setUnidad(null);
	}

	public Casilla getCasillaOrigen() {
		return casillaOrigen;
	}
	public Casilla getCasillaDestino() {
		return casillaDestino;
	}
	public void setCasillaOrigen(Casilla casillaOrigen) {
		this.casillaOrigen = casillaOrigen;
	}

	public void setCasillaDestino(Casilla casillaDestino) {
		this.casillaDestino = casillaDestino;
	}


	public Unidad getUnidad() {
		return unidad;
	}
	public int paso() {
		return pasos;
	}
	public void setPasos(int pasos) {
		this.pasos = pasos;
	}
	public void sumarPaso() {
		pasos+=paso;
	}
	public void setDireccion(String movimiento) {
		this.direccion = movimiento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setPaso(int paso) {
		this.paso = paso;
	}
	public int getPasos() {
		return pasos;
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
	public boolean comprobar_trayecto_finalizado() {
		if(pasos==DatosGlobales.CASILLA_SIZE) {
			casillaDestino.setUnidad(unidad);
			casillaDestino.getUnidad().getAnimacion().setDireccionActual(direccion);
			return true;
		}
		return false;
	}
	public void mover() {
		
		switch(direccion) {
		case DatosGlobales.DERECHA:
			x+=paso;
			break;
		case DatosGlobales.IZQUIERDA :
			x -= paso;
			break;
		case DatosGlobales.ABAJO:
			y += paso;
			break;
		case DatosGlobales.ARRIBA :
			y -= paso;
			break;			
		}
		pasos+=paso;		
	}
	public void dibujar_unidad(Lienzo lienzo, int sprite) {
		lienzo.dibujar_unidad(
				unidad.getAnimacion().getSpriteSheet(), 
				unidad.getAnimacion().getSprites(direccion).get(sprite),
				x, y);
	}
}
	
