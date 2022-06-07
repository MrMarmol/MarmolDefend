package frames.menuPrincipal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import audio.Musico;
import bean.botones.BotonBestiario;
import bean.datos_globales.DatosGlobales;
import bean.unidades.unidades.Unidad;
import frames.partida.Comunicador;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import procesos.habilidades.Habilidad;



public class ControladorMenuPrincipal {
	
	//Menu principal
    @FXML
    private ImageView imgFondo;
    
    @FXML
    private Button btnPartida;

    @FXML
    private Button btnBestiario;

    @FXML
    private Button btnArchivo;

    @FXML
    private Button btnRanking;

    @FXML
    private Button btnConfiguracion;

    @FXML
    private Button btnSalir;
    
    //Iniciar Partida
    
    @FXML
    private AnchorPane subMenuPartida;    
    @FXML
    private Label lElegirRol;
    @FXML
    private TextField tfIpHost;
    
    //Bestiario
    ArrayList<BotonBestiario> botonesBestiario;
    @FXML
    private AnchorPane subMenuBestiario;
    @FXML
    private ImageView img_marco_submenu;
    @FXML
    Group groupHumanos;
    @FXML
    private Label txtDescripcion;       
    @FXML
    private ImageView imgCara;
    @FXML
    private Label vAt;
    @FXML
    private Label vDef;
    @FXML
    private Label vHabilidad;
    @FXML
    private Label vMov;       
    @FXML
    private Button btnCerrarBestiario;
    @FXML
    private Label vPg;
    @FXML
    private Label vMana;
    @FXML
    private Label tNombreHabilidad;
    @FXML
    private Label tNombreUnidad;
    @FXML
    private Group gHabilidades;

    //Configuracion
    @FXML
    private AnchorPane subMenuConfiguracion;
    @FXML
    private ImageView img_marco_submenuConf;
    
	private String[] idiomas;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnConfirmar;
    @FXML 
    private Label txtIdioma; 
    @FXML
    private Button btnBorrarDatos;
    @FXML
    private Button btnCrearMapa;
    @FXML
    private Button btnCrearUnidad;
    @FXML
    private Button btnCrearEdificio;
    @FXML
    private Button btnCreditos;
    @FXML
    private Label lIdioma;
    
    private Comunicador comunicadorPartida;
        
    public void inicializar() {
    	
    	//Musico.reproducir_musica("musMenuPrincipal");
    	btnPartida.setText(DatosGlobales.idioma.getProperty("mein_iniciarPartida"));
    	btnBestiario.setText(DatosGlobales.idioma.getProperty("mein_bestiario"));
    	btnArchivo.setText(DatosGlobales.idioma.getProperty("mein_archivos"));
    	btnRanking.setText(DatosGlobales.idioma.getProperty("mein_ranking"));
    	btnConfiguracion.setText(DatosGlobales.idioma.getProperty("mein_configuracion"));
    	btnSalir.setText(DatosGlobales.idioma.getProperty("mein_salir"));
    	btnCerrarBestiario.setText(DatosGlobales.idioma.getProperty("mein_bes_cerrar"));
    	lIdioma.setText(DatosGlobales.idioma.getProperty("mein_conf_idioma"));
    	btnCrearUnidad.setText(DatosGlobales.idioma.getProperty("mein_conf_crearUnidad"));
    	btnCrearEdificio.setText(DatosGlobales.idioma.getProperty("mein_conf_crearEdificio"));
    	btnCrearMapa.setText(DatosGlobales.idioma.getProperty("mein_conf_crearMapa"));
    	btnBorrarDatos.setText(DatosGlobales.idioma.getProperty("mein_conf_borrarDatos"));
    	btnCreditos.setText(DatosGlobales.idioma.getProperty("mein_conf_creditos"));
    	btnConfirmar.setText(DatosGlobales.idioma.getProperty("mein_conf_confirmar"));
    	idiomas = new String[] {"Español","English"};
    	txtIdioma.setText(idiomas[0]);
    	implementar_onclick();
    }    

    private void implementar_onclick() {
    	
    	for(BotonBestiario boton : botonesBestiario) {
    		boton.getBoton().setOnAction(new EventHandler() {
    			@Override
    			public void handle(Event event) {
    				mostrarUnidad(boton.getUnidad());
				}
			});
		}
    }
    @FXML
    void mostrarBestiario(ActionEvent event) {
    	if(subMenuConfiguracion.isVisible())
    		subMenuConfiguracion.setVisible(false);

    	subMenuBestiario.setVisible(true);    
    	mostrarUnidad(DatosGlobales.unidades.get("Aldeano"));
    	
    }
    @FXML
    void mostrar_configuracion(ActionEvent event){
    	if(subMenuBestiario.isVisible())
    		subMenuBestiario.setVisible(false);
		subMenuConfiguracion.setVisible(true);

    		
    }
    private void mostrarUnidad(Unidad unidad) {
    	tNombreHabilidad.setText("");
    	vHabilidad.setText("");
    	txtDescripcion.setText(unidad.getDescripcion());
    	imgCara.setImage(unidad.getCara());
    	vAt.setText(unidad.getFullAt()+"");
    	vDef.setText(unidad.getFullDef()+"");
    	vMov.setText(unidad.getMov()+"");
    	vPg.setText(unidad.getPgTotales()+"");
    	vMana.setText(unidad.getMana()+"");
    	tNombreUnidad.setText(unidad.getNombre()+"");    	
    	
    	for(Node nodo : gHabilidades.getChildren())
    		nodo.setVisible(false);
    	double x = 0;
    	for(Habilidad habilidad : unidad.getHabilidades()) {
    		Button boton = new Button();
    		boton.setText(habilidad.getNombre());
    		boton.setPrefWidth(100);
    		boton.setOnAction(new EventHandler() {
   				@Override
				public void handle(Event event) {
   					vHabilidad.setText(habilidad.getDescripcion());
   					tNombreHabilidad.setText(habilidad.getNombre());
				}
    		});
    		gHabilidades.getChildren().add(boton);
    		boton.setLayoutX(x);
    		x += DatosGlobales.ANCHO_BOTONES_HABILIDAD;    		
    	}
    }
    

    @FXML
    void iniciarPartida(ActionEvent event) {    
    	subMenuBestiario.setVisible(false);
    	subMenuConfiguracion.setVisible(false);    	
    	subMenuPartida.setVisible(true);    	
    }
    @FXML
    void crear_partida(ActionEvent event) {
    	
    	comunicadorPartida = new Comunicador(DatosGlobales.PUERTO);
    	lElegirRol.setText("Esperando jugadores...");

    	try {
			comunicadorPartida.buscar_partida();
		} catch (IOException puerto_ocupado) {
			lElegirRol.setText("El puerto ya está ocupado");
		}
    }
    @FXML
    void unir_partida(ActionEvent event) {
    	comunicadorPartida = new Comunicador(tfIpHost.getText(), DatosGlobales.PUERTO);
    	try {
			comunicadorPartida.buscar_partida();
		} catch (IOException host_inexistente) {
			lElegirRol.setText("No hay partidas en esa ip");
		}
    }
    @FXML
    void cancelar_partida(ActionEvent event) {
    	comunicadorPartida = null;
    	subMenuPartida.setVisible(false);    	
    }
    @FXML
    void cerrar_ventana(ActionEvent event) {
    	subMenuBestiario.setVisible(false);
    }
    

    @FXML
    void confirmarCambios(ActionEvent event)  {
		try {
	    	FileInputStream in;
			in = new FileInputStream(DatosGlobales.rutas.getProperty("configuracion"));
	    	Properties configuracion = new Properties();
	    	configuracion.load(in);
	    	in.close();

	    	FileOutputStream out = new FileOutputStream(DatosGlobales.rutas.getProperty("configuracion"));
	    	
	    	if(txtIdioma.getText().equals("Español"))
	    		configuracion.setProperty("idioma", "esp");
	    	else
	    		configuracion.setProperty("idioma", "eng");
	    	configuracion.store(out, null);
	    	out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	subMenuConfiguracion.setVisible(false);
    }

    @FXML
    void idmSiguiente(ActionEvent event) {
    	if(txtIdioma.getText().equals(idiomas[0]))
    		txtIdioma.setText(idiomas[1]);
    	else
    		txtIdioma.setText(idiomas[0]);
    }

    @FXML
    void imdAnterior(ActionEvent event) {
    	if(txtIdioma.getText().equals(idiomas[0]))
    		txtIdioma.setText(idiomas[1]);
    	else
    		txtIdioma.setText(idiomas[0]);
    }
    @FXML
    void salir(ActionEvent event) {
    	cerrar();
    }
    public void cerrar() {
    	Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    

}

	