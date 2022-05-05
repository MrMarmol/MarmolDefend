package app;
	
import bean.datos_globales.DatosGlobales;
import frames.menuPrincipal.ConstructorFMenuPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
		
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage ventana) {
		//Se cargan los datos de uso colectivo
		DatosGlobales.inicializar(); 
		//Se carga y se muestra el menú principal
		ConstructorFMenuPrincipal menu_principal = new ConstructorFMenuPrincipal(ventana);
		menu_principal.mostrar();
	}
}
