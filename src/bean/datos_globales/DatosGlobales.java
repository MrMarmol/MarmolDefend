package bean.datos_globales;

import java.util.ArrayList;
import java.util.HashMap;
import bean.edificios.Edificio;
import bean.edificios.EdificioCombate;
import bean.idioma.Idioma;
import bean.rutas.Rutas;
import bean.unidades.sprites.SpritesUnidad;
import bean.unidades.unidades.Unidad;
import procesos.habilidades.Habilidad;
import procesos.habilidades.habilidadesImpl.*;
import procesos.lectorXml.LecturaBestiario;


/*Clase que contiene las constantes usadas en un ámbito global*/
public class DatosGlobales {
	
	
	private static LecturaBestiario lector;
	
	
	public static final String TITULO = "MARMOL DEFEND";
	public static final int PUERTO = 9884;
	
	public static final String DERECHA = "DERECHA";
	public static final String IZQUIERDA = "IZQUIERDA";
	public static final String ABAJO = "ABAJO";
	public static final String ARRIBA = "ARRIBA";
	
	public static final String ARRIBA_DERECHA = "ARRIBA_DERECHA";
	public static final String ARRIBA_IZQUIERDA = "ARRIBA_IZQUIERDA";
	public static final String ABAJO_DERECHA = "ABAJO_DERECHA";
	public static final String ABAJO_IZQUIERDA = "ABAJO_IZQUIERDA";

	
	public static final String ANDAR = "ANDAR";
	public static final String CORRER = "CORRER";
	public static final int PASOS_ANDAR = 2;
	public static final int PASOS_CORRER = 4;
	
	public static final String MADERA = "MADERA";
	public static final String METAL = "METAL";
	public static final String ORO = "ORO";
	public static final String COMIDA = "COMIDA";

	
	public static final int CASILLA_SIZE = 32;
	public static final int COLUMNAS_BOTONES_PRODUCCION = 5;
	public static final int FILAS_BOTONES_PRODUCCION = 2;

	public static final double MENU_PRINCIPAL_WIDTH = 1171;
	public static final double MENU_PRINCIPAL_HEIGHT = 757;
	
	public static final int DISTANCIA_HABILIDAD_EMPALAR = 100;
	public static final int DISTANCIA_HABILIDAD_ARCO = 6;
	public static final int DISTANCIA_HABILIDAD_MANANTIAL = 4;
	public static final int DISTANCIA_HABILIDAD_BOLA_FUEGO = 6;
	public static final int DISTANCIA_HABILIDAD_ABSORCION = 2;
	public static final int DISTANCIA_HABILIDAD_CURAR = 1;
	public static final int DISTANCIA_HABILIDAD_LLAMARADA = 4;
	public static final int DISTANCIA_HABILIDAD_JERARQUIA = 8;
	public static final int DISTANCIA_HABILIDAD_FALANGE = 3;
	
	public static final int SIZE_SPRITE_HABILIDAD_AREA = 32;
	public static final int NUM_SPRITES_HABILIDAD_AREA = 3;
	public static final int DURACION_HABILIDAD_AREA = 100;
	
	public static final int PORCION_ABSORCION = 3;
	public static final int CURACION_HABILIDAD_MANANTIAL = 2;
	public static final double BONUS_HABILIDAD_ERUPCION = 0.4;
	public static final double BONUS_HABILIDAD_HIMNO = 0.1;
	public static final double BONUS_HABILIDAD_ARCO = 0.5;
	public static final double BONUS_HABILIDAD_BOLA_FUEGO = 0.5;
	public static final double BONUS_HABILIDAD_LLAMARADA = 2;
	public static final double BONUS_HABILIDAD_FALANGE = 0.3;
	
	public static final int COSTE_HABILIDAD_EMPALAR = 2;
	public static final int COSTE_HABILIDAD_ARCO = 6;
	public static final int COSTE_HABILIDAD_MANANTIAL = 4;
	public static final int COSTE_HABILIDAD_BOLA_FUEGO = 6;
	public static final int COSTE_HABILIDAD_ABSORCION = 2;
	public static final int COSTE_HABILIDAD_CURAR = 1;
	public static final int COSTE_HABILIDAD_LLAMARADA = 4;
	public static final int COSTE_HABILIDAD_JERARQUIA = 8;
	public static final int COSTE_HABILIDAD_FALANGE = 3;
	public static final int COSTE_HABILIDAD_ERUPCION = 3;
	public static final int COSTE_HABILIDAD_HIMNO = 1;
	
	public static final double ANCHO_BOTONES_HABILIDAD = 100;
	
	public static Rutas rutas;
	public static Idioma idioma;
	public static HashMap<String,Unidad> unidades;
	public static ArrayList<Unidad> humanos;
	public static ArrayList<Unidad> demonios;
	public static ArrayList<Edificio> edificios;
	private static HashMap<String,String> descripcion_habilidades;
	private static boolean datos_cargados;	
	public static SpritesUnidad anm;
	
	public final static String ACCION_ATACAR = "ATACAR";
	public final static String ACCION_CONSTRUIR = "CONSTRUIR";
	public final static String ACCION_ALOJAR = "ALOJAR";
	public final static String ACCION_DESALOJAR = "DESALOJAR";
	public final static String ACCION_PRODUCIR = "PRODUCIR";
	public final static String ACCION_ANDAR = "ANDAR";
	public final static String ACCION_CORRER = "CORRER";
	public final static String ACCION_HABILIDAD = "HABILIDAD";
	public final static String ACCION_TURNO_TERMINADO = "TURNO_TERMINADO";

	
	 
	 
	 public static void inicializar() {		 
		 
		 lector = new LecturaBestiario();
		 
			 rutas = new Rutas();
			 idioma = new Idioma();
			 System.out.println(idioma);
			 descripcion_habilidades = lector.lectura_descripcion_habilidades();
			 unidades = lector.lectura_unidades();
			 edificios = lector.lectura_edificios();
			 
			 datos_cargados = true;			 			
		 
	 }
	 public static ArrayList<Habilidad> obtener_habilidades(ArrayList<String> habilidades_string){
		 
		 ArrayList<Habilidad> habilidades = new ArrayList<Habilidad>();
		 
		 if(habilidades_string!=null && !habilidades_string.isEmpty())
		 for(String habilidad_string : habilidades_string)
			 switch(habilidad_string) {
			 case "Falange":
				 habilidades.add(new HFalange(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Absorción":
				 habilidades.add(new HAbsorcion(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Arco":
				 habilidades.add(new HArco(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Bola de fuego":
				 habilidades.add(new HBolaFuego(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Curación":
				 habilidades.add(new HCuracion(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Empalar":
				 habilidades.add(new HEmpalar(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Erupción":
				 habilidades.add(new HErupcion(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Himno":
				 habilidades.add(new HHimno(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Jerarquía":
				 habilidades.add(new HJerarquia(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Llamarada":
				 habilidades.add(new HLlamarada(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			 case "Manantial":
				 habilidades.add(new HManantial(habilidad_string,descripcion_habilidades.get(habilidad_string)));
				 break;
			default : break;
			 }
		 return habilidades;
	 }
}
