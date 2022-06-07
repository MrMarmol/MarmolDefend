package procesos.habilidades.habilidadesImpl;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;
import procesos.habilidades.HabilidadDiana;
import procesos.habilidades.animacion.AnimacionLanzamiento;
import procesos.lienzo.Lienzo;

/** Subclase implementada que hereda de HabilidadDiana. La unidad objetivo cura la totalidad de sus pg*/
public class HCuracion extends HabilidadDiana{
	
	private final int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_CURAR;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_CURAR;

	public HCuracion(String nombre, String descripcion) {
		super(nombre, descripcion);
	}
	
	@Override
	public boolean comprobar_habilidad_posible(Casilla casillaOrigen, Casilla casillaObjetivo) {
		area = obtener_area(casillaOrigen, DISTANCIA);
		return contacto(casillaObjetivo.getX(),casillaObjetivo.getY(),DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE);	}

	@Override
	public void aplicar_efecto() {
		casillaObjetivo.getUnidad().setPg(casillaObjetivo.getUnidad().getPgTotales());
		
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, Casilla casillaDestino, String direccion) {
		this.casillaObjetivo = casillaDestino;
		this.animacion = new AnimacionLanzamiento(casillaOrigen, casillaDestino, direccion, 0);
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"cura.png");	
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
