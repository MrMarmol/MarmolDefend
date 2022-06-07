package bean.unidades.unidades;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.unidades.sprites.SpritesUnidad;
import javafx.scene.image.Image;
import procesos.habilidades.Habilidad;

public class UnidadProduccion extends Unidad{

	private ArrayList<Edificio> edificios;
	
	public UnidadProduccion(Unidad unidad, ArrayList<Edificio> edificios ) {
		super(unidad);
		this.edificios = edificios;
	}
	public UnidadProduccion(Unidad unidad) {
		super(unidad);
		this.edificios = DatosGlobales.edificios;
	}
	
	public ArrayList<Edificio> getEdificios() {
		return edificios;
	}
	

}
