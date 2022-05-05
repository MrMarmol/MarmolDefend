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
import procesos.animacion.UnidadEnMovimiento;
import procesos.cargar.CargadoMapa;

import java.util.ArrayList;
import java.util.HashMap;

import app.Jugador;
import java.io.IOException;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.edificios.interfaces.InEdificioCombate;
import bean.mapas.DatosMapa;
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

public class ControladorPartida {
    
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
    ImageView marcoHistoria;
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
       
    GraphicsContext lienzo;
    Casilla casilla;
    Tablero tablero;
    Image fondo;
    DatosMapa dm;
    CargadoMapa cm;
    HashMap<Key, CasillaObstaculo> obstaculos;
    
    private ArrayList<Casilla> camino_casillas;
    private ArrayList<String> camino_direcciones;

    
    private int sprite = 0; 
    private int fps = 0;    
    private Casilla casilla_actual;
    private Casilla casilla_seleccionada;
    private UnidadEnMovimiento um;
    private boolean unidad_moviendose;
    private Rectangle coordenadaImagen;        
    private double x;
    private double y;    
    private Edificio edificio;
    
    private boolean atacando;
    private boolean construyendo;
    private boolean recolectando;
    private boolean camino_construyendose;
    private boolean corriendo;
    
    private int num_casilla_camino;
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
				DatosGlobales.unidades.get(1).getBando());
		
    	tablero.getCasillas().get(new Key(0.0,0.0)).setUnidad(aldeano);
    	tablero.getCasillas().get(new Key(96.0,96.0)).setUnidad(soldado);
    	casilla_actual = 	tablero.getCasillas().get(new Key(0.0,0.0));
    			
    	camino_casillas = new ArrayList<Casilla>();
    	camino_direcciones = new ArrayList<String>();

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
    	}
    }
    private void implementar_click_derecho() {
    	for(Casilla casilla : tablero.getCasillas().values()) {   
    		casilla.getBoton().setOnMouseClicked(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				if(event.getButton().equals(MouseButton.SECONDARY) && casilla.equals(casilla_actual)) {
    					if(camino_construyendose) {    			
    						camino_construyendose = false;
    						borrar_camino();
    						}
    					else if(!corriendo){
    						camino_construyendose = true;
    						camino_casillas.add(casilla_actual);
    						casilla.setCamino(true);
    						System.out.println("Camino iniciado");
    					}    					
    				}
    				else if(event.getButton().equals(MouseButton.SECONDARY)) {
    					if(camino_construyendose) {
    						camino_construyendose=false;
    						correr();
    					} else borrar_camino();
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
    				
    				if(!casilla.getCamino()) {	   
    					if(camino_construyendose && casilla.getUnidad()==null) {
	        				if(tablero.comprobar_camino_adyacente(casilla, camino_casillas.get(camino_casillas.size()-1))) {
	        					camino_direcciones.add(tablero.obtener_direccion(casilla,camino_casillas.get(camino_casillas.size()-1)));
	    						camino_casillas.add(casilla);
	        					casilla.setCamino(true);
	        				}
	    				}    				
	    			}    			
    			}
    			});
    	}
    }

    private void borrar_camino() {    	
    	for(Casilla casilla : camino_casillas)
			casilla.setCamino(false);

    	camino_casillas.clear();
    	camino_direcciones.clear();
    	camino_construyendose = false;
    	num_casilla_camino = 0;
    }
    
    																				private void correr(){
    	corriendo = true;
    	establecer_movimiento(camino_direcciones.get(num_casilla_camino),4);
    }
    
    
    private void buscar_casilla_seleccionada(Event event) {
    	String[] ids = event.getTarget().toString().split("\\[");
    	String id = ids[1].split(",")[0].split("=")[1];
    	
    	for(Casilla casilla : tablero.getCasillas().values())    
    		if(casilla.getBoton().getId().equals(id))
    			casilla_seleccionada = casilla;
    }
    private boolean comprobar_acciones() {
    	if(atacando||construyendo||recolectando)
    		return true;
    	return false;
    }
    
    private void ejecutar_acciones() {
    	    	
	    if(tablero.comprobar_casilla_adyacente(casilla_actual, casilla_seleccionada)) {    			    	
	    	if(atacando)
	    		if(casilla_seleccionada.getUnidad()!=null) {
		   		atacar(casilla_actual,casilla_seleccionada);
	    		} 
	    		else {
	    			tHistoria.appendText("Seleccione una unidad a la que atacar\n");
	    		}
		   	else if(construyendo) 
		       	construir(casilla_seleccionada.getUnidad());
		    	
		   	else if(recolectando) 
		   		recolectar(casilla_seleccionada.getUnidad());			    	
	    }
	    else tHistoria.appendText("Seleccione una casilla adyacente a la unidad.\n");   
	}
    
    private void actualizar_casilla_actual() {
    	casilla_actual = casilla_seleccionada;    	    	    	
    }
    private void actualizar_marco() {
		if(casilla_actual.getUnidad()!=null) {
			
			if(casilla_actual.getUnidad() instanceof InUnidadCombate) 
				mostrar_marco_combate();		
			
			else if(casilla_actual.getUnidad() instanceof InUnidadProduccion) 
				mostrar_marco_produccion();
			
			actualizar_estadisticas(casilla_actual.getUnidad());
		}
		else if(casilla_actual.getEdificio()!=null) {
			if(casilla_actual.getEdificio() instanceof InEdificioCombate) 
				mostrar_marco_edificio();
		}
    }
 
    private void mostrar_marco_combate() {
    	btnCancelar.setVisible(false);
    	btnRecolectar.setVisible(false);
    	gUnidades.setVisible(false);
    	gEdificios.setVisible(false);
    	
    	btnAtacar.setVisible(true);
    	gEstadisticas.setVisible(true);
    	btnAtacar.setLayoutY(tAcciones.getLayoutY()+10);
    	
    }
    private void mostrar_marco_produccion() {
    	btnCancelar.setVisible(false);
    	btnRecolectar.setVisible(true);
    	gUnidades.setVisible(false);
    	gEdificios.setVisible(true);
    	
    	btnAtacar.setVisible(false);
    	gEstadisticas.setVisible(true);
    	btnRecolectar.setLayoutY(tAcciones.getLayoutY()+10);

    }
    private void actualizar_estadisticas(Unidad unidad) {
    	vAt.setText(unidad.getAt()+"");
    	vDefensa.setText(unidad.getDef()+"");
    	vPg.setText(unidad.getPg()+"");
    	imgCaraUnidad.setImage(unidad.getCara());
    }
    private void mostrar_marco_edificio() {}


    private void implementar_onkeypressed() {    	
    	base.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(casilla_actual!=null && casilla_actual.getUnidad()!=null)				
				switch(event.getCode()){
					case D:
						establecer_movimiento(DatosGlobales.DERECHA,2);
						break;
					case W:
						establecer_movimiento(DatosGlobales.ARRIBA,2);
						break;
					case  S:
						establecer_movimiento(DatosGlobales.ABAJO,2);
						break;
					case A:
						establecer_movimiento(DatosGlobales.IZQUIERDA,2);
						break;					
					default:
						break;
				}
				else if(um == null)
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

    private void obtener_coordenada_raton(MouseEvent raton) {
    	System.out.println(raton.getSceneX()+"+"+raton.getSceneY());
    	Casilla casillaUbicada = tablero.get_casilla(raton.getX(), raton.getY());
    	System.out.println(casillaUbicada.getX()+"+"+casillaUbicada.getY());

    	if(casillaUbicada.equals(tablero.comprobar_casilla_adyacente(casilla_seleccionada, casillaUbicada)))
    		System.out.println("Casilla adyacente");
    	else System.out.println("Caca");
    }
    private void recolectar(Unidad unidad) {
    	if(unidad instanceof Unidad) {}	//Si implementa la interfaz de recolectar, la unidad llama a su metodo recolectar
    	cancelar_accion();
    }
    
    private void construir(Unidad unidad) {
    	if(unidad instanceof Soldado) 	
    	edificio = new Edificio(new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+"castillo.png"),casilla_actual.getX()+100,casilla_actual.getY()+100,"castillo");
    	if(unidad instanceof Aldeano)
        edificio = new Edificio(new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+"aserradero.png"),casilla_actual.getX()+100,casilla_actual.getY()+100,"aserradero");

    	for(Casilla casilla : tablero.getCasillas().values()) 
    	{
    		if(edificio.contacto(casilla.getX(), casilla.getY(), DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE)) {
    			casilla.setEdificio(edificio);
    			edificio.casillas.add(casilla);
    			casilla.getBoton().setText("A");;
    			//Relacion bidireccional ostias, mucho más sencillo, el codigo limpio me puede comer los huevos
    		}
    	}
    }
    private void atacar(Casilla casillaAtacante, Casilla CasillaAtacada) {
    	uc = (UnidadCombate) casillaAtacante.getUnidad();
		uc.atacar(CasillaAtacada.getUnidad());  
		atacando = false;
    	tHistoria.appendText(casillaAtacante.getUnidad().getNombre()+" atacó a "+CasillaAtacada.getUnidad().getNombre()+".\n");
    	cancelar_accion();
    }   
	
	public void mover() {
		
		/*Si no se reinician los valores, las coordenadas conservarian la posicion de la anterior unidad seleccionada*/
		x = um.getCasillaOrigen().getX();
		y = um.getCasillaOrigen().getY();
	    
		coordenadaImagen = um.getUnidad().getAnimacion().getSprites(um.getDireccion()).get(sprite);  
		
		switch(um.getDireccion()) {
			case DatosGlobales.DERECHA:
				x = um.getCasillaOrigen().getX()+um.paso();
				break;
			case DatosGlobales.IZQUIERDA :
				x = um.getCasillaOrigen().getX()-um.paso();
				break;
			case DatosGlobales.ABAJO:
				y = um.getCasillaOrigen().getY()+um.paso();
				break;
			case DatosGlobales.ARRIBA :
				y = um.getCasillaOrigen().getY()-um.paso();
				break;			
		}		  

		lienzo.drawImage(um.getUnidad().getAnimacion().getSpriteSheet(), //la imagen con todos los movimientos
    			coordenadaImagen.getX(), coordenadaImagen.getY(), coordenadaImagen.getWidth(), coordenadaImagen.getHeight(), //aisla el sprite en concreto del spriteSheet
    			x, y,  //la coordenada para dibujar la imagen
    			coordenadaImagen.getWidth(), coordenadaImagen.getHeight());	//el tamaño de la imagen
		
    	um.sumarPaso();
    	
    	//termina el movimiento al avanzar una casilla 
    	if(um.paso() == DatosGlobales.CASILLA_SIZE)
    	{
    		um.getCasillaDestino().setUnidad(um.getUnidad());
    		casilla_actual = um.getCasillaDestino();
    		um = null;    
    		unidad_moviendose=false;
    		
    		if(corriendo)
    		{ 
    			num_casilla_camino++;

    			if(num_casilla_camino == camino_casillas.size()-1) {
    				corriendo = false;
    				borrar_camino();
        		} else { 	    	

    			correr();
    			System.out.println(num_casilla_camino);}}

    		
    	}		
	}
	public void movimiento_estatico() {
    	for(Casilla casilla : tablero.getCasillas().values()) {
    		
    		if(casilla.getUnidad()!=null) {
    		coordenadaImagen = casilla.getUnidad().getAnimacion().getDireccionActual().get(sprite);
        	lienzo.drawImage(casilla.getUnidad().getAnimacion().getSpriteSheet(), coordenadaImagen.getX(), coordenadaImagen.getY(), coordenadaImagen.getWidth(), coordenadaImagen.getHeight(), casilla.getBoton().getLayoutX(), casilla.getBoton().getLayoutY(),  coordenadaImagen.getWidth(), coordenadaImagen.getHeight());        	            	   
    		}
    	}
    	if(edificio!=null)
    	lienzo.drawImage(edificio.imagen, edificio.x, edificio.y);
	}
	public void actualizar_datos() {
		fps++;
    	if(fps == 10) {
    		sprite++; 
    		fps = 0;
    	}
    	
    	if(sprite>2)
    		sprite=0;
	}
    
    public void ciclo() {
    	AnimationTimer animationTimer = new AnimationTimer() {
    	
    	@Override
    	public void handle(long tiempoActual) {
    		    		
        	lienzo.drawImage(fondo,0,0);   

        	if(um == null) {
        		if(corriendo)
        			correr();
        	} else 
        		mover();
        	
      
        	movimiento_estatico();        	
        	actualizar_datos();
        	
        	for(Casilla casilla : camino_casillas)
            	lienzo.drawImage(new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"casilla_camino.png"), casilla.getX(),casilla.getY());
    	};
    };
    animationTimer.start();
    }
    

    public void establecer_movimiento(String direccion, int pasos) {	
    	
	    if(!unidad_moviendose && casilla_actual.getUnidad()!=null) {
	    	um = new UnidadEnMovimiento(casilla_actual, tablero.getCasillaAdyacente(casilla_actual, direccion), casilla_actual.getUnidad(), direccion);
	    	um.setPaso(pasos);
	    		
	    	if(um.getCasillaDestino()!=null && um.getCasillaDestino().getEdificio()==null && um.getCasillaDestino().getUnidad()==null) {
	    		um.getUnidad().getAnimacion().setDireccionActual(direccion);
	    		casilla_actual.setUnidad(null);    
	    		unidad_moviendose=true;
	    	}
	    	else //la casilla a la que intenta desplazarse no existe o tiene un obstáculo, se cancela el movimiento	    			
	    		um = null;
	    }
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
}



