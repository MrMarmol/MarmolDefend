package bean.tablero;

import java.util.HashMap;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.tablero.casillas.CasillaObstaculo;

public class Tablero {
	
	private HashMap<Key,Casilla> casillas;
	private HashMap<Key,CasillaObstaculo> casillasObstaculo;

	public Tablero() {
		casillas = new HashMap<Key,Casilla>();
		casillasObstaculo = new HashMap<Key,CasillaObstaculo>();
	}
	
	public HashMap<Key,Casilla> getCasillas() {
		return casillas;
	}
	public HashMap<Key,CasillaObstaculo> getObstaculos() {
		return casillasObstaculo;
	}
	
	public Casilla get_casilla(double x, double y) {
		for(Key clave : casillas.keySet()) 
			if(clave.getX()>=x && x<(clave.getX()+DatosGlobales.CASILLA_SIZE))//x se encuentra dentro de la casilla 
			return casillas.get(clave);
		return null;									
	}
	
	public Casilla getCasillaAdyacente(Casilla casilla, String direccion){
				
		switch(direccion) {
			case DatosGlobales.DERECHA :
			return casillas.get(new Key(casilla.getX()+32, casilla.getY()));

			case DatosGlobales.IZQUIERDA : 
				return casillas.get(new Key(casilla.getX()-32, casilla.getY()));

			case DatosGlobales.ARRIBA :  
				return casillas.get(new Key(casilla.getX(), casilla.getY()-32));

			case DatosGlobales.ABAJO :
				return casillas.get(new Key(casilla.getX(), casilla.getY()+32));	
		}
		return null;
	}
	public boolean comprobar_casilla_adyacente(Casilla casillaActual, Casilla casillaSeleccionada) {
		
		/*Si está a su derecha en diagonal*/
		if(casillaSeleccionada.getX()+DatosGlobales.CASILLA_SIZE==casillaActual.getX() && 
				casillaSeleccionada.getY()+DatosGlobales.CASILLA_SIZE==casillaActual.getY() ||
				casillaSeleccionada.getY()-DatosGlobales.CASILLA_SIZE==casillaActual.getY())
			return true;
		/*Si está a su izquierda en diagonal)*/
		if(casillaSeleccionada.getX()-DatosGlobales.CASILLA_SIZE==casillaActual.getX() && 
				casillaSeleccionada.getY()+DatosGlobales.CASILLA_SIZE==casillaActual.getY() ||
				casillaSeleccionada.getY()-DatosGlobales.CASILLA_SIZE==casillaActual.getY())
			return true;
		
		/*Si está a su derecha*/
		if(casillaSeleccionada.getX()==casillaActual.getX()+DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getY()==casillaActual.getY())
			return true;
		
		/*Si está a su izquierda*/
		if(casillaSeleccionada.getX()==casillaActual.getX()-DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getY()==casillaActual.getY())
			return true;
		
		/*Si está abajo*/
		if(casillaSeleccionada.getY()==casillaActual.getY()+DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getX()==casillaActual.getX())
			return true;
				
		/*Si está arriba*/
		if(casillaSeleccionada.getY()==casillaActual.getY()-DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getX()==casillaActual.getX())
			return true;
		
		return false;
	}
	
	//La construcción de un camino no permite casillas en diagonal, por lo que se excluye.
	public boolean comprobar_casillas_adyacentes(Casilla casillaActual, Casilla casillaSeleccionada) {
					
		/*Si está a su derecha*/
		if(casillaSeleccionada.getX()==casillaActual.getX()+DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getY()==casillaActual.getY())
			return true;
		
		/*Si está a su izquierda*/
		if(casillaSeleccionada.getX()==casillaActual.getX()-DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getY()==casillaActual.getY())
			return true;
		
		/*Si está abajo*/
		if(casillaSeleccionada.getY()==casillaActual.getY()+DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getX()==casillaActual.getX())
			return true;
				
		/*Si está arriba*/
		if(casillaSeleccionada.getY()==casillaActual.getY()-DatosGlobales.CASILLA_SIZE && casillaSeleccionada.getX()==casillaActual.getX())
			return true;
		
		return false;
	}
	public String obtener_direccion(Casilla casillaAdyacente, Casilla casillaOrigen) {
		
		if(casillaAdyacente.getX()==casillaOrigen.getX()+DatosGlobales.CASILLA_SIZE && casillaAdyacente.getY()==casillaOrigen.getY()) {
			System.out.println("derecha");
			return "DERECHA";
		}
		
		/*Si está a su izquierda*/
		if(casillaAdyacente.getX()==casillaOrigen.getX()-DatosGlobales.CASILLA_SIZE && casillaAdyacente.getY()==casillaOrigen.getY()) {
			System.out.println("izquierdas");
			return "IZQUIERDA";}
		
		/*Si está abajo*/
		if(casillaAdyacente.getY()==casillaOrigen.getY()+DatosGlobales.CASILLA_SIZE && casillaAdyacente.getX()==casillaOrigen.getX()) {
			System.out.println("ABA");
			return "ABAJO";
		}
				
		/*Si está arriba*/
		if(casillaAdyacente.getY()==casillaOrigen.getY()-DatosGlobales.CASILLA_SIZE && casillaAdyacente.getX()==casillaOrigen.getX()) {
			System.out.println("ARRIBA");

			return "ARRIBA";}
		
		return "";
		
	}
}
