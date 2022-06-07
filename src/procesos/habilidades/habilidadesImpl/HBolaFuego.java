package procesos.habilidades.habilidadesImpl;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import bean.unidades.unidades.UnidadCombate;
import javafx.scene.image.Image;
import procesos.habilidades.HabilidadDiana;
import procesos.habilidades.animacion.AnimacionLanzamiento;
import procesos.lienzo.Lienzo;

/** Subclase implementada que hereda de HabilidadDiana. Realiza un ataque común con el damage incrementado*/
public class HBolaFuego extends HabilidadDiana {
		
	private final int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_BOLA_FUEGO;
	private final int VELOCIDAD_ANIMACION = 4;
	private final double BONUS = DatosGlobales.BONUS_HABILIDAD_BOLA_FUEGO;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_BOLA_FUEGO;
	private int bonus_at;

	public HBolaFuego(String nombre, String descripcion) {
		super(nombre, descripcion);
	}
	@Override
	public boolean comprobar_habilidad_posible(Casilla casillaOrigen, Casilla casillaObjetivo) {
		area = obtener_area(casillaOrigen, DISTANCIA);
		return contacto(casillaObjetivo.getX(),casillaObjetivo.getY(),DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE);			
	}

	@Override
	public void aplicar_efecto() {
		UnidadCombate unidad_atacante = new UnidadCombate(casillaOrigen.getUnidad());
		unidad_atacante.atacar(casillaObjetivo.getUnidad(),bonus_at);
		
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, Casilla casillaDestino, String direccion) {
		this.casillaOrigen = casillaOrigen;
		this.casillaObjetivo = casillaDestino;
		this.animacion = new AnimacionLanzamiento(casillaOrigen, casillaDestino, direccion,VELOCIDAD_ANIMACION);
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"bola_fuego.png");				
		bonus_at = (int)(Math.round(casillaOrigen.getUnidad().getFullAt()*BONUS));
		aplicar_coste_habilidad(casillaOrigen.getUnidad());
	
	}
	@Override
	public int getMana() {
		return COSTE;
	}
	@Override
	public void aplicar_coste_habilidad(Unidad unidad) {
		unidad.reducir_mana(COSTE);
	}

}
