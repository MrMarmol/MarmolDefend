package frames.menuPrincipal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import audio.Musico;
import bean.botones.BotonBestiario;
import bean.datos_globales.DatosGlobales;
import bean.unidades.Unidad;
import frames.partida.ConstructorFPartida;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;



public class ControladorMenuPrincipal {
	
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

    @FXML
    private ImageView imgFondo;
    
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
    private AnchorPane subMenuConfiguracion;

    @FXML
    private ImageView img_marco_submenuConf;

    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnSiguiente;

    @FXML
    private Button btnConfirmar;

    ArrayList<BotonBestiario> botonesBestiario;
        
    @FXML
    void mostrarBestiario(ActionEvent event) {
    	subMenuBestiario.setVisible(true);      	   	
    }
    private void mostrarUnidad(Unidad unidad) {
    	txtDescripcion.setText(unidad.getDescripcion());
    	imgCara.setImage(unidad.getCara());
    }
    
    @FXML
    void parar(ActionEvent event) {
		Musico.reproducir_musica("musMenuPrincipal");
    }
    @FXML
    void iniciarPartida(ActionEvent event) throws FileNotFoundException, IOException {
    	ConstructorFPartida constructorFPartida = new ConstructorFPartida();
    	constructorFPartida.mostrar();
    }
    
    @FXML
    void cerrar_ventana(ActionEvent event) {
    	subMenuBestiario.setVisible(false);
    }
    

    @FXML
    void confirmarCambios(ActionEvent event) {
    	Musico.detener_musica();
    }

    @FXML
    void idmSiguiente(ActionEvent event) {

    }

    @FXML
    void imdAnterior(ActionEvent event) {

    }
    
    public void inicializar() {
    	
    	Musico.reproducir_musica("musMenuPrincipal");
    	btnPartida.setText(DatosGlobales.idioma.getProperty("mein_iniciarPartida"));
    	btnBestiario.setText(DatosGlobales.idioma.getProperty("mein_bestiario"));
    	btnArchivo.setText(DatosGlobales.idioma.getProperty("mein_archivos"));
    	btnRanking.setText(DatosGlobales.idioma.getProperty("mein_ranking"));
    	btnConfiguracion.setText(DatosGlobales.idioma.getProperty("mein_configuracion"));
    	btnSalir.setText(DatosGlobales.idioma.getProperty("mein_salir"));
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
}

	