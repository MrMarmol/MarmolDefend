package frames.menuPrincipal;

import java.io.IOException;
import java.util.ArrayList;

import bean.botones.BotonBestiario;
import bean.datos_globales.DatosGlobales;
import bean.unidades.Unidad;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConstructorFMenuPrincipal {

	private Stage ventana;
	private FXMLLoader loader;
	private AnchorPane root;
	private ControladorMenuPrincipal cmp;
	private Scene scene;
	
	public ConstructorFMenuPrincipal(Stage ventana) {
		this.ventana = ventana;
		inicializar_datos();
	}
	
	public void mostrar() {
		try {			
	  		ventana.getIcons().add(new Image(DatosGlobales.rutas.getProperty("imgIconos")+"icono.png"));
			ventana.setTitle(DatosGlobales.TITULO);
			ventana.setScene(scene);			
			ventana.show(); 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void inicializar_datos() 
	{
		try {
			loader = new FXMLLoader(getClass().getResource("vista_menu_principal.fxml")); //Ubicación de punto de vista 
			root = loader.load();
			scene = new Scene(root,DatosGlobales.MENU_PRINCIPAL_WIDTH, DatosGlobales.MENU_PRINCIPAL_HEIGHT);			
			cmp = loader.<ControladorMenuPrincipal>getController();
			construirFrame();
			cmp.inicializar();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void construirFrame() {
		cargar_bestiario();
	}
	private void cargar_bestiario() {
    	cmp.botonesBestiario= new ArrayList<BotonBestiario>();
		int numUnidad = 0;	    	
    	for(Node node : cmp.groupHumanos.getChildren()) {
    		 
    		BotonBestiario boton = new BotonBestiario(DatosGlobales.unidades.get(numUnidad), (Button) node);
    		numUnidad++;
    		if(numUnidad>3)numUnidad--;
    		cmp.botonesBestiario.add(boton);    		 
    	}
	}
}

