package procesos.acciones.accionesPartida.construir;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.tablero.casillas.Casilla;

public class ConstruirCimientos {
	
	private Edificio edificio;
	
	public ConstruirCimientos(Edificio edificio) {
		this.edificio = edificio;
	}
	
	public void establecer_cimientos(double x, double y) {
		edificio.setX(x);
		edificio.setY(y);
	}
		
	//Si el edificio está sobre la casilla
	public boolean contacto(double x, double y, double w, double h) {
				
		//Si el edificio ocupa una casilla por muy poco espacio, se obvia, para ello se le resta 10 píxeles al tamaño
			if(x >= edificio.getX() + edificio.getImagen().getWidth()-10)
				return false;
			if(x+w <= edificio.getX())
				return false;
			if(y >= edificio.getY()+edificio.getImagen().getHeight()-10)
				return false;
			if(y+h <= edificio.getY())
				return false;

			return true;			
	}			
	//Verifica que las casillas que componen el edificio están vacias y que no tienen otro edificio
	public boolean casillas_libres() {
		for(Casilla casilla : edificio.getCasillas())
			if(casilla.getUnidad()!= null || casilla.getEdificio().equals(edificio))
				 return false;
		return true;
	}
	public Edificio getEdificio() {
		return edificio;
	}
	public void agregar_cimiento(Casilla casilla) {
			casilla.setEdificio(edificio);
			edificio.getCasillas().add(casilla);
			
	}
	public boolean comprobar_casilla_edificable(Casilla casilla) {
		if(casilla.getEdificio()==null && casilla.getUnidad()==null && !casilla.isObstaculo())
			return true;
		return false;
	}
	public void eliminar_edificio() {
		for(Casilla casilla : edificio.getCasillas()) {
			casilla.setEdificio(null);
			casilla.getBoton().setText("");
		}
		edificio.getCasillas().clear();
	}
}
