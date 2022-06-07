package frames.menuPrincipal;

import java.io.IOException;
import java.util.ArrayList;

import bean.botones.BotonBestiario;
import bean.datos_globales.DatosGlobales;
import bean.unidades.unidades.Unidad;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConstructorMenuPrincipal {

	private Stage ventana;
	private FXMLLoader loader;
	private AnchorPane root;
	private ControladorMenuPrincipal cmp;
	private Scene scene;
	
	public ControladorMenuPrincipal getControlador() {
		return cmp;
	}
	public ConstructorMenuPrincipal(Stage ventana) {
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
		double x = 0;
		double y = 0;
    	for(Unidad unidad : DatosGlobales.unidades.values()) {
    		
    		Button boton = new Button();
    		boton.setPrefSize(50, 50);
    		BotonBestiario boton_bestiario = new BotonBestiario(unidad,boton);    		
    		cmp.groupHumanos.getChildren().add(boton);    	
    		boton.setLayoutX(x); boton.setLayoutY(y);
    		x+=60;    		
    		if(x==300) {
    			x=0;
    			y+=60;
    		}
    		cmp.botonesBestiario.add(boton_bestiario);    		 
    	}
	}
}

