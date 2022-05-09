package procesos.acciones.unidades.mover;

import java.util.ArrayList;
import java.util.HashMap;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.Unidad;

public class ControladorMovimiento {
	
	//Puede haber varias unidades corriendo simultaneamente
	private ArrayList<AccionMoverCorrer> acciones_correr;
	//Solo se puede controlar una unidad con el teclado
	private AccionMoverAndar accion_andar;
	
	public ControladorMovimiento() {
		super();
		acciones_correr = new ArrayList<AccionMoverCorrer>();
		accion_andar = null;
	}
	public ArrayList<AccionMoverCorrer> getMovimientos(){
		return acciones_correr;
	}
	public boolean comprobar_movimiento_posible(Casilla casillaOrigen, Casilla casillaDestino) {
		return casillaDestino!=null && casillaDestino.getEdificio()==null && casillaDestino.getUnidad()==null? true : false;
	}	
	//Comprueba que se pueda crear la accion andar
	public boolean comprobar_accion_andar() {
		return accion_andar ==null ? true : false; 
	}
	
	//Comprueba qué movimientos han terminado y los borra
	public void actualizar_acciones() {
		if(accion_andar !=null)
			if(accion_andar.comprobar_trayecto_finalizado())
				accion_andar = null;
			
		ArrayList<AccionMoverCorrer> movimientos_terminados = new ArrayList<AccionMoverCorrer>();
		
		for(AccionMoverCorrer movimiento :acciones_correr) {
			if(movimiento.comprobar_trayecto_finalizado()) {				
					movimiento.sumar_casilla();
					if(movimiento.getPosicionActual()==movimiento.getDirecciones().size()) 
						movimientos_terminados.add(movimiento);						
					else 
						movimiento.avanzar_casilla();						
				}
			}		
		for(AccionMoverCorrer movimiento_terminado: movimientos_terminados) 
			for(AccionMoverCorrer movimiento: acciones_correr)
				if(movimiento.equals(movimiento_terminado)) {
					eliminar_movimiento_correr(movimiento);
					break;
				}
	}
	 public void crear_accion_andar(Casilla casillaOrigen, Casilla casillaDestino, String direccion, int paso) {		    	
			if(accion_andar==null) 	    		
		    	accion_andar = new AccionMoverAndar(casillaOrigen, casillaDestino,direccion,paso);		    				    
	 }
	 
	 public void crear_accion_correr(Casilla casillaOrigen, int paso, ArrayList<String> direcciones, ArrayList<Casilla> camino) {
		 
		Unidad unidad = casillaOrigen.getUnidad();	    	
		if(unidad!=null ) { 
				 AccionMoverCorrer movimiento = new AccionMoverCorrer(casillaOrigen, camino.get(1), direcciones.get(0),paso, direcciones, camino);
				 agregar_movimiento_correr(movimiento);
				 casillaOrigen.setUnidad(null);
		 }	
	 }

	 public void mover() {
		 for(AccionMover movimiento : acciones_correr)
			 movimiento.mover();
		 if(accion_andar !=null) 
			 accion_andar.mover();
	 }
			
	public void agregar_movimiento_correr(AccionMoverCorrer movimiento) {
		acciones_correr.add(movimiento);
	}
	public void eliminar_movimiento_correr(AccionMoverCorrer movimiento_terminado) {
		acciones_correr.remove(movimiento_terminado);
	}
	public AccionMover getAccionAndar() {
		return accion_andar;
	}

}
