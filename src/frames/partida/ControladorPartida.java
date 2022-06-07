package frames.partida;


import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import procesos.cargar.CargadoMapa;
import procesos.habilidades.Habilidad;
import procesos.habilidades.HabilidadArea;
import procesos.habilidades.HabilidadDiana;
import procesos.habilidades.animacion.AnimacionAtaque;
import procesos.habilidades.pasividad.InHabilidadPasiva;
import procesos.habilidades.*;
import procesos.lienzo.Lienzo;
import java.util.ArrayList;
import java.util.HashMap;

import audio.Musico;
import bean.botones.BotonBestiario;
import bean.botones.BotonEdificio;
import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.edificios.EdificioCombate;
import bean.edificios.EdificioPrincipal;
import bean.edificios.EdificioProduccion;
import bean.jugador.Jugador;
import bean.mapas.DatosMapa;
import bean.recurso.Recurso;
import bean.tablero.Key;
import bean.tablero.Tablero;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import bean.unidades.unidades.UnidadCombate;
import bean.unidades.unidades.UnidadProduccion;
import procesos.acciones.*;
import procesos.acciones.AccionesDTO.Accion;
import procesos.acciones.AccionesDTO.AccionAlojar;
import procesos.acciones.AccionesDTO.AccionAndar;
import procesos.acciones.AccionesDTO.AccionAtacar;
import procesos.acciones.AccionesDTO.AccionConstruir;
import procesos.acciones.AccionesDTO.AccionCorrer;
import procesos.acciones.AccionesDTO.AccionDesalojar;
import procesos.acciones.AccionesDTO.AccionHabilidad;
import procesos.acciones.AccionesDTO.AccionHabilidadArea;
import procesos.acciones.AccionesDTO.AccionHabilidadDiana;
import procesos.acciones.AccionesDTO.AccionHabilidadLineal;
import procesos.acciones.AccionesDTO.AccionProducir;
import procesos.acciones.accionesPartida.construir.ConstruirCimientos;
import procesos.acciones.accionesPartida.construir.ControladorEdificios;
import procesos.acciones.accionesPartida.mover.AccionMover;
import procesos.acciones.accionesPartida.mover.ControladorMovimiento;

/**Clase encargada de escuchar y gestionar todos los eventos detonados durante la partida fruto de las acciones del jugador 
 * así como dibujarlos por pantalla. Mantiene una relación bidireccional con el ComunicadorPartida, a quien le envía las acciones
 * que ejecuta el jugador y a quien le cede su control cuando es el turno del contrincante.*/
public class ControladorPartida {
	
	private final String HOST = "HOST";
	private final String CLIENTE = "CLIENTE";
	
	//Las tablas que componen la vista gráfica del jugador
	GridPane tabla_recursos;
	GridPane tabla_opciones;
	GridPane tabla_inferior;
	
	GridPane tabla_acciones;
    ArrayList<Button> botones_acciones;
    ArrayList<Button> botones_habilidades;
      
	GridPane tabla_produccion;
    ArrayList<Button> botones_produccion;
    ArrayList<BotonEdificio> botonesEdificio;
    
	GridPane tabla_alojamiento;
    ArrayList<Button> botones_alojamiento;
	
	GridPane tabla_estadisticas;	
    ArrayList<Text> estadisticas;    
	
    Comunicador comunicador;
    
    //Los nodos que componen cada tabla de la vista gráfica del jugador
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
    Button btnAtacar;
    @FXML
    Button btnAlojar;
    @FXML
    Button btnCancelar;
    @FXML
    Button btnHabilidad1;
    @FXML
    Button btnHabilidad2;
    @FXML
    Button btnHabilidad3;
    @FXML
    ImageView img_marco_inferior;
    @FXML
    ImageView imgCaraUnidad;
    @FXML
    Text vAt;
    @FXML
    Text vDefensa;
    @FXML
    Text vPg;
    @FXML
    Text vMovimiento;
    @FXML
    Text vMana;
    @FXML
    Text tMana;
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
    @FXML
    Text tHabilidades;
    @FXML
    Text tConstruccion;
    
    Tablero tablero;    
    Lienzo lienzo;    
    Image fondo;
    

    private int numUnidad;
    
    //La matriz que compone un camino para la acción correr: las casillas sobre las que se desplaza la unidad y las direcciones que deben tener sus sprites al ser dibujadas
    private ArrayList<Casilla> camino_casillas;
    private ArrayList<String> camino_direcciones;
    
    //La casilla a la que se le van a a aplicar las acciones
    Casilla casilla_seleccionada;
    
    //La casilla desde donde se pretende realizar las acciones
    Casilla casilla_actual;
    
    //Casilla destino de un acción andar
    Casilla casillaDestino;
    
    //La última casilla de un camino
    private Casilla casilla_auxiliar_camino;
    
    //La casilla sobre la que se va a dibujar una habilidad lineal que aun no ha sido construida
    private Casilla casilla_auxiliar_habilidad;
    
    //Indica si es el turno del jugador
    boolean turno;

    //Indica que el jugador ha pulsado el botón atacar
    private boolean atacando;
    
    //Indica que el jugador ha pulsado un botonEdificio
    private boolean construyendo;
    
    //Indica que el jugador ha pulsado el botón alojar
    private boolean alojandose;
    
    //Indica que el jugador ha pulsado un boton del marco de unidades alojadas de un edificio
    private boolean desalojandose;
    
    //La unidad que el jugador pretende desalojar de un edificio.
    private Unidad unidad_desalojada;
    
    //Indica que el ha pulsado un botónEdificio del marco de producción de una unidad de producción
    private Edificio edificio_construyendose;

    //Indica que el jugador ha comenzado a construir un camino para la acción correr
    private boolean camino_construyendose;
        

    //La unidad obtenida al pulsar un botonBestiario del marco de producción de un edificio de combate4
    private Unidad unidad_produciendose;
    
    //La habilidad que el jugador ha pulsado del array de botones habilidad
    private Habilidad habilidad_seleccionada;

    //Indica si la habilidad seleccionada ya tiene los valores establecidos para su ejecución
    private boolean habilidad_construida;
    
    //Habilidades cuyos efectos se aplicarán o se quitarán en el siguiente turno
    private ArrayList<Habilidad> habilidades_pasivas;
    
    //Habilidades cuya animación es estática y se dibuja durante todo el turno
    private ArrayList<Habilidad> habilidades_estaticas;
 
    ControladorEdificios controlador_edificios;
    ControladorMovimiento controlador_movimientos;  
    
    //El rectangulo que representa el sprite de un spritesheet
    private Rectangle coordenadaImagen; 
    //La posición del sprite que se quiere recuperar de un spritesheet
    private int sprite = 0; 
    //El número de frames por segundo que han corrido
    private int fps = 0;    
    
    //Las coordenadas de la casilla por la que ha pasado el ratón. Usada por el hiloPintor para seguir el rastro del ratón
    private double x;
    private double y;
    
    private int iterador;
    private AnimacionAtaque anmAt;
    
    Jugador jugador;
    private UnidadCombate uc;
    
    public Tablero obtener_tablero() {
    	return tablero;
    }
    @FXML
    void terminar_turno(ActionEvent event) {
    	termina_turno();
    }

    /**Termina el turno del jugador. Cambia el boolean turno a false para que no se lean eventos ni por teclado ni por ratón, impide cualquier interacción 
     * con las opciones del juego y llama al método del comunicador terminar_turno para que el contrincante reciba la acción correspondiente.*/
    private void termina_turno() {
    	turno = false;
    	tabla_inferior.setVisible(false);
    	tabla_opciones.setVisible(false);
    	comunicador.terminar_turno();
    }
    
    /**Comienza el turno del jugador. Cambia el boolean turno a true para permitir interactuar con el teclado y el ratón. Muestra las tablas del juego para
     * y llama al método recargar() con objeto de actualizar los datos del turno.*/
    public void comenzar_turno() {
    	turno = true;
    	tabla_inferior.setVisible(true);
    	tabla_opciones.setVisible(true);
    	casilla_actual = null;
    	recargar();    	
    	
    }
    /**Actualiza los datos del turno. Suma al jugador los recursos de cada edificio de producción que tenga el controlador de edificios, cada unidad recupera
     * su cantidad correspondiente de maná y todas las habilidades pasivas del arrayList creadas el turno anterior quitan su efecto y se eliminan*/
    private void recargar() {
    	for(Recurso recurso : controlador_edificios.getGanancias(jugador.getBando()))
    		jugador.sumar_recurso(recurso.getRecurso(), recurso.getCantidad());
    	actualizar_recursos();
    	
    	for(Unidad unidad : tablero.getUnidades()) { 
    		unidad.recuperarMana();
    		unidad.recuperar_mov();
    		unidad.setAccionRealizada(false);
    	}
    	
    	for(Habilidad habilidad : habilidades_pasivas)
    		((InHabilidadPasiva)habilidad).quitar_efecto();
    	habilidades_pasivas.clear();
    	habilidades_estaticas.clear();
    }
    
    /**Inicializa los datos necesarios del controlador: Crea al jugador, inicializa los arraylists, construye el edificio principal, implementa los eventos de 
     * teclado y ratón e inicia el subhilo ciclo.*/
    public void inicializar(double[] castillo, double[] castilloCliente, String bando) {	
    	jugador = new Jugador();
				
    	anmAt = new AnimacionAtaque(50,new Image(DatosGlobales.rutas.getProperty("imgEfectos")+"atacar.png"),32,3);
    	casilla_actual = tablero.getCasillas().get(new Key(0.0,0.0));    		
    	controlador_movimientos = new ControladorMovimiento();
    	camino_casillas = new ArrayList<Casilla>();
    	camino_direcciones = new ArrayList<String>();    	
    	controlador_edificios = new ControladorEdificios();
    	habilidades_pasivas = new ArrayList<Habilidad>();
    	habilidades_estaticas = new ArrayList<Habilidad>();
    	
    	jugador.setBando(bando);
    	System.out.println("BANDO DEL JUGADOR : "+jugador.getBando());
    	construir_edificio_inicial(DatosGlobales.edificios.get(1),castillo,HOST);
    	construir_edificio_inicial(DatosGlobales.edificios.get(1),castilloCliente,CLIENTE);

    	implementar_eventos();    	    	    	    
		hiloPintor();     	    
    }
    
    /**Implementa los eventos que lanzarán las casillas en función del evento detonado sobre el botón que encapsulan*/
    private void implementar_eventos() {    	
    	implementar_click_izquierdo();
    	implementar_click_derecho();  
    	implementar_onkeypressed();    	 
    	implementar_onMouseEntered();
    }

    /**Implementa el evento click izquierdo sobre los botones del tablero. Al detonar el evento, busca la casilla que encapsula a dicho botón
     * y comprueba si se debe realizar alguna acción sobre la casilla y de ser así lo ejecuta. De lo contrario, la casilla cuyo botón ha sido cliqueado
     * pasa a ser la casilla actual.*/
    private void implementar_click_izquierdo() {
    	for(Casilla casilla : tablero.getCasillas().values()) {   
    		casilla.getBoton().setOnAction(new EventHandler() {
   				@Override
				public void handle(Event event) {
   					if(turno) {
   					if(!habilidad_construida) {				//El juego se detiene mientras una habilidad esté dibujandose
   						buscar_casilla_seleccionada(event);	//Se obtiene la casilla cliqueada
   						if(comprobar_acciones()) {			//Si hay una accion iniciada, se ejecuta sobre la casilla.
   							if(ejecutar_acciones()) 		//Si se ha podido ejecutar la acción devuelve true;
   								actualizar_marco();			//Se actualiza el marco inferior   							
   						}
   						else { 		
   							actualizar_casilla_actual();	//Si no hay ninguna acción, la casilla cliqueada pasa a ser la actual
   							actualizar_marco();				//Se actualiza el marco inferior
   						}
   					}
   				}
   				}
    		});
    	};		
    }
    /**Implementa el evento setOnMouseClicked a cada boton de cada casilla del tablero concretando únicamente el click derecho.
     * Primero comprueba que no haya ninguna habilidad ejecutándose para evitar modificar los valores de la misma. En caso contrario:\n
     * Si la casilla tiene una unidad y hay un camino construyéndose, este se borra; el camino no puede terminar sobre otra unidad.
     * Si la casilla tiene una unidad y no hay un camino construyendose, comienza su construcción y le agrega la primera casilla.
     * Si la casilla no tiene unidad y hay un camino construyendose termina su construcción, agrega la última casilla y comienza el recorrido. 
     * Esta última condición también comprueba si hay un edificio construyéndose y de ser así lo borra.
     * */
    private void implementar_click_derecho() {
    	for(Casilla casilla : tablero.getCasillas().values()) {   
    		casilla.getBoton().setOnMouseClicked(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				//Si una unidad es objetivo de una habilidad y este se desplaza, podría esquivar la habilidad y esta avanzaría hasta el infinito.
    			if(turno) {
    				if(habilidad_seleccionada == null) {
	    				if(event.getButton().equals(MouseButton.SECONDARY) && casilla.getUnidad()!=null) {
	    					if(camino_construyendose) {    			
	    						camino_construyendose = false;
	    						borrar_camino();
	    					}
	    					else if(casilla.getUnidad().getMov()>1) {
	    						camino_construyendose = true;
	    						casilla.setCamino(true);
	    						camino_casillas.add(casilla);
	    					}    					
	    				}
	    				else if(event.getButton().equals(MouseButton.SECONDARY)) {
	    					if(construyendo)
	    						borrar_accion();
	    					else if(camino_construyendose)
	    						if(camino_casillas.size()>1){
	    							correr();
	    							borrar_camino();
	    						}     					
	    				}
    				}
    			}
    		}
    	});
    	}
    }
    /**Implementa el evento onMouseEntered correspondiente a la acción de pasar el ratón sobre una casilla. Este comprueba dos 
     * condiciones posibles:
     * - Si se está construyendo un camino y la casilla está vacía, comprueba que sea adyacente a la última casilla del camino: en caso afirmativo
     * la agrega al camino y en caso contrario se ignora.
     * - Si hay un edificio construyendose, se obtienen las coordenadas de la casilla con objeto de que posteriormente el lienzo dibuje el edificio en su ubicación.
     * - Si hay una habilidad seleccionada y sin construir, la casilla auxiliar adopta el valor dicha casilla con objeto de que el lienzo dibuje el área de la habilidad. 
     */
    private void implementar_onMouseEntered() {
    	for (Casilla casilla : tablero.getCasillas().values()){
    		casilla.getBoton().setOnMouseEntered(new EventHandler<MouseEvent>() {

    			@Override
    			public void handle(MouseEvent event) {
    				
    			if(turno) {
    				if(!casilla.getCamino()) {	   
    					if(camino_construyendose && casilla.getUnidad()==null && casilla.getEdificio()==null && !casilla.isObstaculo()) {
    						if(casilla_actual.getUnidad().getMov()>camino_casillas.size())
		        				if(tablero.comprobar_casillas_adyacentes(casilla, camino_casillas.get(camino_casillas.size()-1))) {
		        					camino_direcciones.add(tablero.obtener_direccion_adyacente(casilla,camino_casillas.get(camino_casillas.size()-1)));
		    						camino_casillas.add(casilla);
		        					casilla.setCamino(true);
		        				}
	    				}    				
	    			}    

    				if(construyendo) {
    					x = casilla.getX();
    					y = casilla.getY();
    				}
    				else if (habilidad_seleccionada !=null && habilidad_construida==false) {
    					casilla_auxiliar_habilidad = casilla;
    				}
    			}
    			}
    			});
    	}
    }

    
    
    /**Crea la acción de andar. Primero comprueba que el movimiento sea posible: el controlador devolverá true si las casillas origen
     * y destino son adyacentes. Luego pasa el valor de la casilla destino a la variable casillaDestino y crea la acción de andar. Por último borra
     * la casilla_actual y comprueba si es el turno del jugador para enviar la acción correspondiente al comunicador.
     * PD: solo puede haber una acción andar ejecutándose simultaneamente y, cuando una unidad comienza a andar, se encuentra en un estado ambiguo entre su casilla
     * origen y su casilla destino ya que la unidad se dibuja en sus zonas limítrofes. Por tanto, el controladorPartida pierde el valor de la unidad y la obtiene 
     * el controlador de movimientos, quien se lo seteará a la casilla destino una vez el movimiento haya terminado*/
    void crear_accion_andar(String direccion) {
		if(controlador_movimientos.comprobar_movimiento_posible(casilla_actual,tablero.getCasillaAdyacente(casilla_actual,direccion))) {
			casillaDestino = tablero.getCasillaAdyacente(casilla_actual, direccion);		
			controlador_movimientos.crear_accion_andar(casilla_actual, casillaDestino, direccion, DatosGlobales.PASOS_ANDAR);
			if(comunicador.miTurno()) {
				Accion accion = new AccionAndar(casilla_actual,DatosGlobales.ACCION_ANDAR,casillaDestino,direccion);
				comunicador.enviar_accion(accion);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			casilla_actual = null;
		}
    }
    /**Implementa el evento correspondiente a pulsar una tecla. Si se pulsa las teclas WASD mientras la casilla actual tiene una unidad, dicha unidad
     * iniciará la acción de andar hacia la dirección correspondiente (A->Izuqierda, W->Arriba,S ->Abajo, D->Derecha), siempre y cuando no haya una habilidad
     * seleccionada para evitar modificar sus valores. En caso contrario, comprueba que la casilla destino sea null (no haya ninguna unidad moviendose hacia otra casilla)
     * y de ser así desplazará el scroll en las direcciones anteriormente mencionadas. */
    private void implementar_onkeypressed() {    	

    	base.setOnKeyPressed(new EventHandler<KeyEvent>() {
    		
			@Override
			public void handle(KeyEvent event) {
			if(turno) {
				if(casilla_actual!=null && casilla_actual.getUnidad()!=null && habilidad_seleccionada == null && casilla_actual.getUnidad().getMov()>0)				
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
				else if(casillaDestino==null)
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
				
			}
		
			}});    	
    }
    
    /**Este método solo es llamado únicamente por el evento onAction de un botón. Aisla el id de la ruta del evento
     *y obtiene la casilla que contenga el botón con dicho identificador, 
     *@param event Evento onAction detonado por un botón del array tablero*/
    private void buscar_casilla_seleccionada(Event event) {
    	
    	String[] ids = event.getTarget().toString().split("\\[");
    	String id = ids[1].split(",")[0].split("=")[1];
    	
    	
    	for(Casilla casilla : tablero.getCasillas().values())    
    		if(casilla.getBoton().getId().equals(id)) {
    			casilla_seleccionada = casilla;
    			break;
    		}
        }
    
    /**Comprueba si se está realizando alguna acción.*/
    private boolean comprobar_acciones() {
    	if(atacando||construyendo||alojandose||desalojandose||habilidad_seleccionada!=null||unidad_produciendose!=null)
    		return true;
    	return false;
    } 

    
    /**Ejecuta las acciones cuyo análogo booleano sea true y le envía la acción al comunicador para que lo envíe al contrincante.*/
    private boolean ejecutar_acciones() {
    	
    	if(habilidad_seleccionada!=null) {
    		
    		if(habilidad_seleccionada instanceof HabilidadDiana) {
    			
    			if(casilla_seleccionada.getUnidad()!=null) {
    				HabilidadDiana habilidad = (HabilidadDiana) habilidad_seleccionada;
    			
    				if(habilidad.comprobar_habilidad_posible(casilla_actual, casilla_seleccionada)) {
    					String direccion = tablero.obtener_direccion(casilla_seleccionada,casilla_actual);
    					habilidad.establecer_valores(casilla_actual, casilla_seleccionada, direccion);
    					ejecutar_habilidad(habilidad);
    					AccionHabilidad accion = new AccionHabilidadDiana(casilla_actual,DatosGlobales.ACCION_HABILIDAD,habilidad,casilla_seleccionada,direccion);
    					comunicador.enviar_accion(accion);
    					
    					return true; 
    				}
    				else tHistoria.appendText("La casilla está demasiado lejos\n");    		
    			}
    			else tHistoria.appendText("Seleccione a una unidad\n");
    		}
    		else if(habilidad_seleccionada instanceof HabilidadLineal) { 
	    		HabilidadLineal habilidad = (HabilidadLineal) habilidad_seleccionada;
	    		String direccion = tablero.obtener_direccion(casilla_seleccionada,casilla_actual);
	    		habilidad.establecer_valores(casilla_actual, tablero.obtener_linea_casillas_adyacentes(casilla_actual, direccion, habilidad.getDistancia()));
	    		ejecutar_habilidad(habilidad);
	    		AccionHabilidad accion = new AccionHabilidadLineal(casilla_actual, habilidad, direccion);
	    		comunicador.enviar_accion(accion);
	    		
	    		return true;
    		}

    	}
    	else if(unidad_produciendose !=null) {
    		return producir_unidad(casilla_actual, unidad_produciendose,casilla_seleccionada);
    		
    	}
    	else if(desalojandose)
    		return desalojar(casilla_actual.getEdificio(), casilla_seleccionada, casilla_actual, numUnidad);    	
    	
    	else if(tablero.comprobar_casilla_adyacente(casilla_actual, casilla_seleccionada)) {    	
    		System.out.println("Casilla actual : "+casilla_actual.getX()+","+casilla_actual.getY()+", casillaSeleccionada : "+casilla_seleccionada.getX()+","+casilla_seleccionada.getY()+", adyacencia : "+tablero.comprobar_casilla_adyacente(casilla_actual, casilla_seleccionada));
    		//Atacar
	    	if(atacando) {
	    		if(casilla_seleccionada.getUnidad()!=null || casilla_seleccionada.getEdificio()!=null) {
	    			atacar(casilla_actual,casilla_seleccionada);
	    			return true;
	    		}
	    		else tHistoria.appendText("Seleccione una unidad a la que atacar\n");
	    	}	    	
		   	else if(construyendo) 
		   			return construir(edificio_construyendose, casilla_seleccionada);
		   	else if(alojandose) 
		    	if(casilla_seleccionada.getEdificio()!=null)
		    		return alojar(casilla_seleccionada.getEdificio(), casilla_actual,casilla_seleccionada);		   	
	    }
	    else 
	    	tHistoria.appendText("Seleccione una casilla adyacente a la unidad.\n");   
    	return false;
	}
    
    /**Crea una unidad en una casilla del tablero. Crea una unidad de combate o de producción y comprueba si se puede agregar a la casilla:
     * si la casilla seleccionada no tiene otra unidad, edificio y obstáculo y que esta se encuentre adyacente al edificio desde el que se
     * pretende producir la undiad. Para saber si la casilla es adyacente, comprueba que alguna casilla del array de casillas del edificio
     * sea adyacente a la misma: en caso afirmativo setea la unidad en dicha casilla, actualiza los recursos del jugador si es su turno y 
     * envía la acción de producir unidad al comunicador, en caso contrario se borra la acción y se comunica por pantalla que seleccione una
     * casilla adyacente. */
    boolean producir_unidad(Casilla casillaActual, Unidad unidad, Casilla casillaSeleccionada){

    	Unidad unidadProducida = null; 
    	if(unidad instanceof UnidadCombate)
    		unidadProducida = new UnidadCombate(unidad);
    	if(unidad instanceof UnidadProduccion)
    		unidadProducida = new UnidadProduccion(unidad);
    	
    	if(casillaSeleccionada.getUnidad()!=null||casillaSeleccionada.getEdificio()!=null || casillaSeleccionada.isObstaculo()) {
    		tHistoria.appendText("Seleccione una casilla desocupada");
    		return false;
    	}    	
    	for(Casilla casilla : casillaActual.getEdificio().getCasillas()) 
				if(tablero.comprobar_casillas_adyacentes(casilla, casillaSeleccionada) ) {
					Musico.reproducir_accion(DatosGlobales.ACCION_PRODUCIR);
					casillaSeleccionada.setUnidad(unidadProducida);
					tHistoria.appendText("Se ha creado un "+unidad.getNombre());
					if(turno) {
						unidadProducida.setBando(jugador.getBando());
						restar_recursos(unidadProducida);
						actualizar_recursos();
						comunicador.enviar_accion(new AccionProducir(casillaSeleccionada,casillaActual,unidadProducida));
					}
					borrar_accion();
					return true;
				}
    	
    	tHistoria.appendText("Seleccione una casilla adyacente");
    	return false;
    }
    
    
    /**Ejecuta la habilidad recibida como parámetro. Primero comprueba si la habilidad es pasiva y de ser así la agrega al array de habilidades pasivas en caso de que 
     * sea su turno. Si no tiene animación se considera una habilidad estática (es una imagen que se dibuja durante todo el turno) por lo que aplica su efecto y se elimina
     * su referencia así como se llama la método borrar_accion. En caso de no ser estática y por tanto tener una animación, la variable habilidad_seleccionada adquiere su valor
     *  y el boolean habilidad_construido pasa a true para que el hilo pintor la dibuje.*/    
    void ejecutar_habilidad(Habilidad habilidad) {
		if(habilidad instanceof InHabilidadPasiva && turno) 
			habilidades_pasivas.add(habilidad);    
		if(habilidad.getAnimacion()==null) {
			habilidades_estaticas.add(habilidad);
			habilidad.aplicar_efecto();
			Musico.reproducir_habilidad(habilidad.getNombre());
			habilidad = null;
			borrar_accion();
		} else {
			habilidad_seleccionada = habilidad;
			habilidad_construida = true;
			Musico.reproducir_habilidad(habilidad_seleccionada.getNombre());

		}

    }
   
    /**Desaloja una unidad dentro de un edificio hacia una casilla seleccionada. Primero comprueba que la casilla esté libre, luego que alguna casilla del 
     * array de casillas el edificio sea adyacente. Si se cumple las condiciones, setea la unidad en la casilla seleccionada, la elimina del edificio y 
     * comprueba si es su turno y debe actualizar las estadisticas y enviar la acción al comunicador o no.Si no se cumplen las condiciones, se  comunica 
     * por pantalla que debe seleccionar una casilla adyacente o una desocupada*/    
    boolean desalojar(Edificio edificio, Casilla casilla_seleccionada, Casilla casilla_actual, int numUnidad) {
	
    	if(casilla_seleccionada.getEdificio()==null&&casilla_seleccionada.getUnidad()==null&&!casilla_seleccionada.isObstaculo()) {
    	for(Casilla casilla : edificio.getCasillas()) 
   				if(tablero.comprobar_casillas_adyacentes(casilla, casilla_seleccionada)) {
   					if(comunicador.miTurno()) {
   						AccionDesalojar accion = new AccionDesalojar(casilla_actual,casilla_seleccionada, numUnidad);
   						comunicador.enviar_accion(accion);
   					}
   					Unidad unidadDesalojada = edificio.getUnidadesAlojadas().get(numUnidad);					
   					casilla_seleccionada.setUnidad(unidadDesalojada);
   					casilla_actual = casilla_seleccionada;
					edificio.desalojar(unidadDesalojada);
   					borrar_accion();
   					return true;
   				}
   			tHistoria.appendText("Seleccione una casilla adyacente al edificio para desalojar la unidad\n"); 
    	}
    	tHistoria.appendText("No puede desalojar la unidad encima de un edificio");
   			return false;
    }
    
    /**Permite alojar una unidad del tablero a un edificio. Primero comprueba que el edificio en cuestión disponga de espacio para alojar la undiad.
     * De ser así, el edificio agrega en su array de unidades alojadas a la unidad en cuestión(edificio.alojar(casilla_actual.getUnidad())), borra la 
     * unidad de la casilla en la que se encontraba y la casilla_actual ahora pasa a ser la del edifcio. En caso de que sea el turno del jugador, se
     * envia la acción al comunicador.*/
    boolean alojar(Edificio edificio, Casilla casilla_actual, Casilla casilla_seleccionada) {
    	if(edificio.comprobar_alojamiento()) {
    		edificio.alojar(casilla_actual.getUnidad());
    		tHistoria.appendText(casilla_actual.getUnidad().getNombre()+" se ha alojado en "+casilla_seleccionada.getEdificio().getNombre());
    		if(turno) {
	    		AccionAlojar accion = new AccionAlojar(casilla_actual,casilla_seleccionada,edificio);
	    		comunicador.enviar_accion(accion);
    		}
    		casilla_actual.setUnidad(null);
    		casilla_actual=casilla_seleccionada;
    		borrar_accion();
    		return true;
    	}
    	else
    		tHistoria.appendText("No caben más unidades en el edificio\n");
    	return false;
    }

    /**Método llamado en caso de que a la casilla seleccionada no se le deba realizar ningúna acción, en cuyo caso esta
     * debería ser ahora la casilla actual. Si la casilla está ocupada por un edificio o unidad del jugador contrincante, 
     * se impide que se intercambien los valores de la casilla para que el jugador no pueda controlar sus objetos ajenos.*/
    private void actualizar_casilla_actual() {
    	boolean confirmacion = false;    	    
    	
    	if(casilla_seleccionada.getUnidad()!=null)
    		if(casilla_seleccionada.getUnidad().getBando().equals(jugador.getBando())) {
    			confirmacion = true;
    			System.out.println(casilla_seleccionada.getUnidad().getBando());
    		}
    	    	
    	if(casilla_seleccionada.getEdificio()!=null) 
    		if( casilla_seleccionada.getEdificio().getBando().equals(jugador.getBando()))
    			confirmacion = true;
    	if(confirmacion) {
  			casilla_actual = casilla_seleccionada;  
			casilla_seleccionada = null;
    	}
    }
    /**Actualiza los valores del marco inferior en función de si la casilla actual tiene una unidad de combate o de producción
     * y posteriormente actualiza las estadísticas. Si en lugar de unidad tiene un edificio, muestra su marco.
     * Si no se cumple ninguna condición, muestra el marco vacío*/
    private void actualizar_marco() {

		if(casilla_actual.getUnidad()!=null) {
			
			if(casilla_actual.getUnidad() instanceof UnidadCombate) 
				mostrar_marco_combate();		
			
			else if(casilla_actual.getUnidad() instanceof UnidadProduccion) 
				mostrar_marco_produccion();
			
			cambiar_estadisticas(casilla_actual.getUnidad());
		}
		else if(casilla_actual.getEdificio()!=null) 
			mostrar_marco_edificio();
		else
			mostrar_marco_vacio();
    }
    
    /**Oculta todas las subtablas del marco inferior. Es llamado cuando la casilla actual es null, es decir, que no hay ninguna 
     * casilla cliqueada*/
    private void mostrar_marco_vacio() {
    	setVisibilidadBotones(false,botones_acciones);
    	setVisibilidadBotones(false,botones_habilidades);
    	setVisibilidadBotones(false,botones_produccion);
    	setVisibilidadBotones(false, botones_alojamiento);    	
    	setVisibilidadEstadisticas(false);
    }
   
    //Subhilo encargado de dibujar los objetos de la partida
    public void hiloPintor() {
    	AnimationTimer animationTimer = new AnimationTimer() {    	
    	@Override
    	public void handle(long tiempoActual) {
			
        	lienzo.dibujar_imagen(fondo,0,0);   //Dibuja el mapa
        	mover();        					//Mueve las unidades y las dibuja
        	dibujar_edificios();				//Dibuja los edificios
        	actualizar_fps();					//Actualiza los frames y los sprites
        	dibujar_camino();					//Dibuja los caminos en construcción
        	dibujar_ataque();					//Dibuja la animacion de ataque si se ha atacado
        	dibujar_habilidades_estaticas();	//Dibuja las habilidades estáticas
        	dibujar_habilidad();				//Dibuja la habilidad lanzada
        	comprobar_casilla_auxiliar_camino();		//comprueba las condiciones de la casilla auxiliar        	
    	};
    	};
    animationTimer.start();
    }
    
    /**Comprueba si la animación de ataque no es null(si se ha realizado la acción de atacar previamente). De ser así,
     * el lienzo dibuja el sprite de ataque y se suma un fps a la animación. Luego se comprueba si los fps han llegado 
     * hasta el tiempo de duración de la animación y lo finaliza (resetea sus valores)*/
    private void dibujar_ataque() {
    	if(anmAt.getAtaque()!=null) {
    		lienzo.dibujar_unidad(anmAt.getImagen(),anmAt.obtener_sprite(), anmAt.getAtaque().getX(), anmAt.getAtaque().getY());
    		anmAt.sumar_fps();
			if(anmAt.comprobar_animacion_terminada()) 
				anmAt.finalizar();
    	}
    }
    /**Dibuja las habilidades que hayan sido construidas pero no hayan iniciado o terminado su animación. Comprueba dos condiciones:
     * 1º - Cuando se construye una habilidad lineal, se debe dibujar el alcance de la misma para que el jugador pueda elegir correctamente 
     * la ubicación de su ejecución. Por tanto comprueba que haya una habilidad seleccionada, que la casilla auxiliar habildad no sea null
     * y que la habilidad seleccionada en cuestión sea lineal. De ser así, obtiene la direccion de la casilla auxiliar respecto a la actual,
     * y obtiene una linea de casillas adyacentes hasta alcanzar la distancia de la habilidad. Sobre dicha línea (un array de casillas), se dibuja
     * el icono de la habilidad.
     * 2º - Si la habilidad ha sido construida(se la seleccionado la ubicación de su ejecución) se comprueba qué tipo de habilidad es y se llama
     * a su animación correspondiente. Luego comprueba si la animación ha terminado y de ser así aplica su efecto y la borra */
    private void dibujar_habilidad() {
    	
    	if(habilidad_seleccionada!=null && habilidad_seleccionada instanceof HabilidadLineal && casilla_auxiliar_habilidad!=null) {
	    	String direccion = tablero.obtener_direccion(casilla_auxiliar_habilidad,casilla_actual);
	    	ArrayList<Casilla> casillas_linea = tablero.obtener_linea_casillas_adyacentes(casilla_actual, direccion, ((HabilidadLineal)habilidad_seleccionada).getDistancia());
	   		if(casillas_linea.size()!=0)
	    		for(Casilla casilla : casillas_linea)
	    			lienzo.dibujar_imagen(new Image(DatosGlobales.rutas.getProperty("imgIconos")+"casilla_camino.png"), casilla.getX(),casilla.getY());	    		
    	}
    	
    	if(habilidad_construida) {
    		if(habilidad_seleccionada instanceof HabilidadDiana) {    			
    			HabilidadDiana diana = (HabilidadDiana) habilidad_seleccionada;
    			diana.mover_animacion();
    			lienzo.dibujar_imagen(diana.getImagen(), diana.getAnimacion().getX(), diana.getAnimacion().getY());
    			if(diana.getAnimacion().comprobar_animacion_terminada()) {
    				diana.aplicar_efecto();
    				borrar_accion();
    			}    			
    		}
    		if(habilidad_seleccionada instanceof HabilidadArea) {
    			HabilidadArea area = (HabilidadArea) habilidad_seleccionada;
    			if(area.animarSoloUnidades()) {
    				for(Casilla casilla : area.getCasillasAfectadas())
    					if(casilla.getUnidad()!=null)
    						lienzo.dibujar_unidad(area.getImagen(),area.getAnimacion().obtener_sprite(),casilla.getX(), casilla.getY());
    			}
    			else 
    				for(Casilla casilla : area.getCasillasAfectadas())
    					lienzo.dibujar_unidad(area.getImagen(),area.getAnimacion().obtener_sprite(),casilla.getX(), casilla.getY());
    		
    			area.getAnimacion().sumar_fps();
    			if(area.getAnimacion().comprobar_animacion_terminada()) {
    				area.aplicar_efecto();
    				borrar_accion();
    			}    
    		}
    		if(habilidad_seleccionada instanceof HabilidadLineal) {
    			HabilidadLineal linea = (HabilidadLineal) habilidad_seleccionada;
    			for(Casilla casilla : linea.getCasillasAfectadas())
    				lienzo.dibujar_unidad(linea.getImagen(),linea.getAnimacion().obtener_sprite(),casilla.getX(),casilla.getY());
    			
    			linea.getAnimacion().sumar_fps();
    			if(linea.getAnimacion().comprobar_animacion_terminada()) {
    				linea.aplicar_efecto();
    				borrar_accion();
    			}    
    		}
    		
    	}    	
    }
    /**Dibuja las habilidades estáticas*/
    private void dibujar_habilidades_estaticas() {
    	for(Habilidad habilidad : habilidades_estaticas) { 
    			
    		if(habilidad instanceof HabilidadLineal) {
    				HabilidadLineal linea = (HabilidadLineal) habilidad;
    				for(Casilla casilla : linea.getCasillasAfectadas())
    					lienzo.dibujar_imagen(habilidad.getImagen(), casilla.getX(), casilla.getY());
    			}
    		if(habilidad instanceof HabilidadDiana) {
    			HabilidadDiana diana = (HabilidadDiana)habilidad;
    			lienzo.dibujar_imagen(diana.getImagen(), diana.getCasillaObjetivo().getX(), diana.getCasillaObjetivo().getY());
    		}
    		if(habilidad instanceof HabilidadArea) {
    			HabilidadArea area = (HabilidadArea)habilidad;
    			for(Casilla casilla : area.getCasillasAfectadas())
					lienzo.dibujar_imagen(habilidad.getImagen(), casilla.getX(), casilla.getY());
    		}
    	}
    	
    }

    /**Comprueba si la casilla auxiliar camino tiene valor(se ha ejecutado una acción correr). Si tiene una unidad, quiere
     * decir que la unidad ha terminado de recorrer el camino. Si la casilla actual tiene valor y no tiene una unidad, quiere
     * decir que mientras la unidad recorria su camino el jugador no ha seleccionado otra casilla sobre la que interactuar. 
     * Si se cumplen estas condiciones anidadas, quiere decir que el jugador quiere retomar el control de la unidad que ha
     * terminado de correr, por lo que el valor de la casilla actual pasa a ser el del final del camino, y la casilla auxiliar 
     * camino pasa a ser null. */
    private void comprobar_casilla_auxiliar_camino() {
    	if(casilla_auxiliar_camino!=null)
    		if(casilla_auxiliar_camino.getUnidad()!=null)//Si la unidad ha terminado de correr
    			if(casilla_actual != null &&casilla_actual.getUnidad()==null){//Si no ha seleccionado otra unidad
    				casilla_actual=casilla_auxiliar_camino;
    				casilla_auxiliar_camino=null;
    			}	
    }
    
    /**Actualiza las coordenadas de los objetos en movimiento y los dibuja*/
	public void mover() {	
			mover_unidades();
	    	actualizar_controlador_movimientos();
			dibujar_unidades_moviendose();		
			dibujar_unidades_estaticas();
	}
	/**Llama al método mover del controlador de movimientos*/
	private void mover_unidades(){		
		controlador_movimientos.mover();
	}
	
	/**Llama al método actualizar_acciones del controlador de movimientos. Posteriormente comprueba si la acción andar ha terminado y la casilla actual 
	 * y la casilla destino intercambian los valores.
	 * WARNING : Si se mantiene pulsado una tecla, se mandarán las acciones más rápido de lo que el ServerSocket tarda en procesarlo y saltarán errores de
	 * persistencia. No se puede detener el hiloc fx brevemente para que pueda actualizarse porque el hilo pintor intentaría pintar sobre casillas con valores
	 * null, por lo que la forma más eficaz de detener milésimamente el programa es con un bucle hueco.*/
	private void actualizar_controlador_movimientos() {		
		controlador_movimientos.actualizar_acciones();				
			
			//Comprueba si la acción de andar ha terminado y actualiza las casillas
			if(controlador_movimientos.comprobar_accion_andar() && casilla_actual == null && casillaDestino !=null) {
				casilla_actual = casillaDestino;
				casillaDestino = null;				
			}
	}
	/**Por cada movimiento que tenga el controlador de movimientos se llama a su método dibuja_unidad*/
	private void dibujar_unidades_moviendose() {
		//Unidades corriendo
		for(AccionMover movimiento : controlador_movimientos.getMovimientos()) 
			movimiento.dibujar_unidad(lienzo, sprite);;
		
		//Unidad andando
		if(controlador_movimientos.getAccionAndar()!=null)
			controlador_movimientos.getAccionAndar().dibujar_unidad(lienzo, sprite);	

	}
	/**Se dibujan las unidades que tengan las casillas del tablero.*/
	private void dibujar_unidades_estaticas() {
		//Unidades estáticas
    	for(Casilla casilla : tablero.getCasillas().values()) {    		
    		if(casilla.getUnidad()!=null) {
    		coordenadaImagen = casilla.getUnidad().getAnimacion().getDireccionActual().get(sprite);
        	lienzo.dibujar_unidad(casilla.getUnidad().getAnimacion().getSpriteSheet(),coordenadaImagen, casilla.getBoton().getLayoutX(), casilla.getBoton().getLayoutY());        	            	   
    		}
    	}
	}


	/**Dibuja los edificios todos los edificios que tenga el controlador de edificios y, si el boolean construyendo es true (el jugador tiene un edificio
	 * seleccionado) dibuja el edificio que va a construirse en las coordenadas X e Y obtenidas por el evento onMouseEntered detonado por la última casilla
	 * por el que ha pasado el ratón*/
    private void dibujar_edificios() {
    	for(Edificio edificio : controlador_edificios.getEdificios())
    		lienzo.dibujar_imagen(edificio.getImagen(), edificio.getX(), edificio.getY());
    	
    	if(construyendo) 
    		lienzo.dibujar_imagen(edificio_construyendose.getImagen(), x, y);
    	
    }
	/**Cada 10 fps, se actualizan los sprites de las UNIDADES en movimiento y estáticas*/
	public void actualizar_fps() {
		fps++;
    	if(fps == 10) {
    		sprite++; 
    		fps = 0;
    	}    	
    	if(sprite>2)
    		sprite=0;
	}
	
	/**Dibuja la imagen del camino en cada casilla del tablero que formen parte, comprobando para ello su valor 
	 * booleano camino.*/
	private void dibujar_camino() {
		for(Casilla casilla : tablero.getCasillas().values())
    		if(casilla.getCamino())
    			lienzo.dibujar_imagen(new Image(DatosGlobales.rutas.getProperty("imgIconos")+"casilla_camino.png"), casilla.getX(),casilla.getY());
	}

	/**Borra el camino que estuviese construyendose para la accion correr. Para ello, setea a false el boolean camino de cada casilla del array
	 * camino_casillas y resetea camino_casillas y camino_direcciones. Por último, devuelve camino_construyendose a fals. */
    private void borrar_camino() {    	
    	for(Casilla casilla : camino_casillas)
			casilla.setCamino(false);

    	camino_casillas = new ArrayList<Casilla>();
    	camino_direcciones = new ArrayList<String>();
    	camino_construyendose = false;
    }
    
    /**Método que se llama cuando se construye un camino. Pasa a false el boolean camino_consturyendose y crea la acción correr en el 
     * controlador de movimientos. Le da a la casilla auxiliar camino el valor de la última casilla del camino y por último enví la
     * acción correspondiente al comunicador.*/
    private void correr(){
    	camino_construyendose = false;
		for(Casilla casilla : camino_casillas)
			casilla_actual.getUnidad().reducir_mov();
		actualizar_marco();
		controlador_movimientos.crear_accion_correr(casilla_actual,4,camino_direcciones,camino_casillas);	
		casilla_auxiliar_camino = camino_casillas.get(camino_casillas.size()-1);

		Accion accion = new AccionCorrer(casilla_actual,camino_direcciones,camino_casillas);
		comunicador.enviar_accion(accion);
    }    
    
    /**Método sobrecargado. Comprueba si el jugador tiene los recursos suficientes para construir un edificio. En caso de que no 
     * disponga de una cantidad igual o superior al de los recursos del edificio, se muestra un mensaje y se devuelve false.*/
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
    /**Método sobrecargado. Comprueba si el jugador tiene los recursos suficientes para producir una unidad. Para ello, obtiene los recursos
     * que cuesta la unidad y comprueba con un switch si el jugador supera su cantidad y en caso negativo muestra un mensaje
     * y devuelve false.*/
    private boolean comprobar_recursos(Unidad unidad) {
    	ArrayList<Recurso> coste_unidad = unidad.getCoste();    	
    	for(Recurso recurso : coste_unidad) {
    		
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
    /**Método sobrecargado. Resta el coste de un edificio a los recursos del jugador*/
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
    /**Método sobrecargado. Resta el coste de una unidad a los recursos del jugador*/
    public void restar_recursos(Unidad unidad) {
    	ArrayList<Recurso> coste_unidad = unidad.getCoste();    	
    	for(Recurso recurso : coste_unidad) {
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
    
    /**Método llamado al inicializar el controlador de la partida que construye el edificio principal del jugador.*/
    private void construir_edificio_inicial(Edificio edificioPrincipal, double[] coordenadas, String bando) {
    	Edificio edificio = new EdificioPrincipal(edificioPrincipal);
    	ConstruirCimientos accion_construir = new ConstruirCimientos(edificio);
    	accion_construir.establecer_cimientos(coordenadas[0], coordenadas[1]);
    	for(Casilla casilla : tablero.getCasillas().values()) 
    	{    	
    		if(accion_construir.contacto(casilla.getX(), casilla.getY(), DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE)) 
    				accion_construir.agregar_cimiento(casilla);
    	}
		controlador_edificios.add(edificio);
		edificio.setBando(bando);
    }
    
    
    /**Construye un edificio en una casilla seleccionada. Primero obtiene una copia del edificio a construir a partir del recibido por parámetro
     * y comprueba si es de combate o de producción. Crea la acción ConstruirCimientos, encargada de validar si el edificio es edificable en esa
     * posición o no. Para ello establece las coordenadas de la casilla seleccionada en la acción (establecer_cimientos) y obtiene las casillas del 
     * tablero que están en contacto con el edificio. De este array de casillas comprueba cada una esté libre para ser edificada (que no tenga otra
     * unidad, edificio u obstáculo) y en caso de que una sola de ellas no esté disponible, elimina el edificio y devuelve false. Si todas están
     * libres se añade el edificio al controlador de edificios y se comprueba que sea el turno del jugador, para actualizar los recursos y enviar
     * la acción al comunicador o no.*/
    boolean construir(Edificio edificio, Casilla casilla_seleccionada) {
    	

    	//Separamos el edificio que hemos arrastrado del botonEdificio del que construiremos
    	Edificio edifi = null;
    	if(edificio instanceof EdificioCombate) 
    		edifi = new EdificioCombate(edificio);
    	else if(edificio instanceof EdificioProduccion) 
    		edifi = new EdificioProduccion(edificio);

    	ConstruirCimientos accion_construir = new ConstruirCimientos(edifi);
    	accion_construir.establecer_cimientos(casilla_seleccionada.getX(), casilla_seleccionada.getY());

    	
    	for(Casilla casilla : tablero.getCasillas().values()) 
    	{    	
    		if(accion_construir.contacto(casilla.getX(), casilla.getY(), DatosGlobales.CASILLA_SIZE, DatosGlobales.CASILLA_SIZE)) {
    			if(accion_construir.comprobar_casilla_edificable(casilla)) {
    				accion_construir.agregar_cimiento(casilla);
    			}
    		  	else { 
    	    		accion_construir.eliminar_edificio();
    	    		return false;
    	    	}
    		} 
    	}
    	if(comunicador.miTurno()) {
    		Accion accionConstruir = new AccionConstruir(casilla_seleccionada,DatosGlobales.ACCION_CONSTRUIR, edifi);
			comunicador.enviar_accion(accionConstruir);
	    	restar_recursos(edificio_construyendose);
			actualizar_recursos();
			edifi.setBando(jugador.getBando());
			borrar_accion(); 
    	}
			controlador_edificios.add(edifi);
			Musico.reproducir_accion(DatosGlobales.ACCION_CONSTRUIR);
			return true;	
    }
    private void setVisibilidadBotones(boolean valor, ArrayList<Button> botones) {
    	for(Button boton : botones)
    		boton.setVisible(valor);
    }

    private void setVisibilidadEstadisticas(boolean valor) {
    	for(Text texto : estadisticas)
    		texto.setVisible(valor);
    }

    /**Muestra los valores del marco correspondiente a los de una unidad de combate. Primero reinicia los 
     * valores de los botones de las habilidades para no arrastrar las de la unidad anterior. Luego muestra los botones
     * de accion y de habilidades, ocultándo el resto. Si se está lanzando una habilidad muestra el botón de cancelar o no.
     * Por último, cada botón habilidad mostrará el nombre de una habilidad de la unidad, comprobará si tiene maná suficiente
     * y de ser así será clickeable o no e implementará el evento onAction, que permitirá iniciar la habilidad correspondiente
     * siempre y cuando no haya otra construida.*/
    private void mostrar_marco_combate() {
    	for(Button boton : botones_habilidades) {
    		boton.setText("");
    		boton.setDisable(true);
    	}
    	setVisibilidadBotones(true,botones_acciones);
    	setVisibilidadBotones(true,botones_habilidades);
    	if(habilidad_seleccionada!=null)
    	btnCancelar.setVisible(true);
    	else btnCancelar.setVisible(false);
    	setVisibilidadBotones(false,botones_produccion);
    	setVisibilidadBotones(false, botones_alojamiento);    	
    	setVisibilidadEstadisticas(true);

    	int i = 0;
    	for(Habilidad habilidad : casilla_actual.getUnidad().getHabilidades()) {
    		Button boton = botones_habilidades.get(i); 
    		boton.setText(habilidad.getNombre());    
    		if(casilla_actual.getUnidad().getMana()>=habilidad.getMana()) {
    		boton.setDisable(false);
    		boton.setOnAction(new EventHandler() {
   				@Override
				public void handle(Event event) {   	
   					if(!habilidad_construida) 		
   					iniciar_habilidad(habilidad);
   				}
    		});
    		}
    		i++;    		
    	}
    	
    }
    /**Metodo detonado cuando se pulsa un botón del array de botones habilidad. Se le da el valor de la habilidad a habilidad_seleccionada. Además,
     * comprueba si es una habilidadArea, la cual no requiere verificar sus valores y se ejecuta directamente.
     * PD: no se está creando una nueva habilidad, sino arrastrando la que contiene el botón seleccionado y cambiándole los valores.*/
    private void iniciar_habilidad(Habilidad habilidad) {
		//No estamos creando una nueva habilidad, si no arrastrando la que contiene el botón y cambiándole los valores
		habilidad_seleccionada = habilidad;

    	if(habilidad instanceof HabilidadArea) {
			HabilidadArea habilidadArea = (HabilidadArea) habilidad_seleccionada;
			habilidadArea.establecer_valores(casilla_actual, tablero.getCasillas().values());
			AccionHabilidadArea accion = new AccionHabilidadArea(casilla_actual,DatosGlobales.ACCION_HABILIDAD, habilidadArea);
			comunicador.enviar_accion(accion);
			
			ejecutar_habilidad(habilidadArea);
    		actualizar_marco();
    	}
    	else {
	    	actualizar_raton();
	    	btnCancelar.setVisible(true);
    	}
    }
    /**Método que muestra el marco correspondiente al de una unidad de producción. Oculta los botones de habilidades
     * y de acciones salvo el de alojar, y muestra los de producción y estadísticas. Por último, por cada Edificio que 
     * la unidad pueda construir se le implementa un método onAction a un botón del array de botones produccion. Este evento
     * comprueba si el jugador tiene recursos suficientes para construir el edificio y de ser así inicia su construcción*/
    private void mostrar_marco_produccion() {
    	setVisibilidadBotones(false, botones_acciones);
    	btnAlojar.setVisible(true);
    	setVisibilidadBotones(true, botones_produccion);    	
    	setVisibilidadEstadisticas(true);  
    	setVisibilidadBotones(false,botones_habilidades);
    	
    	UnidadProduccion unidad = (UnidadProduccion) casilla_actual.getUnidad();
    	
    	int i = 0;
    	for(Edificio edificio : unidad.getEdificios()) {
       			Button boton = (Button) botones_produccion.get(i);
	    		BotonEdificio boton_edificio = new BotonEdificio(edificio,boton);
	    		boton.setOnAction(new EventHandler() {
	   				@Override
					public void handle(Event event) {   					
	   					if(comprobar_recursos(edificio))
							iniciar_construir(edificio);
	   				}
	   			});
	    	i++;    
    	}

    }    

    /**Actualiza las estadísticas con los valores de la unidad
     * @param unidad la unidad cuyos valores deben mostrarse en las estadísticas*/
    private void cambiar_estadisticas(Unidad unidad) {
    	vAt.setText(unidad.getFullAt()+"");
    	vDefensa.setText(unidad.getFullDef()+"");
    	vPg.setText(unidad.getPg()+"");
    	vMovimiento.setText(unidad.getMov()+"");
    	vMana.setText(unidad.getMana()+"");
    	imgCaraUnidad.setImage(unidad.getCara());
    }
    /**Actualiza las estadísticas con los valores del edificio
     * @param edificio el edificio cuyos valores deben mostrarse en las estadísticas*/
    private void cambiar_estadisticas(Edificio edificio) {
    	vAt.setText(edificio.getAt()+"");
    	vDefensa.setText(edificio.getDef()+"");
    	vPg.setText(edificio.getPg()+"");
    	vMovimiento.setText("0");
    	vMana.setText("");
    	imgCaraUnidad.setImage(edificio.getImagen());
    }
    /**El marco correspondiente al de un edificio. Oculta todos los botones salvo los de produccion y los de alojamiento.
     * Por cada unidad alojada que tenga el edificio, crea un BotonBestiario con la unidad y con el botón de alojamiento
     * y le sobreescribe un método onAction consistente en obtener el valor de dicha unidad, actualizar las estadísticas, 
     * iniciar la acción de desalojar y mostrar el botón de cancelar. Es decir: por cada unidad que tenga el edificio alojado,
     * se crea un boton bestiario con un botón alojamiento y con dicha unidad y, al ser cliqueado, se inicia la acción de desalojo.
     * Realiza una acción similar con los botones de producción, solo que estos llaman a crear unidad comprobando antes si el jugador
     * tiene los recursos necesarios para ello.*/
    private void mostrar_marco_edificio() {
    	setVisibilidadBotones(false, botones_produccion);
    	setVisibilidadBotones(false, botones_acciones);
    	setVisibilidadBotones(false, botones_habilidades);
    	setVisibilidadBotones(true, botones_alojamiento);
    	cambiar_estadisticas(casilla_actual.getEdificio());

    	iterador = 0;
		for(Unidad unidad_alojada : casilla_actual.getEdificio().getUnidadesAlojadas()) {
			BotonBestiario boton = new BotonBestiario(unidad_alojada,botones_alojamiento.get(iterador));	
			boton.setPosicion(iterador);
			boton.getBoton().setVisible(true);
			boton.getBoton().setOnAction(new EventHandler() {
   				@Override
				public void handle(Event event) {   					
   					unidad_desalojada = boton.getUnidad();
   					cambiar_estadisticas(boton.getUnidad());
   					desalojandose = true;
   					btnCancelar.setVisible(true);
   					numUnidad = boton.getPosicion();
   				}
   			});
			iterador++;
		}
		for(int e = iterador; e< botones_alojamiento.size(); e++) {
			botones_alojamiento.get(e).setVisible(false);
		}
		iterador =0;		    	
		if(casilla_actual.getEdificio() instanceof EdificioCombate)
    	for(Unidad unidad : ((EdificioCombate)casilla_actual.getEdificio()).getProduccion_unidades()) {
			BotonBestiario boton = new BotonBestiario(unidad,botones_produccion.get(iterador));	
			boton.getBoton().setVisible(true);
			boton.getBoton().setOnAction(new EventHandler() {
	   				@Override
					public void handle(Event event) {   					
	   					if(comprobar_recursos(unidad)) {
	   						unidad_produciendose = unidad;
	   						actualizar_raton();
	   					}
	   					
	   				}
	   			});
			iterador++;
	    	}
    }

    /**Produce la acción de ataque. Obtiene la unidad atacante y llama a su método atacar con la casilla atacada como parámetro. 
     * Posteriormente crea la animación de ataque sobre la casilla atacada y comprueba si es su turno y debe enviar la acción 
     * de ataque al comunicador o no. Finalmente borra la acción*/
    void atacar(Casilla casillaAtacante, Casilla casillaAtacada) {
    	
    	Musico.reproducir_accion(DatosGlobales.ACCION_ATACAR);
    	uc = (UnidadCombate)casillaAtacante.getUnidad();
    	if(casillaAtacada.getUnidad()!=null) {
    		tHistoria.appendText(uc.atacar(casillaAtacada.getUnidad()));
    		if(casillaAtacada.getUnidad().getPg()<=0)
    			casillaAtacada.setUnidad(null);
    	}
    	else if(casillaAtacada.getEdificio()!=null) {
    		tHistoria.appendText(uc.atacar(casillaAtacada.getEdificio()));
    		if(casillaAtacada.getEdificio().getPg()<=0) {
    			Musico.reproducir_accion("edificio_demolido");
    			if(casillaAtacada.getEdificio() instanceof EdificioPrincipal)
    				if(turno)
    					victoria();
    				else
    					derrota();
    			controlador_edificios.eliminar_edificio(casillaAtacada.getEdificio());
    		}
    	}
    	anmAt.setAtaque(casillaAtacada);  
    	if(turno)
    		comunicador.enviar_accion(new AccionAtacar(casillaAtacante, casillaAtacada));
    	borrar_accion();
    	casillaAtacante.getUnidad().accion_realizada();
    }   
    
    private void victoria() {
    	System.out.println("Victoriaaa, llupi");
    	comunicador.fin();
    	cerrar();
    }
    private void derrota() {
    	System.out.println("Derrota");
    	comunicador.fin();
    	cerrar();
    }
    
    @FXML
    void iniciar_ataque(ActionEvent event) {
    	if(!casilla_actual.getUnidad().getAccion()) {
	    	atacando = true;
	    	actualizar_raton();
	    	btnCancelar.setVisible(true);
    	}
    	else {
    		tHistoria.appendText(casilla_actual.getUnidad().getNombre()+" ya no puede atacar más");
    	}
    }
    private void iniciar_construir(Edificio edificio) {

	    	construyendo = true;
	    	actualizar_raton();
	    	btnCancelar.setVisible(true);
	    	edificio_construyendose = edificio;
    }
    @FXML
    void cancelar_accion(ActionEvent event) {
    	borrar_accion();
    }
    /**Limpia cualquier rastro de haber ejecutado alguna acción. Es decir, este método limpia todas las variables que los hilos
     * usan para para comprobar si se debe ejecutar alguna acción. Aconglomerarlas todas en un único método evita incompatibilidades
     * en los valores booleanos (una unidad no puede alojarse en un edificio a la vez que otra pretende salir de este, ya que el array
     * de unidades no sería persistente, por ejemplo).*/
    private void borrar_accion() {
    	atacando=false;
    	construyendo=false;
    	habilidad_seleccionada = null;
    	habilidad_construida = false;
    	habilidad_seleccionada=null;
    	alojandose = false;
    	unidad_desalojada = null;
		desalojandose = false;
    	edificio_construyendose = null;
    	unidad_produciendose =null;
    	actualizar_raton();
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
    	else if(construyendo || habilidad_seleccionada!=null||unidad_produciendose!=null)
        	scroll.setCursor(Cursor.HAND);
    	else 
        	scroll.setCursor(Cursor.DEFAULT);
    }
    
    @FXML
    void alojar_unidad() {
    	alojandose = true;
    	btnCancelar.setVisible(true);
    }
    public void cerrar() {
    	Stage stage = (Stage) this.btnHabilidad1.getScene().getWindow();
        stage.close();
    }
    

}



