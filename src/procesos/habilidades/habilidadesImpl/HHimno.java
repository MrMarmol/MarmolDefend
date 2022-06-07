package procesos.habilidades.habilidadesImpl;

import java.util.ArrayList;
import java.util.Collection;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import procesos.habilidades.HabilidadArea;
import procesos.habilidades.animacion.AnimacionArea;
import procesos.habilidades.pasividad.InHabilidadPasiva;
import procesos.lienzo.Lienzo;

/** Habilidad implementada que extiende de HabilidadArea. Incrementa la def y el at de las unidades presentes en las casillas afectadas*/
/*Ojo: el bonus que aporta esta habilidad a las undiades depdende de sus propias estadísticas. A más bufos tenga esta, más tendrán las unidades afectadas*/
public class HHimno extends HabilidadArea  implements InHabilidadPasiva{

	//Crear dos clases abstractas HabilidadAreaPasiva y HabilidadLinealPasiva solo para poder heredar un atributo que en ocasiones no es necesario simplemente es una gilipollez
	private ArrayList<Unidad> unidades_afectadas;
	private static final int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_MANANTIAL;
	private final double BONUS =DatosGlobales.BONUS_HABILIDAD_HIMNO;
	private int bonus_at;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_HIMNO;
	private int bonus_def;
	
	public HHimno(String nombre, String descripcion) {
		super(nombre, descripcion,true);
	}
	@Override
	public void aplicar_efecto() {
		
		for(Casilla casilla : casillas_afectadas) 
			if(casilla.getUnidad()!=null && casilla.getUnidad().getBando().equals(casillaOrigen.getUnidad().getBando())) {
				casilla.getUnidad().aumentar_at(bonus_at);
				casilla.getUnidad().aumentar_def(bonus_def);
				unidades_afectadas.add(casilla.getUnidad());
				}							
	}
	@Override
	public void quitar_efecto() {
		
		for(Unidad unidad : unidades_afectadas) {
			unidad.reducir_at(bonus_at);
			unidad.reducir_def(bonus_def);
		
		}
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, Collection<Casilla> casillas_tablero) {
		casillas_afectadas = new ArrayList<Casilla>();
		unidades_afectadas = new ArrayList<Unidad>();
		this.casillaOrigen = casillaOrigen;
		this.area = obtener_area(casillaOrigen, DISTANCIA);		
		obtener_casillas_afectadas(casillas_tablero);
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"himno.png");		
		this.animacion = new AnimacionArea(imagen,DatosGlobales.SIZE_SPRITE_HABILIDAD_AREA, DatosGlobales.NUM_SPRITES_HABILIDAD_AREA, DatosGlobales.DURACION_HABILIDAD_AREA);	
		bonus_at = (int)(Math.round(casillaOrigen.getUnidad().getFullAt()*BONUS));
		bonus_def = (int)(Math.round(casillaOrigen.getUnidad().getFullDef()*BONUS));
		if(bonus_at==0)
			bonus_at=1;
		if(bonus_def==0)
			bonus_def=1;
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