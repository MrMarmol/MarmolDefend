package procesos.habilidades.habilidadesImpl;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;
import procesos.habilidades.HabilidadDiana;
import procesos.habilidades.animacion.AnimacionLanzamiento;
import procesos.lienzo.Lienzo;

/** Subclase implementada que hereda de HabilidadDiana. La unidad se cura un porcentaje de la vida de la unidad objetivo, y luego se lo infrinje como damage*/
public class HAbsorcion extends HabilidadDiana{

	private final int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_ABSORCION;
	private final int VELOCIDAD_ANIMACION = 2;
	private final int PORCION_ABSORCION = DatosGlobales.PORCION_ABSORCION;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_ABSORCION;

	private int pg_absorbidos;
	
	public HAbsorcion(String nombre, String descripcion) {
		super(nombre, descripcion);
	}
	@Override
	public boolean comprobar_habilidad_posible(Casilla casillaOrigen, Casilla casillaObjetivo) {
		area = obtener_area(casillaOrigen, DISTANCIA);
		return contacto(casillaObjetivo.getX(),casillaObjetivo.getY(),DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE);	}

	@Override
	public void aplicar_efecto() {
		casillaOrigen.getUnidad().curar(pg_absorbidos);
		casillaObjetivo.getUnidad().recibir_damage(pg_absorbidos);		
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, Casilla casillaDestino, String direccion) {
		this.casillaOrigen = casillaOrigen;
		this.casillaObjetivo = casillaDestino;
		this.animacion = new AnimacionLanzamiento(casillaOrigen, casillaDestino, direccion,VELOCIDAD_ANIMACION);
		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"absorcion.png");
		pg_absorbidos = casillaObjetivo.getUnidad().getPg()/PORCION_ABSORCION; 
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
