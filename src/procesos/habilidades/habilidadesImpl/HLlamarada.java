package procesos.habilidades.habilidadesImpl;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import bean.unidades.unidades.UnidadCombate;
import javafx.scene.image.Image;
import procesos.habilidades.HabilidadLineal;
import procesos.habilidades.animacion.AnimacionArea;
import procesos.habilidades.animacion.AnimacionLanzamiento;

/** Subclase implementada que hereda de HabilidadDiana. Realiza un ataque común con el damage incrementado a cada casilla de la línea*/
public class HLlamarada extends HabilidadLineal{
	
	private static final int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_LLAMARADA;
	private final double BONUS = DatosGlobales.BONUS_HABILIDAD_LLAMARADA;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_LLAMARADA;

	private int bonus_at;

	@Override
	public void aplicar_efecto() {
		UnidadCombate unidad_atacante = new UnidadCombate(casillaOrigen.getUnidad());
		for(Casilla casilla : casillas_afectadas) 
			if(casilla.getUnidad()!=null)
				unidad_atacante.atacar(casilla.getUnidad(), bonus_at);
	}
	public HLlamarada(String nombre, String descripcion) {
		super(nombre, descripcion);
	}
	@Override
	public int getDistancia() {		
		return DISTANCIA;
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, ArrayList<Casilla> casillas_adyacentes) {
		this.casillaOrigen = casillaOrigen;
		this.casillas_afectadas = casillas_adyacentes;
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"llamarada.png");		
		this.animacion = new AnimacionArea(imagen,DatosGlobales.SIZE_SPRITE_HABILIDAD_AREA, DatosGlobales.NUM_SPRITES_HABILIDAD_AREA,DatosGlobales.DURACION_HABILIDAD_AREA);
		bonus_at = (int)(Math.round(casillaOrigen.getUnidad().getFullAt()*BONUS));
		System.out.println(bonus_at+"d");
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
