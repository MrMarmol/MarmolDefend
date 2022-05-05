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



public class DatosGlobales {
	
	public static final String DERECHA = "DERECHA";
	public static final String IZQUIERDA = "IZQUIERDA";
	public static final String ABAJO = "ABAJO";
	public static final String ARRIBA = "ARRIBA";
	public static final int CASILLA_SIZE = 32;
	
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
			 edificios = new ArrayList<Edificio>(); edificios.add(new Castillo(null,0,0,"Castillo"));
			 edificios.add(new Aserradero(null,0,0,"Aserradero"));
			 
			 humanos = new ArrayList<Unidad>();
			 demonios = new ArrayList<Unidad>();

			 for(Unidad unidad : unidades)
				 if(unidad.getBando().equals("Humanidad"))
					 humanos.add(unidad);
				 else 
					 demonios.add(unidad);			 			 
			 datos_cargados = true;
		 }
		 //cargado de flecha new Image("archivos/imagenes/iconos/icono.png"
		 
	 }
	 

}
