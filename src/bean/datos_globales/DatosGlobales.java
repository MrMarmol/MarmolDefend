package bean.datos_globales;

import java.util.ArrayList;
import java.util.HashMap;

import bean.edificios.Aserradero;
import bean.edificios.Castillo;
import bean.edificios.Edificio;
import bean.idioma.Idioma;
import bean.unidades.Unidad;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import bean.rutas.Rutas;
import procesos.animacion.Animacion;
import procesos.incializacion.LecturaBestiario;


/*Clase que contiene las constantes usadas en un ámbito global*/
public class DatosGlobales {
	
	/**/
	public static final String TITULO = "MARMOL DEFEND";

	public static final String DERECHA = "DERECHA";
	public static final String IZQUIERDA = "IZQUIERDA";
	public static final String ABAJO = "ABAJO";
	public static final String ARRIBA = "ARRIBA";
	
	public static final String ANDAR = "ANDAR";
	public static final String CORRER = "CORRER";
	public static final int PASOS_ANDAR = 2;
	public static final int PASOS_CORRER = 4;
	
	public static final String MADERA = "MADERA";
	public static final String METAL = "METAL";
	public static final String ORO = "ORO";
	public static final String COMIDA = "COMIDA";

	
	public static final int CASILLA_SIZE = 32;
	
	public static final double MENU_PRINCIPAL_WIDTH = 1171;
	public static final double MENU_PRINCIPAL_HEIGHT = 757;
	
	public static Rutas rutas;
	public static Idioma idioma;
	public static ArrayList<Unidad> unidades;
	public static ArrayList<Unidad> humanos;
	public static ArrayList<Unidad> demonios;
	public static ArrayList<Edificio> edificios;
	private static boolean datos_cargados;	
	public static Animacion anm;
	 
	 
	 public static void inicializar() {		 
		 
		 if(!datos_cargados) {
			 rutas = new Rutas();
			 idioma = new Idioma();
			 unidades = new LecturaBestiario().lectura();
			 
			 humanos = new ArrayList<Unidad>();
			 demonios = new ArrayList<Unidad>();
			 edificios = new ArrayList<Edificio>();
			 Edificio edificio = new Edificio(new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+"castillo.png"),"castillo");
			 edificios.add(edificio);

			 for(Unidad unidad : unidades)
				 if(unidad.getBando().equals("Humanidad"))
					 humanos.add(unidad);
				 else 
					 demonios.add(unidad);	

			 datos_cargados = true;
		 } 
	 }	 
}
