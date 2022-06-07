package procesos.acciones.accionesPartida.mover;

import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;

public class AccionMoverAndar extends AccionMover{

	public AccionMoverAndar(Casilla casillaOrigen, Casilla casillaDestino, String direccion, int paso) {
		super(casillaOrigen, casillaDestino, direccion, paso);
	}

}
