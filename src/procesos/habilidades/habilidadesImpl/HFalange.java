package procesos.habilidades.habilidadesImpl;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;
import procesos.habilidades.HabilidadLineal;
import procesos.habilidades.animacion.AnimacionArea;
import procesos.habilidades.pasividad.InHabilidadPasiva;
import procesos.lienzo.Lienzo;

/**Subclase que hereda de HabilidadLineal. Las casillas cuyas unidades pertenezcan a la misma clase incrementarán su defensa*/
public class HFalange extends HabilidadLineal implements InHabilidadPasiva {

	private static final int DISTANCIA = DatosGlobales.DISTANCIA_HABILIDAD_FALANGE;
	private final double BONUS = DatosGlobales.BONUS_HABILIDAD_FALANGE;
	private int bonus;
	private final int COSTE = DatosGlobales.COSTE_HABILIDAD_FALANGE;
	protected AnimacionArea animacion;

	public HFalange(String nombre, String descripcion) {
		super(nombre, descripcion);
	}
	@Override
	public void aplicar_efecto() {
		
		for(Casilla casilla : casillas_afectadas) {
				casilla.getUnidad().aumentar_def(bonus);
				casilla.getUnidad().setMov(0);
		}
	}
	@Override
	public void quitar_efecto() {
		for(Casilla unidad : casillas_afectadas)
				unidad.getUnidad().reducir_def(bonus);		
	}
	
	@Override
	public int getDistancia() {
		return DISTANCIA;
	}
	@Override
	public void establecer_valores(Casilla casillaOrigen, ArrayList<Casilla> casillas_adyacentes) {
		this.casillaOrigen = casillaOrigen;
		this.casillas_afectadas = new ArrayList<Casilla>();
		
		for(Casilla casilla : casillas_adyacentes) 
			if(casilla.getUnidad()!=null 
			&& casilla.getUnidad().getNombre().equals(casillaOrigen.getUnidad().getNombre()) 
			&& casilla.getUnidad().getBando().equals(casillaOrigen.getUnidad().getBando()))								
				this.casillas_afectadas.add(casilla);
		casillas_afectadas.add(casillaOrigen);

		this.imagen = new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"falange.png");		
		this.animacion = null; //Animación estática.
		bonus = (int)(Math.round((casillaOrigen.getUnidad().getFullDef()*BONUS)));
		if(bonus==0)
			bonus=1;
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
