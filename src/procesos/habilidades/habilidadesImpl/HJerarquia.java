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
import procesos.habilidades.pasividad.InHabilidadPasiva;

/**Subclase que extiende de HabilidadArea. Cada unidad incrementará en el área incrementará su ataque*/
public class HJerarquia extends HabilidadArea  implements InHabilidadPasiva{

	private ArrayList<Unidad> unidades_afectadas;
	private static final int DISTANCIA= DatosGlobales.DISTANCIA_HABILIDAD_JERARQUIA;
	private final double BONUS = 0.1;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_JERARQUIA;

	private int bonus_at;
	
	public HJerarquia(String nombre, String descripcion) {
		super(nombre, descripcion,true);
	}
	
	@Override
	public void aplicar_efecto() {
		for(Casilla casilla : casillas_afectadas) 
			if(casilla.getUnidad()!=null && casilla.getUnidad().getBando().equals(casillaOrigen.getUnidad().getBando())) {
				bonus_at = (int)(Math.round(casilla.getUnidad().getAt()*BONUS));
				if(bonus_at==0)
					bonus_at=1;
				casilla.getUnidad().aumentar_at(bonus_at);
				unidades_afectadas.add(casilla.getUnidad());				
			}				
	}

	@Override
	public void quitar_efecto() {
		for(Unidad unidad : unidades_afectadas) 
				unidad.reducir_at(bonus_at);
	}
	
	@Override
	public void establecer_valores(Casilla casillaOrigen, Collection<Casilla> casillas_tablero) {
		//Si no se reinician los arrays, el area sería la misma que habilidad lanzada anteriormente.
		casillas_afectadas = new ArrayList<Casilla>();
		unidades_afectadas = new ArrayList<Unidad>();
		this.casillaOrigen = casillaOrigen;
		this.area = obtener_area(casillaOrigen, DISTANCIA);		
		obtener_casillas_afectadas(casillas_tablero);
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"jerarquia.png");		
		this.animacion = new AnimacionArea(imagen,DatosGlobales.SIZE_SPRITE_HABILIDAD_AREA, DatosGlobales.NUM_SPRITES_HABILIDAD_AREA, DatosGlobales.DURACION_HABILIDAD_AREA);	
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
