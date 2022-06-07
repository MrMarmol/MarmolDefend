package procesos.habilidades;

import java.util.ArrayList;
import java.util.Collection;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.sprites.SpritesUnidad;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.habilidades.animacion.AnimacionLanzamiento;
import procesos.lienzo.Lienzo;

/** Superclase abstracta que engloba todas las habilidades.
 * Se ramifca en HabilidadDiana, HabilidadArea y HabilidadLineal*/
public abstract class Habilidad {
		
	protected String nombre;
	protected Casilla casillaOrigen;	
	protected Image imagen;
	protected Rectangle area;
	protected int mana;
	private String descripcion;
	
			
	/**Constructor que se instancia en el BotonHabilidad*/
	public Habilidad(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	} 
	public abstract int getMana();

	protected abstract void aplicar_coste_habilidad(Unidad unidad);
	
	/**Devuelve la animaci�n de la habilidad*/
	public abstract Object getAnimacion();
	/**M�todo que escribir�n todas las habilidades implementadas*/
	public abstract void aplicar_efecto();

	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public Rectangle getArea() {
		return area;
	}
	public Casilla getCasillaOrigen() {
		return casillaOrigen;
	}

	public void setCasillaOrigen(Casilla casillaOrigen) {
		this.casillaOrigen = casillaOrigen;
	}

	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
	

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/** M�todo que permite saber si las coordenadas de un objeto se encuentran parcial o completamente dentro del �rea.
	 * Se ignoran los objetos que est�n en contacto de manera superficial, rest�ndo 10 p�xeles al �rea.
	 * @param  x  la coordenada x del objeto
	 * @param  y  la coordenada y del objeto
	 * @param  w  el ancho del objeto
	 * @param  h  el alto del objeto
	 * @param  area  el rect�ngulo que forma el �rea por sus valores x,y,w,h y que ser�n comparados con los del objeto
	 * @return boolean false si las coordenadas del objeto no est�n en contacto con las del �rea y true en caso de que s�.
	 */
	protected boolean contacto(double x, double y, double w, double h) {
		
			if(x >= area.getX() + area.getWidth()) {
				return false;
			}
			if(x+w <= area.getX()){
				return false;
			}
			if(y >= area.getY()+area.getHeight()){
				return false;
			}
			if(y+h <= area.getY()){
				return false;
			}
			System.out.println(x+","+y);
			return true;			
	}	
	
	/** M�todo que permite obtener el valor del �rea en funci�n de la Casilla sobre la que se lanza la habilidad (casillaFoco)
	 * y mediante la distancia m�xima de la habilidad. Consigue las coordenadas X e Y descendiendo en direcci�n diagonal inferior 
	 * izquierda y el ancho y el alto mediante el doble de la distancia.
	 * @param casillaFoco casilla desde la que se lanza la habilidad; el centro del �rea
	 * @param distancia el n�mero de casillas partiendo desde el foco al que puede alcanzar la habilidad
	 * @return new Rectangle(x,y, distancia*2, distancia*2) el �rea de la habilidad
	 */
	protected Rectangle obtener_area(Casilla casillaFoco, int distancia) {
		
		System.out.println("Coordenadas casillaFoco : "+casillaFoco.getX()+","+casillaFoco.getY());
		double x = casillaFoco.getX()-(distancia*DatosGlobales.CASILLA_SIZE);
		double y = casillaFoco.getY()-(distancia*DatosGlobales.CASILLA_SIZE); 		
		return new Rectangle(x,y, distancia*2*DatosGlobales.CASILLA_SIZE+DatosGlobales.CASILLA_SIZE, distancia*2*DatosGlobales.CASILLA_SIZE+DatosGlobales.CASILLA_SIZE);
	}
		
}
