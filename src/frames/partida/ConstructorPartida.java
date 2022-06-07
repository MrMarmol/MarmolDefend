package frames.partida;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import audio.Musico;
import bean.botones.BotonBestiario;
import bean.botones.BotonEdificio;
import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.mapas.DatosMapa;
import bean.tablero.Key;
import bean.tablero.Tablero;
import bean.tablero.casillas.Casilla;

import bean.unidades.unidades.Unidad;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import procesos.cargar.CargadoMapa;
import procesos.lienzo.Lienzo;
import javafx.scene.layout.Priority.*;


public class ConstructorPartida {
	
	
	//La altura que ocupará el marco inferior
	//El padding que mantendrán los nodos del marco
	private final double  HEIGHT_MARCO_INFERIOR = 150;
	private final double PADDING_MARCO_INFERIOR = 15;
	
	//La altura y paddings del marco superior
	private final double HEIGHT_BARRA_SUPERIOR = 35;
	private final double PADDING_LATERAL_BARRA_SUPERIOR = 23;
	private final double PADDING_SUPERIOR_BARRA_SUPERIOR = 5;
	
	//La distancia que separará cada columna cuando la tabla tenga que distanciar sus nodos
	private final int SEPARADOR_SECCIONES = 20;
	private final int SIZE_VENTANA_TERMINAR_TURNO = 250;
	
	//Anchura de los nodos de las subtabla	
	private final double ANCHO_HISTORIA = 300;
	private final double ANCHO_BOTONES_ACCIONES = 80;
	private final double ANCHO_BOTONES_HABILIDAD = DatosGlobales.ANCHO_BOTONES_HABILIDAD;
	private final double SIZE_BOTONES_PRODUCCION = 40;
	
	//JavaFX sufre un ictus cuando intenta averiguar el width de los gridpane. Hay que hacerlo manual.
	private final double ANCHO_TABLA_ACCIONES = ANCHO_HISTORIA+SEPARADOR_SECCIONES*2+ANCHO_BOTONES_ACCIONES+ANCHO_BOTONES_HABILIDAD;
	private final double ANCHO_TABLA_PRODUCCION = SIZE_BOTONES_PRODUCCION*DatosGlobales.COLUMNAS_BOTONES_PRODUCCION;
	private final double ANCHO_TABLA_ESTADISTICAS = 400;
	private final double ANCHO_SUBTABLAS_INFERIORES = ANCHO_TABLA_ACCIONES+ANCHO_TABLA_PRODUCCION*2+ANCHO_TABLA_ESTADISTICAS;
	
	//El hGap de la tabla inferior para que simule la propiedad justify.
	private double hGap_tabla_inferior;
	
	//Donde se situa la imagen de fondo del marco inferior respecto al eje Y
	private double imgMarcoInferiorY;
	//Coordenadas de la tabla padre de marco inferior
	private double tablaInferiorX;
	private double tablaInferiorY;
	
	//La altura del scroll
	private double height_scroll;
	
	//Elementos para la construcción de la vista
	private Stage ventana;
	private FXMLLoader loader;
	private AnchorPane root;
	private Scene scene;
	ControladorPartida cp;
	private Dimension screenSize;
	private String nombre_mapa;
	private CargadoMapa cm;
	private DatosMapa dm;
	private String bando;
	
	public ConstructorPartida(String nombre_mapa, String bando) {
		this.nombre_mapa = nombre_mapa;
		this.bando = bando;
		ventana = new Stage();
		try {
			inicializar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mostrar() {
    	//Musico.reproducir_musica("musPartida");
		ventana.initStyle( StageStyle.UNDECORATED );		
		ventana.getIcons().add(new Image(DatosGlobales.rutas.getProperty("imgRecursos")+"icono.png"));
		ventana.setTitle(DatosGlobales.TITULO);
		ventana.setFullScreen(true);
		ventana.setScene(scene);
		ventana.setMaximized(true);		
		ventana.show();		
	}
	
	private void inicializar() throws IOException {
		//Obtengo la resolución del monitor
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	//Cargo la vista
    	loader = new FXMLLoader(getClass().getResource("vista_partida.fxml")); 
		root = loader.load();
		scene = new Scene(root,screenSize.width,screenSize.height);
		//Obtengo el controlador de la vista
		cp = loader.<ControladorPartida>getController();
		
		
		//Obtengo el tamaño y la ubicación correcta de cada nodo 				
		height_scroll = screenSize.height - HEIGHT_MARCO_INFERIOR-HEIGHT_BARRA_SUPERIOR;
		imgMarcoInferiorY = HEIGHT_BARRA_SUPERIOR+height_scroll;	
		tablaInferiorY = HEIGHT_BARRA_SUPERIOR+height_scroll+PADDING_MARCO_INFERIOR;				
		tablaInferiorX = screenSize.width* 0.02;
		
		//Instancio las tablas
		cp.tabla_recursos = new GridPane();
    	cp.tabla_opciones = new GridPane();
    	cp.tabla_inferior = new GridPane();
    	cp.tabla_acciones = new GridPane();
    	cp.tabla_produccion = new GridPane();
    	cp.tabla_alojamiento = new GridPane();
    	cp.tabla_estadisticas = new GridPane();
    	
    	//Instancio los arrays
        cp.botones_acciones = new ArrayList<Button>();
        cp.botones_habilidades = new ArrayList<Button>();
        cp.botones_produccion =   new ArrayList<Button>();
        cp.botones_alojamiento = new ArrayList<Button>();
        cp.estadisticas = new ArrayList<Text>();
        
        //Agrego los nodos instanciados en la vista a los arrays
    	cp.botones_acciones.add(cp.btnAtacar);
    	cp.botones_acciones.add(cp.btnAlojar);
    	cp.botones_acciones.add(cp.btnCancelar);
    	
    	cp.botones_habilidades.add(cp.btnHabilidad1);
    	cp.botones_habilidades.add(cp.btnHabilidad2);
    	cp.botones_habilidades.add(cp.btnHabilidad3);
    	
    	cp.estadisticas.add(cp.vAt);
    	cp.estadisticas.add(cp.vDefensa);
    	cp.estadisticas.add(cp.vMovimiento);
    	cp.estadisticas.add(cp.vPg);
    	
    	//Agrego las tablas a la base
    	cp.base.getChildren().add(cp.tabla_recursos);
    	cp.base.getChildren().add(cp.tabla_opciones);
    	cp.base.getChildren().add(cp.tabla_inferior);

		//Construyo el Frame e inicializo el Controlador.
		construirFrame();
		cp.inicializar(dm.getCoordenadasCastilloHost(),dm.getCoordenadasCastilloCliente(), bando);


		
		
		
	}
	
	private void construirFrame() {
		cargar_datos_mapa(nombre_mapa);
		modelar_ventana_completa();
		modelar_ventana_terminar_turno();
		crear_lienzo();
		crear_tablero();
		acoplar_tablero();								
	}
	
	
	private void cargar_datos_mapa(String nombre_mapa) {
		cm = new CargadoMapa(nombre_mapa);
		try {
			dm = cm.cargar_mapa();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void modelar_ventana_completa() {		 
		 ajustar_size_panes();
		 ajustar_size_canvas();
		 crear_marco_superior();
		 crear_marco_inferior();
	    }
	private void ajustar_size_panes() {
		cp.base.setPrefSize(screenSize.width, screenSize.height);
		cp.scroll.setPrefSize(screenSize.width, height_scroll);
		cp.scroll.setLayoutY(HEIGHT_BARRA_SUPERIOR);
		cp.scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		cp.scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		cp.mapa.setPrefSize(dm.getAncho(), dm.getLargo());
	}
	private void ajustar_size_canvas() {
		cp.canvas.setWidth(dm.getAncho());
	 	cp.canvas.setHeight(dm.getLargo());
	}
	private void crear_marco_superior() {
		//La imagen de fondo del marco superior
		cp.imgMarco_superior.setImage(new Image(DatosGlobales.rutas.getProperty("imgMenu")+"marco_superior_partida.png"));
		cp.imgMarco_superior.setFitWidth(screenSize.width);
		cp.imgMarco_superior.setFitHeight(HEIGHT_BARRA_SUPERIOR);
		cp.imgMarco_superior.setLayoutX(0);
		cp.imgMarco_superior.setLayoutY(0);
		
		//Se coloca la tabla recursos
		cp.tabla_recursos.setHgap(SEPARADOR_SECCIONES);
		cp.tabla_recursos.setLayoutX(PADDING_LATERAL_BARRA_SUPERIOR);
		cp.tabla_recursos.setLayoutY(PADDING_SUPERIOR_BARRA_SUPERIOR);
		
		/*Se coloca la tabla opciones en la esquina superior derecha, obteniendo para ello el ancho de un boton 
		 *como referencia y desplazándose hacia la izquierda tantos botones como tenga la tabla */		
		double x = cp.btnCapital.getPrefWidth()*3;			
		cp.tabla_opciones.setLayoutX(screenSize.getWidth()-x-PADDING_LATERAL_BARRA_SUPERIOR);
		cp.tabla_opciones.setLayoutY(PADDING_SUPERIOR_BARRA_SUPERIOR);
		
		 rellenar_tabla_recursos();
		 rellenar_tabla_opciones();
		 cp.tabla_recursos.setPrefHeight(HEIGHT_BARRA_SUPERIOR);
			
}
	private void rellenar_tabla_recursos() {
		colocar_recursos(cp.tabla_recursos,"madera.png",cp.vMadera, "1000",0,0);
		colocar_recursos(cp.tabla_recursos,"oro.png",cp.vOro, "1000",2,0);
		colocar_recursos(cp.tabla_recursos,"metal.png",cp.vMetal, "1000",4,0);
		colocar_recursos(cp.tabla_recursos,"comida.png",cp.vComida, "1000",6,0);
	}
	private void rellenar_tabla_opciones() {
		cp.tabla_opciones.add(cp.btnPasarTurno, 0, 0);
		cp.tabla_opciones.add(cp.btnMenu, 1, 0);
		cp.tabla_opciones.add(cp.btnCapital, 2, 0);
	}
	private void colocar_recursos(GridPane tabla, String nombre_imagen, Text textoRecurso, String valorRecurso, int columna, int fila) {
		ImageView imgRecurso = new ImageView(new Image(DatosGlobales.rutas.getProperty("imgRecursos")+nombre_imagen));
		imgRecurso.setFitWidth(30);
		imgRecurso.setFitHeight(25);
		textoRecurso.setText(valorRecurso);
		tabla.add(imgRecurso, columna, fila);
		tabla.add(textoRecurso, columna+1, fila);
	}	

	private void crear_marco_inferior() {
		//Imagen de fondo
		cp.img_marco_inferior.setImage(new Image(DatosGlobales.rutas.getProperty("imgMenu")+"marco_partida.png"));
		cp.img_marco_inferior.setLayoutX(0);
		cp.img_marco_inferior.setLayoutY(imgMarcoInferiorY);
		cp.img_marco_inferior.setFitHeight(HEIGHT_MARCO_INFERIOR);
		cp.img_marco_inferior.setFitWidth(screenSize.width);		
		
		//La tabla inferior
		cp.tabla_inferior.setLayoutX(tablaInferiorX);
		cp.tabla_inferior.setLayoutY(tablaInferiorY);
		
		crear_tabla_acciones();
		crear_tabla_produccion();
		crear_tabla_alojamiento();
		crear_tabla_estadisticas();		

		/*La distancia de cada subtabla de la tabla inferior, su valor se establecerá en función 
		 * de la resolución de la pantalla, acercando o alejando sus subtablas*/
		hGap_tabla_inferior = (screenSize.getWidth()-ANCHO_SUBTABLAS_INFERIORES)/4 - PADDING_MARCO_INFERIOR*2;
		cp.tabla_inferior.setHgap(hGap_tabla_inferior);
	}
	
	private void crear_tabla_acciones() {
		cp.tabla_acciones.setHgap(SEPARADOR_SECCIONES);		
		cp.tabla_acciones.add(cp.tHistoria, 0, 0,1,4);
		
    	cp.tHistoria.setMaxHeight(HEIGHT_MARCO_INFERIOR-PADDING_MARCO_INFERIOR*2);
    	cp.tHistoria.setMinWidth(ANCHO_HISTORIA);
    	cp.tHistoria.selectPositionCaret(cp.tHistoria.getLength());
		    	
    	//Acciones
    	cp.tabla_acciones.add(cp.tAcciones, 1, 0);
    	cp.tabla_acciones.add(cp.btnAtacar, 1, 1);
    	cp.tabla_acciones.add(cp.btnAlojar, 1, 2);    	
    	cp.tabla_acciones.add(cp.btnCancelar,1,3);
    	for(Button boton : cp.botones_acciones) {
    		boton.setMinWidth(ANCHO_BOTONES_ACCIONES);
    		boton.setMaxWidth(ANCHO_BOTONES_ACCIONES);
    	}

    	//Habilidades
    	cp.tabla_acciones.add(cp.tHabilidades, 2, 0);
    	cp.tabla_acciones.add(cp.btnHabilidad1, 2, 1);
    	cp.tabla_acciones.add(cp.btnHabilidad2, 2, 2);
    	cp.tabla_acciones.add(cp.btnHabilidad3, 2, 3);
    	
    	int i = 1;
     	for(Button boton : cp.botones_habilidades) {
    		boton.setMinWidth(ANCHO_BOTONES_HABILIDAD);
    		boton.setText("Habilidad "+i); i++;
     	}    	
     	
    	cp.tabla_inferior.add(cp.tabla_acciones, 0, 0);
	}
	private void crear_tabla_produccion() {
		cp.tabla_produccion.add(new Text("   PRODUCCION"), 0,0, DatosGlobales.COLUMNAS_BOTONES_PRODUCCION,1);
    	
    	for(int e = 0; e<DatosGlobales.FILAS_BOTONES_PRODUCCION;e++)	
    		for(int i = 0; i< DatosGlobales.COLUMNAS_BOTONES_PRODUCCION; i++) {
    			Button boton = new Button();
    			boton.setPrefSize(SIZE_BOTONES_PRODUCCION,SIZE_BOTONES_PRODUCCION);
    			cp.botones_produccion.add(boton);
    			cp.tabla_produccion.add(boton, i, e+1);
    		}
    	cp.tabla_inferior.add(cp.tabla_produccion, 1, 0);
	}
	private void crear_tabla_alojamiento() {
		cp.tabla_alojamiento.add(new Text("   ALOJAMIENTO"), 0,0, DatosGlobales.COLUMNAS_BOTONES_PRODUCCION,1);
    	
    	for(int e = 0; e<DatosGlobales.FILAS_BOTONES_PRODUCCION;e++)	
    		for(int i = 0; i< DatosGlobales.COLUMNAS_BOTONES_PRODUCCION; i++) {
    			Button boton = new Button();
    			boton.setMinSize(SIZE_BOTONES_PRODUCCION,SIZE_BOTONES_PRODUCCION);
    			cp.botones_alojamiento.add(boton);
    			cp.tabla_alojamiento.add(boton, i, e+1);
    		}
    	cp.tabla_inferior.add(cp.tabla_alojamiento, 2, 0);
	}
	
	private void crear_tabla_estadisticas() {
		cp.tabla_estadisticas = new GridPane();
		cp.tabla_estadisticas.setHgap(SEPARADOR_SECCIONES);
				
			//Imagen
			cp.imgCaraUnidad.setFitWidth(HEIGHT_MARCO_INFERIOR-PADDING_MARCO_INFERIOR*2);
			cp.imgCaraUnidad.setFitHeight(cp.imgCaraUnidad.getFitWidth());
			cp.tabla_estadisticas.add(cp.imgCaraUnidad, 0, 0,1,4);
			
			cp.tabla_estadisticas.add(new Text("Estaditicas"), 1,0, 4,1);
			cp.tabla_estadisticas.add(new Text("At:"), 1,1);
			cp.tabla_estadisticas.add(cp.vAt, 2,1);
    	
			cp.tabla_estadisticas.add(new Text("Def:"), 1,2);
			cp.tabla_estadisticas.add(cp.vDefensa, 2,2);
    	
			cp.tabla_estadisticas.add(new Text("Mov:"), 1,3);
			cp.tabla_estadisticas.add(cp.vMovimiento, 2,3);
    	
			cp.tabla_estadisticas.add(new Text("Mana:"), 3,1);
			cp.tabla_estadisticas.add(cp.vMana, 4,1);
    	
			cp.tabla_estadisticas.add(new Text("Pg:"), 3,2);
			cp.tabla_estadisticas.add(cp.vPg, 4,2);
    	
			cp.tabla_inferior.add(cp.tabla_estadisticas, 3, 0);		
	}
	
	private void modelar_ventana_terminar_turno() {
		cp.gMenuTerminarTurno.setLayoutX(screenSize.width/2-SIZE_VENTANA_TERMINAR_TURNO/2);
		cp.gMenuTerminarTurno.setLayoutY(screenSize.height/2-SIZE_VENTANA_TERMINAR_TURNO/2);
		
		cp.imgFondoPasarTurno.setImage(new Image(DatosGlobales.rutas.getProperty("imgMenu")+"marco.png"));
		cp.imgFondoPasarTurno.setFitWidth(SIZE_VENTANA_TERMINAR_TURNO);
		cp.imgFondoPasarTurno.setFitHeight(SIZE_VENTANA_TERMINAR_TURNO);

		cp.tTerminarTurno.setText("¿Terminar el turno?");
		cp.tTerminarTurno.setLayoutX(SIZE_VENTANA_TERMINAR_TURNO/2-cp.tTerminarTurno.getText().length()*3);
		cp.tTerminarTurno.setLayoutY(20);
		cp.btnTerminarTurnoSi.setLayoutX(0);
		cp.btnTerminarTurnoSi.setLayoutY(50);

	}	
	 private void crear_lienzo() {
	    	cp.lienzo = new Lienzo(cp.canvas);
	    	cp.fondo = new Image (DatosGlobales.rutas.getProperty("imgMapa")+nombre_mapa+".png");
		}
		private void crear_tablero() {
			Casilla casilla;
			int ancho_mapa = Integer.parseInt(cm.getAncho());
			int altura_mapa = Integer.parseInt(cm.getLargo());

			cp.tablero = new Tablero();
	    	boolean bObstaculo = false;
	    	double x = 0;
	    	double y = 0;
	    	int i = 0;
	    	
	    	do {
	    		for(Key obstaculo: dm.getObstaculos()) 	    		
	    			if(obstaculo.getX()==x && obstaculo.getY()==y) {
	    				bObstaculo = true;
	    				break;
	    			}
	    			
	    			casilla = new Casilla(x,y);    		
		    		casilla.getBoton().setId(i+"");
		    		cp.tablero.getCasillas().put(new Key(casilla.getX(), casilla.getY()),casilla);
		    		if(bObstaculo) 
		    			casilla.setObstaculo(true);

	    		
	    		bObstaculo = false;
	    		x+=DatosGlobales.CASILLA_SIZE;
	    		i++;
	    		
	    		if(x>ancho_mapa) {
	    			x = 0; 
	    			y+=DatosGlobales.CASILLA_SIZE;
	    		}
	    	} while (y<altura_mapa);	    	    	
		}

	private void acoplar_tablero() {

    	for(Casilla casilla : cp.tablero.getCasillas().values()) {   
    		cp.mapa.getChildren().add(casilla.getBoton());
    	}
	}				
}
