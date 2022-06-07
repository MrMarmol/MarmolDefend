package procesos.habilidades;

import java.util.ArrayList;
import bean.tablero.casillas.Casilla;
import javafx.scene.shape.Rectangle;
import procesos.habilidades.animacion.AnimacionArea;
import procesos.habilidades.animacion.AnimacionLanzamiento;


/**Super clase abstracta que hereda de Habilidad. HabilidadLineal permite aplicar su efecto a una lista de casillas adyacentes
 *  entre s� y a la casillaOrigen que inicia la habilidad. Aunque l�gicamente es similar a HabilidadArea, conceptualmente no.
 * @see AnimacionArea permite animar el efecto de la habilidad.
 * @see Tablero	
 */
public abstract class HabilidadLineal extends Habilidad {

	protected ArrayList<Casilla> casillas_afectadas;
	protected AnimacionArea animacion;
	
	public HabilidadLineal(String nombre, String descripcion) {
		super(nombre, descripcion);		
	}
	@Override
	public AnimacionArea getAnimacion() {
		return animacion;
	}
	public ArrayList<Casilla> getCasillasAfectadas(){
		return casillas_afectadas;
	}
	/**Devuelve un valor n�merico equivalente al n�mero de casillas que forma la l�nea. Es una constante cuyo valor
	 * inicializar� cada hijo de esta clase, por lo que el atributo lo agregar� �nicamente sus herederos.*/
	public abstract int getDistancia();
	
	/**M�todo abstracto que cada subclase implementar� con sus propios valores. Este es el m�todo que distingue a todas las habilidades implementadas*/
	public abstract void establecer_valores(Casilla casillaOrigen, ArrayList<Casilla> casillas_adyacentes);}
