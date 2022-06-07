package procesos.habilidades.habilidadesImpl;

import java.util.ArrayList;
import java.util.Collection;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.habilidades.HabilidadArea;
import procesos.habilidades.animacion.AnimacionArea;
import procesos.habilidades.animacion.AnimacionLanzamiento;

/** Habilidad implementada que extiende de HabilidadArea. Cura a las unidades presentes en las casillas afectadas*/
public class HManantial extends HabilidadArea{

	private final static int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_MANANTIAL;
	private final int CURACION = DatosGlobales.CURACION_HABILIDAD_MANANTIAL; 
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_MANANTIAL;

	
	@Override
	public void aplicar_efecto() {
		for(Casilla casilla : casillas_afectadas) 
			if(casilla.getUnidad()!=null)
				casilla.getUnidad().curar(CURACION);				
	}
	public HManantial(String nombre, String descripcion) {
		super(nombre, descripcion,false);
		this.casillas_afectadas = new ArrayList<Casilla>();
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, Collection<Casilla> casillas_tablero) {
		casillas_afectadas = new ArrayList<Casilla>();
		this.casillaOrigen = casillaOrigen;
		this.area = obtener_area(casillaOrigen, DISTANCIA);		
		obtener_casillas_afectadas(casillas_tablero);				
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"manantial.png");		
		this.animacion = new AnimacionArea(imagen,DatosGlobales.SIZE_SPRITE_HABILIDAD_AREA, DatosGlobales.NUM_SPRITES_HABILIDAD_AREA,DatosGlobales.DURACION_HABILIDAD_AREA);
		aplicar_coste_habilidad(casillaOrigen.getUnidad());
	}
	@Override
	public int getMana() {
		// TODO Auto-generated method stub
		return COSTE;
	}
	@Override
	public void aplicar_coste_habilidad(Unidad unidad) {
		unidad.reducir_mana(COSTE);
	}

}

