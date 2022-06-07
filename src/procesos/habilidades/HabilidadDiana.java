package procesos.habilidades;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import procesos.habilidades.animacion.AnimacionLanzamiento;
import procesos.lienzo.Lienzo;

/**Super clase abstracta que hereda de Habilidad. HabilidadDiana permite aplicar su efecto a una casilla (casillaObjetivo).
 * @see AnimacionLanzamiento permite animar el efecto de la habilidad.
 */
public abstract class HabilidadDiana extends Habilidad {
	
	protected Casilla casillaObjetivo;
	protected AnimacionLanzamiento animacion;
	
	public HabilidadDiana(String nombre, String descripcion) {
		super(nombre, descripcion);
	}

	public Casilla getCasillaObjetivo() {
		return casillaObjetivo;
	}
	public void mover_animacion() {
		animacion.mover();
	}
	public boolean comprobar_animacion_terminada() {
		return animacion.comprobar_animacion_terminada();
	}

	public void setCasillaObjetivo(Casilla casillaObjetivo) {
		this.casillaObjetivo = casillaObjetivo;
	}

	public void setAnimacion(AnimacionLanzamiento animacion) {
		this.animacion = animacion;
	}
	@Override
	public AnimacionLanzamiento getAnimacion() {
		return animacion;
	}
	
	
	/** Método abstracto que cada subclase implementará con sus propios valores. Este es el método que distingue a todas las habilidades implementadas*/
	public abstract void establecer_valores(Casilla casillaOrigen, Casilla casillaDestino, String direccion);
	
	/** Método abstracto que permitirá a sus subclases llamar al método estático comprobar_distancia(Casilla,Casilla,int)*/
	public abstract boolean comprobar_habilidad_posible(Casilla casillaOrigen, Casilla casillaObjetivo);


	
}
