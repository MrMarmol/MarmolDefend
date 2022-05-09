package frames.partida;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import procesos.animacion.Animacion;
import procesos.cargar.CargadoMapa;
import procesos.lienzo.Lienzo;

import java.util.ArrayList;
import java.util.HashMap;

import app.Jugador;

import java.io.IOException;

import bean.botones.BotonBestiario;
import bean.botones.BotonEdificio;
import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.edificios.interfaces.InEdificioCombate;
import bean.mapas.DatosMapa;
import bean.recurso.Recurso;
import bean.tablero.Key;
import bean.tablero.Tablero;
import bean.tablero.casillas.Casilla;
import bean.tablero.casillas.CasillaObstaculo;
import bean.tablero.obstaculos.Obstaculo;
import bean.unidades.Aldeano;
import bean.unidades.Soldado;
import bean.unidades.Unidad;
import bean.unidades.UnidadCombate;
import bean.unidades.interfaces.InUnidadCombate;
import bean.unidades.interfaces.InUnidadProduccion;
import procesos.acciones.*;
import procesos.acciones.unidades.alojar.AccionAlojar;
import procesos.acciones.unidades.atacar.AccionAtaque;
import procesos.acciones.unidades.construir.AccionConstruir;
import procesos.acciones.unidades.construir.ControladorEdificios;
import procesos.acciones.unidades.mover.AccionMover;
import procesos.acciones.unidades.mover.ControladorMovimiento;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;


public class ControladorPartida {
	
	//El controlador es el UNICO que conoce la UBICACION X e Y de cada UNIDAD, así como el que tiene acceso a las CASILLAS del TABLERO
    
	@FXML
	Group gUnidadesEdificio;
	@FXML
	TextArea tHistoria;
    @FXML
    AnchorPane base;   
	@FXML
    AnchorPane mapa;
    @FXML
    Canvas canvas;
    @FXML
    ScrollPane scroll;    
    @FXML
    Group gAcciones;
    @FXML
    Button btnAtacar;
    @FXML
    Button btnCancelar;
    @FXML
    Button btnRecolectar;
    @FXML
    Button btnAlojar;
    @FXML
    Group gHabilidades;
    @FXML
    Button btnHabilidad3;
    @FXML
    Button btnHabilidad1;
    @FXML
    Button btnHabilidad2;
    @FXML
    Group gUnidades;
    @FXML
    Group gEdificios;
    @FXML
    ImageView img_marco_inferior;
    @FXML
    Group gEstadisticas;
    @FXML
    ImageView imgCaraUnidad;
    @FXML
    Text tAt;
    @FXML
    Text tDefensa;
    @FXML
    Text tPg;
    @FXML
    Text tMovimiento;
    @FXML
    Text vAt;
    @FXML
    Text vDefensa;
    @FXML
    Text vPg;
    @FXML
    Text vMovimiento;
    @FXML
    Group gRecursos;
    @FXML
    Text vComida;
    @FXML
    Text vMetal;
    @FXML
    Text vOro;
    @FXML
    Text vMadera;
    @FXML
    ImageView imgOro;
    @FXML
    ImageView imgMetal;
    @FXML
    ImageView imgMadera;
    @FXML
    ImageView imgComida;
    @FXML
    ImageView imgMarco_superior;
    @FXML
    Button btnMenu;
    @FXML
    Button btnCapital;
    @FXML
    Button btnPasarTurno;
    @FXML
    Group gMenuTerminarTurno;
    @FXML
    ImageView imgFondoPasarTurno;
    @FXML
    Text tTerminarTurno;
    @FXML
    Button btnTerminarTurnoSi;
    @FXML
    Button btnTerminarTurnoNo;
    @FXML
    Text tAcciones;
       
    Casilla casilla;
    HashMap<Key, CasillaObstaculo> obstaculos;
    Tablero tablero;
    
    Lienzo lienzo;    
    Image fondo;
    
    DatosMapa dm;
    CargadoMapa cm;
    ArrayList<BotonEdificio> botonesEdificio;

    private ArrayList<Casilla> camino_casillas;
    private ArrayList<String> camino_direcciones;
    
    private Casilla casilla_seleccionada;
    private Casilla casilla_actual;
    private Casilla casillaDestino;
    private Casilla casilla_auxiliar;
    
    private boolean atacando;
    private boolean construyendo;
    private boolean recolectando;
    private boolean alojandose;
    private boolean desalojandose;
    
    private ControladorEdificios controlador_edificios;
    private Edificio edificio_construyendose;
    private boolean camino_construyendose;
    
    private ControladorMovimiento conmo;  
    private Rectangle coordenadaImagen;        
    private int sprite = 0; 
    private int fps = 0;    
    
    private double x;
    private double y;

    private Unidad unidad_desalojada;
    private Jugador jugador;
    private UnidadCombate uc;
    
    @FXML
    void mostrar_terminar_turno(ActionEvent event) {
    	gMenuTerminarTurno.setVisible(true);
    }
    @FXML
    void terminar_turno(ActionEvent event) {
    	
    }
    @FXML
    void cerrar_ventana_terminar_turno(ActionEvent event) {
    	gMenuTerminarTurno.setVisible(false);
    }
    
    public void inicializar() {	
    	jugador = new Jugador();
		Aldeano aldeano = new Aldeano(DatosGlobales.unidades.get(0).getNombre(),DatosGlobales.unidades.get(0).getDescripcion(), 
				DatosGlobales.unidades.get(0).getCara(),
				DatosGlobales.unidades.get(0).getPortada(),
				DatosGlobales.unidades.get(0).getAnimacion(),
				DatosGlobales.unidades.get(0).getAt(),
				DatosGlobales.unidades.get(0).getDef(),
				DatosGlobales.unidades.get(0).getPg(),
				DatosGlobales.unidades.get(0).getHabilidades(),
				DatosGlobales.unidades.get(0).getBando()				
		);
		UnidadCombate soldado = new Soldado(
				DatosGlobales.unidades.get(1).getNombre(),DatosGlobales.unidades.get(1).getDescripcion(), 
				DatosGlobales.unidades.get(1).getCara(),
				DatosGlobales.unidades.get(1).getPortada(),
				DatosGlobales.unidades.get(1).getAnimacion(),
				DatosGlobales.unidades.get(1).getAt(),
				DatosGlobales.unidades.get(1).getDef(),
				DatosGlobales.unidades.get(1).getPg(),
				DatosGlobales.unidades.get(1).getHabilidades(),
				DatosGlobales.unidades.get(1).getBando(),
				new AccionAtaque());
		UnidadCombate soldado2= new Soldado(
				DatosGlobales.unidades.get(1).getNombre(),DatosGlobales.unidades.get(1).getDescripcion(), 
				DatosGlobales.unidades.get(1).getCara(),
				DatosGlobales.unidades.get(1).getPortada(),
				DatosGlobales.unidades.get(1).getAnimacion(),
				DatosGlobales.unidades.get(1).getAt(),
				DatosGlobales.unidades.get(1).getDef(),
				DatosGlobales.unidades.get(1).getPg(),
				DatosGlobales.unidades.get(1).getHabilidades(),
				DatosGlobales.unidades.get(1).getBando(),
				new AccionAtaque());
		//	Al crear las unidades comparten la ubicacion de memoria de la animacion al ser un objeto, debes crear un constructor de cloacion y crear una animacion de nuevo
		
    	tablero.getCasillas().get(new Key(0.0,0.0)).setUnidad(aldeano);
    	tablero.getCasillas().get(new Key(96.0,96.0)).setUnidad(soldado);
    	tablero.getCasillas().get(new Key(32.0,32.0)).setUnidad(soldado2);

    	casilla_actual = tablero.getCasillas().get(new Key(0.0,0.0));
    			
    	conmo = new ControladorMovimiento();
    	camino_casillas = new ArrayList<Casilla>();
    	camino_direcciones = new ArrayList<String>();
    	controlador_edificios = new ControladorEdificios();
    	Jugador jugador = new Jugador();
    	jugador.setMadera(1000);
    	

    	implementar_acciones();    	    	    	    
		ciclo();     	    
    }
    
    
    private void implementar_acciones() {    	
    	implementar_click_izquierdo();
    	implementar_click_derecho();  
    	implementar_onkeypressed();    	 
    	implementar_onMouseEntered();
    }
    private void implementar_click_izquierdo() {
    	for(Casilla casilla : tablero.getCasillas().values()) {   
    		casilla.getBoton().setOnAction(new EventHandler() {
   				@Override
				public void handle(Event event) {
   					buscar_casilla_seleccionada(event);	//Se obtiene la casilla cliqueada
   					if(comprobar_acciones())			//Si hay una accion iniciada, se ejecuta sobre la casilla.
   						ejecutar_acciones();
   					else {				
   						actualizar_casilla_actual();	//Si no hay ninguna acción, la casilla cliqueada pasa a ser la actual
   						actualizar_marco();				//Se actualiza el marco inferior en función de la nueva casilla actual
   					}
   				}
    		});
    	};
    	for(BotonEdificio bEdificio : botonesEdificio) {   

	    		bEdificio.getBoton().setOnAction(new EventHandler() {
					@Override
					public void handle(Event event) {
						if(comprobar_recursos(bEdificio.getEdificio()))
						iniciar_construir(bEdificio.getEdificio());
					}	    		
	    		});    
    	};    			
    }
    private void implementar_click_derecho() {
    	for(Casilla casilla : tablero.getCasillas().values()) {   
    		casilla.getBoton().setOnMouseClicked(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				/*Si se hace click derecho sobre una unidad, comprueba si ya hay un camino construyéndose
    				 * y lo borra o de lo contrario inicia su construccion */
    				if(event.getButton().equals(MouseButton.SECONDARY) && casilla.getUnidad()!=null) {
    					if(camino_construyendose) {    			
    						camino_construyendose = false;
    						borrar_camino();
    					}
    					else {
    						camino_construyendose = true;
    						casilla.setCamino(true);
    						camino_casillas.add(casilla);
    					}    					
    				}
    				//Si se hace click derecho sobre una casilla vacía y hay un camino construyendose, comienza a recorrerlo.
    				else if(event.getButton().equals(MouseButton.SECONDARY)) {
    					if(construyendo)
    						cancelar_construccion();
    					else if(camino_construyendose) {
    						camino_construyendose=false;
    						correr();
    						borrar_camino();
    					}     					
    				}
    			}
    	});
    	}
    }
    
    private void implementar_onMouseEntered() {
    	for (Casilla casilla : tablero.getCasillas().values()){
    		casilla.getBoton().setOnMouseEntered(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				
    				/*Si el raton pasa sobre una casilla mientras se está construyendo un camino, comprueba si la casilla es adyacente
    				 * y la agrega al camino. En caso contrario se ignora*/
    				if(!casilla.getCamino()) {	   
    					if(camino_construyendose && casilla.getUnidad()==null) {
	        				if(tablero.comprobar_casillas_adyacentes(casilla, camino_casillas.get(camino_casillas.size()-1))) {
	        					camino_direcciones.add(tablero.obtener_direccion(casilla,camino_casillas.get(camino_casillas.size()-1)));
	    						camino_casillas.add(casilla);
	        					casilla.setCamino(true);
	        				}
	    				}    				
	    			}    
    				//Si se está construyendo y el dibujo del edificio está siguiendo al ratón, va actualizando las coordenadas donde el lienzo
    				//dibujará el edificio
    				if(construyendo) {
    					x = casilla.getX();
    					y = casilla.getY();
    				}
    			}
    			});
    	}
    }

    private void crear_accion_andar(String direccion) {
		if(conmo.comprobar_movimiento_posible(casilla_actual,tablero.getCasillaAdyacente(casilla_actual,direccion))) {
			casillaDestino = tablero.getCasillaAdyacente(casilla_actual, direccion);							
			conmo.crear_accion_andar(casilla_actual, casillaDestino, direccion, DatosGlobales.PASOS_ANDAR);		
			casilla_actual = null;
		}
    }
    private void implementar_onkeypressed() {    	

    	base.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		
    		//Si selecciona una casilla con una unidad, crea la accion de andar hacia cuatro direcciones WASD
			@Override
			public void handle(KeyEvent event) {
				if(casilla_actual!=null && casilla_actual.getUnidad()!=null)				
				switch(event.getCode()){
					case D:
						crear_accion_andar(DatosGlobales.DERECHA);
						break;
					case W:
						crear_accion_andar(DatosGlobales.ARRIBA);
						break;
					case  S:
						crear_accion_andar(DatosGlobales.ABAJO);
						break;
					case A:
						crear_accion_andar(DatosGlobales.IZQUIERDA);
						break;					
					default:
						break;
				}
				//Si selecciona el mapa, este se desplazará en cuatro direcciones WASD
					if(casillaDestino==null)
					switch(event.getCode()){
					case D:
						scroll.setHvalue(scroll.getHvalue()+0.1);
						break;
					case W:
						scroll.setVvalue(scroll.getVvalue()-0.1);
						break;
					case  S:
						scroll.setVvalue(scroll.getVvalue()+0.1);
						break;
					case A:
						scroll.setHvalue(scroll.getHvalue()-0.1);
						break;
					default:
						break;
				}
			}});    	
    }
    
    private void buscar_casilla_seleccionada(Event event) {
    	//Se obtiene el id del botón cliqueado
    	String[] ids = event.getTarget().toString().split("\\[");
    	String id = ids[1].split(",")[0].split("=")[1];
    	
    	//Se obtiene la casilla que contiene dicho botón
    	for(Casilla casilla : tablero.getCasillas().values())    
    		if(casilla.getBoton().getId().equals(id))
    			casilla_seleccionada = casilla;
    }
    private boolean comprobar_acciones() {
    	if(atacando||construyendo||recolectando||alojandose||desalojandose)
    		return true;
    	return false;
    }
    
    private void ejecutar_acciones() {
    	if(desalojandose)
   			desalojar();
    	else
    	//Si la casilla seleccionada es adyacente a la casilla desde la que se pretende realizar un aacción, se ejecuta la acción.
	    if(tablero.comprobar_casilla_adyacente(casilla_actual, casilla_seleccionada)) {    			    	
	    	if(atacando) {
	    		if(casilla_seleccionada.getUnidad()!=null) 
	    			atacar(casilla_actual,casilla_seleccionada);	    		
	    		else 
	    			tHistoria.appendText("Seleccione una unidad a la que atacar\n");
	    		}
		   	else if(construyendo) 
		       	construir(edificio_construyendose);
		    	
		   	else if(recolectando) 
		   		recolectar(casilla_seleccionada.getUnidad());	
		   	else if(alojandose) 
		    	if(casilla_seleccionada.getEdificio()!=null)
		    		alojar();		   	
	    }
	    else tHistoria.appendText("Seleccione una casilla adyacente a la unidad.\n");   
	}
    private void desalojar() {
    	for(Edificio edificio : controlador_edificios.getEdificios()) {
    		if(edificio.contiene_unidad(unidad_desalojada)) {
    			for(Casilla casilla : edificio.getCasillas()) 
    				if(tablero.comprobar_casillas_adyacentes(casilla, casilla_seleccionada)) {
    					casilla_seleccionada.setUnidad(unidad_desalojada);
    					unidad_desalojada=null;
    					desalojandose = false;
						edificio.desalojar(unidad_desalojada);
						break;
    				}
    			tHistoria.appendText("Seleccione una casilla adyacente al edificio");
    			break;
    		}    			
    	}
    }
    private void alojar() {
    	AccionAlojar alojamiento = new AccionAlojar(casilla_actual.getUnidad(),casilla_seleccionada.getEdificio());
    	if(alojamiento.alojar()) {
    		tHistoria.appendText(casilla_actual.getUnidad().getNombre()+" se ha alojado en "+casilla_seleccionada.getEdificio().getNombre());
    		casilla_actual.setUnidad(null);
    		cancelar_alojamiento();
    	}
    	else
    		tHistoria.appendText("No caben más unidades en el edificio\n");
    }

    public void cancelar_alojamiento(){
    	alojandose = false;
    }
    private void actualizar_casilla_actual() {
    	casilla_actual = casilla_seleccionada;  
    	casilla_seleccionada = null;

    }
    private void actualizar_marco() {
    	//Comprueba qué tipo de unidad es y actualiza el marco correspondiente. Después cambia las estadísticas por 
    	//de la nueva unidad seleccionada. En caso de que la casilla tenga un edificio, muestra las opciones de este.
		if(casilla_actual.getUnidad()!=null) {
			
			if(casilla_actual.getUnidad() instanceof InUnidadCombate) 
				mostrar_marco_combate();		
			
			else if(casilla_actual.getUnidad() instanceof InUnidadProduccion) 
				mostrar_marco_produccion();
			
			cambiar_estadisticas(casilla_actual.getUnidad());
		}
		else if(casilla_actual.getEdificio()!=null) 
				mostrar_marco_edificio();
		
    }
   
    public void ciclo() {
    	AnimationTimer animationTimer = new AnimationTimer() {    	
    	@Override
    	public void handle(long tiempoActual) {
			
        	lienzo.dibujar_imagen(fondo,0,0);   //Dibuja el mapa
        	mover();        					//Mueve las unidades y las dibuja
        	dibujar_edificios();				//Dibuja los edificios
        	actualizar_fps();					//Actualiza los frames y los sprites
        	dibujar_camino();					//Dibuja los caminos en construcción
        	comprobar_casilla_auxiliar();		//comprueba las condiciones de la casilla auxiliar        	
    	};
    	};
    animationTimer.start();
    }
    private void comprobar_casilla_auxiliar() {
    	if(casilla_auxiliar!=null)
    		if(casilla_auxiliar.getUnidad()!=null)//Si la unidad ha terminado de correr
    			if(casilla_actual != null &&casilla_actual.getUnidad()==null){//Si no ha seleccionado otra unidad
    				casilla_actual=casilla_auxiliar;
    				casilla_auxiliar=null;
    			}	
    }
    
	public void mover() {		
		mover_unidades();
    	actualizar_controlador_movimientos();
		dibujar_unidades_moviendose();	
	}
	//El controlador actualiza sus movimientos
	private void mover_unidades(){		
		conmo.mover();
	}
	
	private void dibujar_unidades_moviendose() {
		//Unidades corriendo
		for(AccionMover movimiento : conmo.getMovimientos()) 
			lienzo.dibujar_unidad(movimiento,movimiento.getUnidad().getAnimacion().getSprites(movimiento.getDireccion()).get(sprite),movimiento.getX(),movimiento.getY());
		
		//Unidad andando
		if(conmo.getAccionAndar()!=null)
		lienzo.dibujar_unidad(conmo.getAccionAndar(), conmo.getAccionAndar().getUnidad().getAnimacion().getSprites(conmo.getAccionAndar().getDireccion()).get(sprite), conmo.getAccionAndar().getX(), conmo.getAccionAndar().getY());
		
		//Unidades estáticas
    	for(Casilla casilla : tablero.getCasillas().values()) {    		
    		if(casilla.getUnidad()!=null) {
    		coordenadaImagen = casilla.getUnidad().getAnimacion().getDireccionActual().get(sprite);
        	lienzo.dibujar_unidad(casilla.getUnidad(),coordenadaImagen, casilla.getBoton().getLayoutX(), casilla.getBoton().getLayoutY());        	            	   
    		}
    	}
	}

	private void actualizar_controlador_movimientos() {		
		conmo.actualizar_acciones();				
			
			//Comprueba si la acción de andar ha terminado y actualiza las casillas
			if(conmo.comprobar_accion_andar() && casilla_actual == null) {
				casilla_actual = casillaDestino;
				casillaDestino = null;
			}
	}
    private void dibujar_edificios() {
    	for(Edificio edificio : controlador_edificios.getEdificios())
    		lienzo.dibujar_imagen(edificio.getImagen(), edificio.getX(), edificio.getY());
    	
    	if(construyendo) 
    		lienzo.dibujar_imagen(edificio_construyendose.getImagen(), x, y);
    	
    }
	//Cada 10 fps, se actualizan las imágenes
	public void actualizar_fps() {
		fps++;
    	if(fps == 10) {
    		sprite++; 
    		fps = 0;
    	}    	
    	if(sprite>2)
    		sprite=0;
	}
	
	//Dibuja los caminos
	private void dibujar_camino() {
		for(Casilla casilla : tablero.getCasillas().values())
    		if(casilla.getCamino())
    			lienzo.dibujar_imagen(new Image(DatosGlobales.rutas.getProperty("imgIconos")+"casilla_camino.png"), casilla.getX(),casilla.getY());
	}

    private void borrar_camino() {    	
    	for(Casilla casilla : camino_casillas)
			casilla.setCamino(false);

    	camino_casillas = new ArrayList<Casilla>();
    	camino_direcciones = new ArrayList<String>();
    	camino_construyendose = false;
    }
    
    private void correr(){
    	camino_construyendose = false;
		conmo.crear_accion_correr(casilla_actual,4,camino_direcciones,camino_casillas);	
		casilla_auxiliar = camino_casillas.get(camino_casillas.size()-1);
    }    
    private void recolectar(Unidad unidad) {
    	if(unidad instanceof Unidad) {}	//Si implementa la interfaz de recolectar, la unidad llama a su metodo recolectar
    	cancelar_accion();
    }
    
    private boolean comprobar_recursos(Edificio edificio) {
    	ArrayList<Recurso> coste_edificio = edificio.getCosteRecursos();    	
    	for(Recurso recurso : coste_edificio) {
    		
    		switch(recurso.getRecurso()) {    		
    			case DatosGlobales.ORO:
    				if(jugador.getOro()<recurso.getCantidad()) {
    			    	tHistoria.appendText("No tienes oro suficiente");    				
    					return false;
    				}
    				break;
    			case DatosGlobales.MADERA:
    				if(jugador.getMadera()<recurso.getCantidad()) {
    			    	tHistoria.appendText("No tienes madera suficiente");
    					return false;
    				}
    				break;
    			case DatosGlobales.COMIDA:
    				if(jugador.getComida()<recurso.getCantidad()) {
    			    	tHistoria.appendText("No tienes comida suficiente");
    					return false;
    				}
    				break;
    			case DatosGlobales.METAL:
    				if(jugador.getMetal()<recurso.getCantidad()) {
    			    	tHistoria.appendText("No tienes metal suficiente");
    					return false;
    				}
    				break;
    		}
    	}
		return true;
    }
    public void restar_recursos(Edificio edificio) {
    	ArrayList<Recurso> coste_edificio = edificio.getCosteRecursos();    	
    	for(Recurso recurso : coste_edificio) {
    		
    		switch(recurso.getRecurso()) {    		
    			case DatosGlobales.ORO:
    				jugador.restar_recurso(recurso.getRecurso(),recurso.getCantidad());
    				break;
    			case DatosGlobales.MADERA:
    				jugador.restar_recurso(recurso.getRecurso(),recurso.getCantidad());
    				break;
    			case DatosGlobales.COMIDA:
    				jugador.restar_recurso(recurso.getRecurso(),recurso.getCantidad());
    				break;
    			case DatosGlobales.METAL:
    				jugador.restar_recurso(recurso.getRecurso(),recurso.getCantidad());
    				break;    	
    		}
    	}
    }
    
    private void construir(Edificio edificio) {
    	
    	//Separamos el edificio que hemos arrastrado del botonEdificio del que construiremos
			Edificio edifi = new Edificio(edificio);

    	AccionConstruir accion_construir = new AccionConstruir(edifi);
    	accion_construir.establecer_cimientos(casilla_seleccionada.getX(), casilla_seleccionada.getY());
    	
    	boolean edificable = true;
    	for(Casilla casilla : tablero.getCasillas().values()) 
    	{    	
    		if(accion_construir.contacto(casilla.getX(), casilla.getY(), DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE)) {
    			if(accion_construir.comprobar_casilla_edificable(casilla)) {
    				accion_construir.agregar_cimiento(casilla);
    			}
    		  	else { 
    	    		edificable = false;
    	    		accion_construir.eliminar_edificio();
    	    		break;
    	    	}
    		} 
    	}
    	if(edificable) {
    		restar_recursos(edifi);
    		actualizar_recursos();
    		controlador_edificios.add(edifi);
    		cancelar_construccion(); 		
    	}    
    }
    private void cancelar_construccion() {
    	edificio_construyendose = null;
    	construyendo=false;
    	actualizar_raton();
    	btnCancelar.setVisible(false);
    }
    private void mostrar_marco_combate() {
    	btnAlojar.setVisible(true);
    	gUnidadesEdificio.setVisible(false);
    	btnCancelar.setVisible(false);
    	btnRecolectar.setVisible(false);
    	gUnidades.setVisible(false);
    	gEdificios.setVisible(false);
    	
    	btnAtacar.setVisible(true);
    	gEstadisticas.setVisible(true);
    	btnAtacar.setLayoutY(tAcciones.getLayoutY()+10);
    	
    }
    private void mostrar_marco_produccion() {
    	btnAlojar.setVisible(true);
    	gUnidadesEdificio.setVisible(false);
    	btnCancelar.setVisible(false);
    	btnRecolectar.setVisible(true);
    	gUnidades.setVisible(false);
    	gEdificios.setVisible(true);
    	
    	btnAtacar.setVisible(false);
    	gEstadisticas.setVisible(true);
    	btnRecolectar.setLayoutY(tAcciones.getLayoutY()+10);

    }
    
    private void mostrar_marco_deseleccionado() {
    	for(Node nodo : gAcciones.getChildren())
    		nodo.setVisible(false);
    	for(Node nodo : gHabilidades.getChildren())
    		nodo.setVisible(false);
    	for(Node nodo : gEdificios.getChildren())
    		nodo.setVisible(false);
    	for(Node nodo : gEstadisticas.getChildren())
    		nodo.setVisible(false);    	
    }
    private void cambiar_estadisticas(Unidad unidad) {
    	vAt.setText(unidad.getAt()+"");
    	vDefensa.setText(unidad.getDef()+"");
    	vPg.setText(unidad.getPg()+"");
    	imgCaraUnidad.setImage(unidad.getCara());
    }
    private void mostrar_marco_edificio() {
    	gEdificios.setVisible(false);

    	btnAlojar.setVisible(false);
    	btnCancelar.setVisible(false);
    	btnRecolectar.setVisible(false);
    	gHabilidades.setVisible(false);
    	gUnidadesEdificio.getChildren().clear();
    	gUnidadesEdificio.setVisible(true);
    	
		for(AccionAlojar alojamiento : casilla_actual.getEdificio().getUnidadesAlojadas()) {
			BotonBestiario boton = new BotonBestiario(alojamiento.getUnidad(),new Button());
			gUnidadesEdificio.getChildren().add(boton.getBoton());
			boton.getBoton().setOnAction(new EventHandler() {
   				@Override
				public void handle(Event event) {   					
   					unidad_desalojada = boton.getUnidad();
   					desalojandose = true;
   				}
   			});
		}
		int x = 25; 
    	int y = 25; 
    	int i = 1;	    	
	
		for(Node nodo : gUnidadesEdificio.getChildren()) {
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

    private void atacar(Casilla casillaAtacante, Casilla casillaAtacada) {
    	uc = new UnidadCombate(casillaAtacante.getUnidad());
    	tHistoria.appendText(uc.atacar(casillaAtacada.getUnidad()));  
    	cancelar_accion();
    }   
    
    @FXML
    void iniciar_ataque(ActionEvent event) {
    	atacando = true;
    	actualizar_raton();
    	btnCancelar.setVisible(true);
    	btnCancelar.setLayoutY(btnCancelar.getLayoutY()-60);
    }
    @FXML
    void iniciar_recolectar(ActionEvent event) {
    	recolectando = true;
    	actualizar_raton();
    	btnCancelar.setVisible(true);
    	btnCancelar.setLayoutY(btnCancelar.getLayoutY()-60);
    }
    private void iniciar_construir(Edificio edificio) {
    	construyendo = true;
    	actualizar_raton();
    	btnCancelar.setVisible(true);
    	btnCancelar.setLayoutY(btnCancelar.getLayoutY()-60);
    	edificio_construyendose = edificio;
    }
    @FXML
    void cancelar_accion(ActionEvent event) {
    	cancelar_accion();
    }
    private void cancelar_accion() {
    	atacando=false;
    	construyendo=false;
    	recolectando=false;
    	actualizar_raton();
    	btnCancelar.setLayoutY(btnCancelar.getLayoutY()+60);
    	btnCancelar.setVisible(false);
    	
    }
    private void actualizar_recursos() {
    	vComida.setText(jugador.getComida()+"");
    	vOro.setText(jugador.getOro()+"");
    	vMadera.setText(jugador.getMadera()+"");
    	vMetal.setText(jugador.getMetal()+"");
    }
    private void actualizar_raton() {
    	if(atacando)
        	scroll.setCursor(Cursor.CROSSHAIR);
    	else if(construyendo)
        	scroll.setCursor(Cursor.HAND);
    	else if(recolectando)
        	scroll.setCursor(Cursor.HAND);
    	else 
        	scroll.setCursor(Cursor.DEFAULT);
    }
    
    @FXML
    void alojar_unidad() {
    	alojandose = true;
    }
    

}



