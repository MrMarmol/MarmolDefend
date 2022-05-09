package bean.unidades;

import java.util.ArrayList;

import bean.unidades.interfaces.InUnidadCombate;
import javafx.scene.image.Image;
import procesos.acciones.unidades.atacar.AccionAtaque;
import procesos.animacion.Animacion;

public class UnidadCombate extends Unidad implements InUnidadCombate {
	
	private AccionAtaque ataque;

	
	public UnidadCombate(String nombre, String descripcion, Image cara, Image portada, Animacion animacion,
			int at, int def, int pg, ArrayList<String> habilidades, String bando, AccionAtaque ataque) {
		super(nombre, descripcion, cara, portada, animacion, at, def, pg, habilidades, bando);	
		ataque = new AccionAtaque();
	}

	public UnidadCombate(Unidad unidad) {
		super(unidad.getNombre(),unidad.getDescripcion(),unidad.getCara(),unidad.getPortada(),unidad.getAnimacion(),
				unidad.getAt(),unidad.getDef(),unidad.getPg(),unidad.getHabilidades(),unidad.getBando());
		ataque = new AccionAtaque();
	}
	@Override
	public String atacar(Unidad unidad) {
		return ataque.atacar(this,unidad);		
	}

	@Override
	public void usar_habilidad() {
		// TODO Auto-generated method stub
		
	}


	

}
