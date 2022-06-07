package app;
	
import bean.datos_globales.DatosGlobales;
import frames.menuPrincipal.ConstructorMenuPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;



public class Main extends Application {
		
	private Stage ventana;
	ConstructorMenuPrincipal menu_principal;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage ventana) {
		DatosGlobales.inicializar();
		this.ventana = ventana;
		menu_principal = new ConstructorMenuPrincipal(ventana);
		menu_principal.mostrar();
		}
	
}
