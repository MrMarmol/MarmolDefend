package procesos.habilidades;

import java.util.ArrayList;
import java.util.Collection;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.habilidades.animacion.AnimacionArea;
import procesos.habilidades.animacion.AnimacionLanzamiento;

/** Superclase abstracta que hereda de Habilidad. HabilidadArea permite aplicar su efecto 
 * sobre una lista de casillas (casillas_afectadas) que se encuentran dentro de su área.
 * @see AnimacionArea permite animar el efecto de la habilidad.
 */
public abstract class HabilidadArea extends Habilidad{
	
	protected ArrayList<Casilla> casillas_afectadas;
	protected AnimacionArea animacion;	
	//La animación se dibujará solo en las casillas con unidades
	protected boolean animacion_unidades;

	public HabilidadArea(String nombre, String descripcion, boolean animacion_unidades) {
		super(nombre, descripcion);
		this.animacion_unidades=animacion_unidades;
	}
	
	public boolean animarSoloUnidades() {
		return animacion_unidades;
	}
	
	public ArrayList<Casilla> getCasillasAfectadas() {
		return casillas_afectadas;
	}

	@Override
	public AnimacionArea getAnimacion() {
		return animacion;
	}
	protected void obtener_casillas_afectadas(Collection<Casilla> casillas_tablero){
		for(Casilla casilla : casillas_tablero)
			if(contacto(casilla.getX(),casilla.getY(),DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE))
				casillas_afectadas.add(casilla);		
	}

	/** Método abstracto que cada subclase implementará con sus propios valores. Este es el método que distingue a todas las habilidades implementadas*/
	public abstract void establecer_valores(Casilla casillaOrigen, Collection<Casilla> collection);

}
