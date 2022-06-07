package bean.tablero;

import java.util.ArrayList;
import java.util.HashMap;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;

public class Tablero {
	
	private HashMap<Key,Casilla> casillas;

	public Tablero() {
		casillas = new HashMap<Key,Casilla>();
	}
	
	public ArrayList<Unidad> getUnidades(){
		ArrayList<Unidad> unidades = new ArrayList<Unidad>();
		for(Casilla casilla : casillas.values())
			if(casilla.getUnidad()!=null)
				unidades.add(casilla.getUnidad());
		return unidades;
	}
	
	public HashMap<Key,Casilla> getCasillas() {
		return casillas;
	}

	
	public Casilla get_casilla(double x, double y) {
		for(Key clave : casillas.keySet()) 
			if(clave.getX() == x && clave.getY()==y)
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
		
		/*Si está a su izquierda en diagonal*/
		if(casillaSeleccionada.getX()+DatosGlobales.CASILLA_SIZE==casillaActual.getX() && 
				(casillaSeleccionada.getY()+DatosGlobales.CASILLA_SIZE==casillaActual.getY() ||
				casillaSeleccionada.getY()-DatosGlobales.CASILLA_SIZE==casillaActual.getY())) 
			return true;
		/*Si está a su derecha en diagonal)*/
		if(casillaSeleccionada.getX()-DatosGlobales.CASILLA_SIZE==casillaActual.getX() && 
				(casillaSeleccionada.getY()+DatosGlobales.CASILLA_SIZE==casillaActual.getY() ||
				casillaSeleccionada.getY()-DatosGlobales.CASILLA_SIZE==casillaActual.getY())) 
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
	public String obtener_direccion_adyacente(Casilla casillaAdyacente, Casilla casillaOrigen) {
		
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
	
	public String obtener_direccion(Casilla casillaDestino, Casilla casillaOrigen) {
		
		if(casillaDestino.getX()==casillaOrigen.getX()) {	//Está en la misma línea X
			if(casillaDestino.getY()>casillaOrigen.getY())	//Está más abajo
				return "ABAJO";
			else return "ARRIBA";
		}
		if(casillaDestino.getY()==casillaOrigen.getY()) {	//Está en la misma línea X
			if(casillaDestino.getX()>casillaOrigen.getX())	//Está más abajo
				return "DERECHA";
			else return "IZQUIERDA";
		}
		if(casillaDestino.getX()>casillaOrigen.getX()) {
			if(casillaDestino.getY()>casillaOrigen.getY())
				return "ABAJO_DERECHA";
			else
				return "ARRIBA_DERECHA";				
		}
		if(casillaDestino.getX()<casillaOrigen.getX()) {
			if(casillaDestino.getY()>casillaOrigen.getY())
				return "ABAJO_IZQUIERDA";
			else
				return "ARRIBA_IZQUIERDA";				
		}
		return "";
		
	}
	public ArrayList<Casilla> obtener_linea_casillas_adyacentes(Casilla casillaOrigen, String direccion, int distancia){
		
		ArrayList<Casilla> linea_casillas = new ArrayList<Casilla>();
		switch(direccion) {
		case DatosGlobales.DERECHA :
			for(int i = 1; i<distancia;i++) 
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX()+DatosGlobales.CASILLA_SIZE*i, casillaOrigen.getY())));				
			return linea_casillas;
		case DatosGlobales.IZQUIERDA : 
			for(int i = 1; i<distancia;i++)
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX()-DatosGlobales.CASILLA_SIZE*i, casillaOrigen.getY())));
			return linea_casillas;
		case DatosGlobales.ARRIBA :  
			for(int i = 1; i<distancia;i++) 
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX(), casillaOrigen.getY()-DatosGlobales.CASILLA_SIZE*i)));
			return linea_casillas;
		case DatosGlobales.ABAJO :
			for(int i = 1; i<distancia;i++) 
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX(), casillaOrigen.getY()+DatosGlobales.CASILLA_SIZE*i)));
			return linea_casillas;			
		case DatosGlobales.ABAJO_DERECHA :
			for(int i = 1; i<distancia;i++)
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX()+DatosGlobales.CASILLA_SIZE*i, casillaOrigen.getY()+DatosGlobales.CASILLA_SIZE*i)));
			return linea_casillas;
		case DatosGlobales.ABAJO_IZQUIERDA :
			for(int i = 1; i<distancia;i++)
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX()-DatosGlobales.CASILLA_SIZE*i, casillaOrigen.getY()+DatosGlobales.CASILLA_SIZE*i)));
			return linea_casillas;
		case DatosGlobales.ARRIBA_DERECHA :
			for(int i = 1; i<distancia;i++)
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX()+DatosGlobales.CASILLA_SIZE*i, casillaOrigen.getY()-DatosGlobales.CASILLA_SIZE*i)));
			return linea_casillas;
		case DatosGlobales.ARRIBA_IZQUIERDA :
			for(int i = 1; i<distancia;i++)
				linea_casillas.add(casillas.get(new Key(casillaOrigen.getX()-DatosGlobales.CASILLA_SIZE*i, casillaOrigen.getY()-DatosGlobales.CASILLA_SIZE*i)));
			return linea_casillas;
			}

	return null;
	}
	public Casilla obtener_casilla(double x, double y) {
		Casilla casilla_adyacente;
		for(Casilla casilla : casillas.values()) {
    		System.out.println("Casilla tablero : "+casilla.getX()+"-"+casilla.getY());
			if(casilla.getX()<x && casilla.getY()<y) {
				casilla_adyacente = casillas.get(new Key(casilla.getX()+DatosGlobales.CASILLA_SIZE,casilla.getY()+DatosGlobales.CASILLA_SIZE));			
				if(casilla_adyacente.getX()>x && casilla_adyacente.getY()>y)
					return casilla;
			}		
		}
		return null;
	}
		
}
