package bean.edificios;

import java.util.ArrayList;

import bean.recurso.Recurso;
import javafx.scene.image.Image;

public class EdificioProduccion extends Edificio{

	private Recurso ganancia;

	public EdificioProduccion(Image imagen, String nombre, Image portada, int at, int def, int pg,int num_alojamientos, ArrayList<Recurso> coste,Recurso ganancia) {
		super(imagen, nombre, portada, at, def, pg, num_alojamientos, coste);
		this.ganancia = ganancia;
	}
	public EdificioProduccion(Edificio edificio) {
		super(edificio);
		this.ganancia = new Recurso(((EdificioProduccion)edificio).getGananciaNombre(),((EdificioProduccion)edificio).getGananciaCantidad());
	}

	public String getGananciaNombre() {
		return ganancia.getRecurso();
	}
	public Recurso getGanancia() {
		return new Recurso(ganancia.getRecurso(), ganancia.getCantidad()*unidades_alojadas.size());
	}
	public int getGananciaCantidad() {
		return ganancia.getCantidad();
	}
	
}
