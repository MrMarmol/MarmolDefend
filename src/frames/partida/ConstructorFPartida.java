package frames.partida;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.HashMap;

import app.BotonBestiario;
import app.BotonEdificio;
import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.tablero.Key;
import bean.tablero.Tablero;
import bean.tablero.casillas.Casilla;
import bean.tablero.casillas.CasillaObstaculo;
import bean.tablero.obstaculos.Obstaculo;
import bean.unidades.Unidad;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import procesos.cargar.CargadoMapa;

public class ConstructorFPartida {
	
	private double ALTURA_MARCO = 150;
	private double PADDING_MARCO = 15;
	private int NUM_BOTONES_HORIZONTAL = 5;
	private int SEPARADOR_SECCIONES = 20;
	private double BARRA_SUPERIOR = 35;
	private double PADDING_BARRA_SUPERIOR = 23;
	private int SIZE_BOTON = 100;
	private int SIZE_VENTANA_TERMINAR_TURNO = 250;
	private double size_scroll;
	
	//La posición Y en la que los nodos pueden acoplarse dentro del marco
	private double espacio_marco; 
	
	private double posicion_historia;
	private double espacio_historia;
	
	private Stage ventana;
	private FXMLLoader loader;
	private AnchorPane root;
	private Scene scene;
	private ControladorPartida cp;
	private Dimension screenSize;
	
	public ConstructorFPartida() {
		ventana = new Stage();
		try {
			inicializar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void mostrar() {
		ventana.initStyle( StageStyle.UNDECORATED );		
		ventana.getIcons().add(new Image("archivos/imagenes/iconos/icono.png"));
		ventana.setTitle("MARMOL DEFEND");
		ventana.setFullScreen(true);
		ventana.setScene(scene);
		ventana.setMaximized(true);		
		ventana.show();		
	}
	
	private void inicializar() throws IOException {
		//Obtengo la resolución del monitor
		Toolkit t = Toolkit.getDefaultToolkit();
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
    	//Cargo la vista
    	loader = new FXMLLoader(getClass().getResource("vista_partida.fxml")); 
		root = loader.load();
		scene = new Scene(root,screenSize.width,screenSize.height);
		cp = loader.<ControladorPartida>getController();
		
		//Obtengo el tamaño y la ubicación correcta de cada nodo 				
		size_scroll = screenSize.height - ALTURA_MARCO;
		espacio_marco = screenSize.height - ALTURA_MARCO+PADDING_MARCO;
		
		posicion_historia = screenSize.width* 0.02;
		espacio_historia = screenSize.width*0.3;		
		
		construirFrame();
		cp.inicializar();
	}
	
	private void construirFrame() {
		cargar_datos();
		modelar_ventana_completa();
		modelar_ventana_terminar_turno();
	//	modelar_ventana_capital();
		crear_lienzo();
		crear_tablero();
		acoplar_tablero();								
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
		cp.btnTerminarTurnoNo.setLayoutX(SIZE_VENTANA_TERMINAR_TURNO-cp.btnTerminarTurnoNo.getWidth());
		cp.btnTerminarTurnoNo.setLayoutY(50);
	}
	
	private void cargar_datos() {
		cp.cm = new CargadoMapa("pruebas");
		cp.obstaculos = new HashMap<Key,CasillaObstaculo>();
		try {
			cp.dm = cp.cm.cargar_mapa();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void modelar_ventana_completa() {		 
		 ajustar_size_panes();
		 ajustar_size_canvas();
		 ajustar_marco_inferior();
		 ajustar_marco_superior();
	     ajustar_cuadro_texto();
	     colocar_grupo_vertical(cp.gAcciones,espacio_historia+posicion_historia, espacio_marco);
	     colocar_grupo_vertical(cp.gHabilidades,espacio_historia+posicion_historia+cp.btnAtacar.getPrefWidth(), espacio_marco);
	     colocar_grupo_unidades();
	     colocar_grupo_edificios();
	     colocar_grupo_estadisticas();
	    }
	private void ajustar_size_panes() {
		cp.base.setPrefSize(screenSize.width, screenSize.height);
		cp.scroll.setPrefSize(screenSize.width, size_scroll-BARRA_SUPERIOR);
		cp.scroll.setLayoutY(BARRA_SUPERIOR);
		cp.scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		cp.scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		cp.mapa.setPrefSize(cp.dm.getAncho(), cp.dm.getLargo());
	}
	private void ajustar_size_canvas() {
		cp.canvas.setWidth(cp.dm.getAncho());
	 	cp.canvas.setHeight(cp.dm.getLargo());
	}
	private void ajustar_marco_inferior() {
		cp.marcoHistoria.setImage(new Image(DatosGlobales.rutas.getProperty("imgMenu")+"marco_partida.png"));
		cp.marcoHistoria.setLayoutX(0);
		cp.marcoHistoria.setLayoutY(size_scroll);
		cp.marcoHistoria.setFitHeight(ALTURA_MARCO);
		cp.marcoHistoria.setFitWidth(screenSize.width);
		
		cp.imgMarco_superior.setImage(new Image(DatosGlobales.rutas.getProperty("imgMenu")+"marco_superior_partida.png"));
		cp.imgMarco_superior.setFitWidth(screenSize.width);
		cp.imgMarco_superior.setFitHeight(BARRA_SUPERIOR);
		cp.imgMarco_superior.setLayoutX(0);
		cp.imgMarco_superior.setLayoutY(0);
	}
	private void ajustar_marco_superior() {
		cp.gRecursos.setLayoutX(PADDING_BARRA_SUPERIOR);
		cp.gRecursos.setLayoutY(5);	
		
		cp.imgMadera.setImage(new Image(DatosGlobales.rutas.getProperty("imgRecursos")+"madera.png"));
		colocar_recursos(cp.imgMadera,PADDING_BARRA_SUPERIOR,cp.vMadera,0+"");
		
		cp.imgOro.setImage(new Image(DatosGlobales.rutas.getProperty("imgRecursos")+"oro.png"));
		colocar_recursos(cp.imgOro,cp.vMadera.getLayoutX()+SEPARADOR_SECCIONES*2,cp.vOro,0+"");
		
		cp.imgMetal.setImage(new Image(DatosGlobales.rutas.getProperty("imgRecursos")+"metal.png"));
		colocar_recursos(cp.imgMetal,cp.vOro.getLayoutX()+SEPARADOR_SECCIONES*2,cp.vMetal,0+"");
		
		cp.imgComida.setImage(new Image(DatosGlobales.rutas.getProperty("imgRecursos")+"comida.png"));
		colocar_recursos(cp.imgComida,cp.vMetal.getLayoutX()+SEPARADOR_SECCIONES*2,cp.vComida,0+"");
		
	    colocar_botones_marco_superior();
	}
	private void colocar_recursos(ImageView imagen, double x, Text texto,String valor) {
		imagen.setLayoutX(x);
		imagen.setLayoutY(0);
		imagen.setFitWidth(30);
		imagen.setFitHeight(25);
		texto.setText(valor);
		texto.setLayoutX(imagen.getLayoutX()+imagen.getFitWidth()+5);	
		texto.setLayoutY(PADDING_BARRA_SUPERIOR);
	}	
	private void colocar_botones_marco_superior() {						
		ajustar_botones_marco_superior(cp.btnMenu,screenSize.width-cp.btnMenu.getPrefWidth()-PADDING_BARRA_SUPERIOR,4,25);
		ajustar_botones_marco_superior(cp.btnCapital,cp.btnMenu.getLayoutX()-cp.btnCapital.getPrefWidth(),4,25);
		ajustar_botones_marco_superior(cp.btnPasarTurno,cp.btnCapital.getLayoutX()-cp.btnPasarTurno.getPrefWidth(),4,25);		
	}
	private void ajustar_botones_marco_superior(Button boton, double x, double y, double height) {
		boton.setLayoutX(x);
		boton.setLayoutY(y);
		if(height>boton.getMinHeight())
		boton.setMinHeight(25);
		boton.setPrefHeight(25);
	}
	private void ajustar_cuadro_texto() {
		cp.tHistoria.setLayoutX(posicion_historia);
    	cp.tHistoria.setLayoutY(espacio_marco);
    	cp.tHistoria.setPrefHeight(ALTURA_MARCO-PADDING_MARCO*2);
    	cp.tHistoria.setPrefWidth(espacio_historia);
    	cp.tHistoria.selectPositionCaret(cp.tHistoria.getLength());
	}
	private void colocar_grupo_vertical(Group grupo, double x, double y) {
		grupo.setLayoutX(x);
		grupo.setLayoutY(y);    
		
		int nx = 25; 
		int ny = 0;
    	
    	for(Node nodo :grupo.getChildren()) {
    		
    		if(nodo.getClass().equals(Text.class)) {
    			nodo.setLayoutX(35);
    			nodo.setLayoutY(20);
    		} else {
    			nodo.setLayoutX(nx);
    			nodo.setLayoutY(ny);
    		}
    		ny+=30;
    	}
	}
	private void colocar_grupo_unidades() {
		
	  	cp.gUnidades.setLayoutX(cp.gHabilidades.getLayoutX()+cp.btnHabilidad1.getPrefWidth()+SEPARADOR_SECCIONES);
	 	cp.gUnidades.setLayoutY(espacio_marco);
	 	
    	Button bot;
    	for(int i = 0; i<2;i++)
    	for(Unidad unidad : DatosGlobales.unidades)
    	{
    		bot = new Button();
    		BotonBestiario boton = new BotonBestiario(unidad,bot);
    		cp.gUnidades.getChildren().add(bot);
    	}
    	
    	int x = 25; 
    	int y = 25; 
    	int i = 1;	

	  	for(Node nodo :cp.gUnidades.getChildren()) {
	    		
	    	if(nodo.getClass().equals(Text.class)) {
	    		nodo.setLayoutX(100);
	    		nodo.setLayoutY(20);
	    		}
	    	else {
	    		nodo.setLayoutX(x);
	    		nodo.setLayoutY(y);
	    		x+=50;
		    	i++;
	    	}	    
	    	if(i>5){
	    		i=1;
	    		x=25;
	    		y+=45;
	    	}
	    }
	}
	private void colocar_grupo_edificios() {
	  	cp.gEdificios.setLayoutX(cp.gHabilidades.getLayoutX()+cp.btnHabilidad1.getPrefWidth()+SEPARADOR_SECCIONES);
	 	cp.gEdificios.setLayoutY(espacio_marco);
	 	
    	Button bot;
    	for(int i = 0; i<6; i++)
    	for(Edificio edificio : DatosGlobales.edificios)
    	{
    		bot = new Button();
    		BotonEdificio boton = new BotonEdificio(edificio,bot);
    		cp.gEdificios.getChildren().add(bot);
    	}
    	
    	int x = 25; 
    	int y = 25; 
    	int i = 1;	

	  	for(Node nodo :cp.gEdificios.getChildren()) {
	    		
	    	if(nodo.getClass().equals(Text.class)) {
	    		nodo.setLayoutX(100);
	    		nodo.setLayoutY(20);}
	    	else {
	    		nodo.setLayoutX(x);
	    		nodo.setLayoutY(y);
	    		x+=SIZE_BOTON;
		    	i++;
	    	}	    	
	    	if(i>NUM_BOTONES_HORIZONTAL){
	    		i=1;
	    		x=25;
	    		y+=30;
	    	}
	    }
	}
	
	private void colocar_grupo_estadisticas() {
		cp.gUnidades.setVisible(false);
		cp.gEstadisticas.setLayoutX(cp.gEdificios.getLayoutX()+SIZE_BOTON*NUM_BOTONES_HORIZONTAL+SEPARADOR_SECCIONES*2);
		cp.gEstadisticas.setLayoutY(espacio_marco);
		
		cp.imgCaraUnidad.setImage(DatosGlobales.unidades.get(1).getCara());
		cp.imgCaraUnidad.setFitWidth(ALTURA_MARCO-PADDING_MARCO*2);
		cp.imgCaraUnidad.setFitHeight(cp.imgCaraUnidad.getFitWidth());
		
		double y = 15;
		double x = 5;
		double i = 0;
		for(Node texto : cp.gEstadisticas.getChildren()) {
			
			if(texto instanceof Text) {
			texto.setLayoutX(cp.imgCaraUnidad.getFitWidth()+x);
			texto.setLayoutY(y);
			y+=25;
			i++;
			}
			if(i==4) {
				y=15;
				x=40;
				i=0;
			}
		}

	}
	
	 private void crear_lienzo() {
	    	cp.lienzo = cp.canvas.getGraphicsContext2D();
	    	cp.fondo = new Image (DatosGlobales.rutas.getProperty("imgMapa")+"PRUEBA.png");
		}
		private void crear_tablero() {
			cp.tablero = new Tablero();
	    	boolean bObstaculo = false;
	    	double x = 0;
	    	double y = 0;
	    	int i = 0;
	    	
	    	do {
	    		for(Obstaculo obstaculo: cp.dm.getObstaculos()) {
	    		
	    			if(obstaculo.contacto(x, y, DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE)){
	    				cp.obstaculos.put(new Key(x,y),new CasillaObstaculo(x,y,obstaculo));    	
	    				bObstaculo = true;
	    			}
	    		}    		
	    		if(!bObstaculo) {    			    		
		    		cp.casilla = new Casilla(x,y);    		
		    		cp.casilla.getBoton().setId(i+"");
		    		cp.tablero.getCasillas().put(new Key(cp.casilla.getX(), cp.casilla.getY()),cp.casilla);
	    		}
	    		bObstaculo = false;
	    		x+=DatosGlobales.CASILLA_SIZE;
	    		i++;
	    		
	    		if(x>2000) {
	    			x = 0; 
	    			y+=DatosGlobales.CASILLA_SIZE;
	    		}
	    	} while (y<1000);	    	    	
		}

	private void acoplar_tablero() {

    	for(Casilla casilla : cp.tablero.getCasillas().values()) {   
    		cp.mapa.getChildren().add(casilla.getBoton());
    	}
	}				
}
