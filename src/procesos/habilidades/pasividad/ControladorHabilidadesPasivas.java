package procesos.habilidades.pasividad;

import java.util.ArrayList;

import procesos.habilidades.Habilidad;

public class ControladorHabilidadesPasivas {

	//Controla las habilidades pasivas presentes en la partida
	private ArrayList<InHabilidadPasiva> habilidades;
	
	public ArrayList<InHabilidadPasiva> getAnimaciones() {
		return habilidades;		
	}


	public void quitar_efectos(InHabilidadPasiva habilidad) {
		for(InHabilidadPasiva habilidad_pasiva : habilidades) 
			if(habilidad_pasiva.equals(habilidad)) {
				habilidad_pasiva.quitar_efecto();	
				habilidades.remove(habilidad_pasiva);
				break;		
			}
	}
}
