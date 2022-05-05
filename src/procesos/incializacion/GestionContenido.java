package procesos.incializacion;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import bean.datos_globales.DatosGlobales;
import bean.unidades.Unidad;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import procesos.animacion.Animacion;



public class GestionContenido extends DefaultHandler {

	private ArrayList<Unidad> unidades;
	private Unidad unidad;
	private String campo;
	private String valor;
	
	private String nombre;
	private String descripcion;
	private int at;
	private int def;
	private int pg;
	private ArrayList<String> habilidades;
	private String bando;
	
	public GestionContenido() {
		super();
		unidades = new ArrayList<Unidad>();		
	}
	public ArrayList<Unidad> getUnidades() {
		return unidades;
	}
	
	public void startDocument() {
		System.out.println("Lectura de bestiario");
	}
	public void endDocument() {
		System.out.println("Final del bestiario");
	}
	public void startElement(String uri,String nombre, String nombreC, Attributes atts) {
		switch(nombre) {
			case "Humanidad":
				bando = "humano";
				break;
			case "Demonios":
				bando = "demonio";
				break;
			case "Habilidades":
				habilidades = new ArrayList<String>();
				break;
			default : 
				campo = nombre;			
		}				
	}
    public void endElement(String uri, String localName, String qName){

    	if(localName.equals("Unidad")) {
    		if(comprobar_atributos()) {    		
    			unidad = new Unidad(nombre, 
    					descripcion, 
    					new Image(DatosGlobales.rutas.getProperty("imgCaraUnidades")+nombre.toLowerCase().replace(" ","")+".png"), 
    					new Image(DatosGlobales.rutas.getProperty("imgPortadaUnidades")+nombre.toLowerCase().replace(" ", "")+".png"),
    					new Animacion (1, obtener_animaciones(new Image((DatosGlobales.rutas.getProperty("imgMovimientoUnidades")+nombre.toLowerCase().replace(" ","")+".png"))), new Image((DatosGlobales.rutas.getProperty("imgMovimientoUnidades")+nombre.toLowerCase().replace(" ","")+".png"))),
    					at, def, pg, habilidades, bando);
    			unidades.add(unidad);    			
    		}
    		else {
    			if(nombre != null)
    				System.out.println("Los datos de la unidad "+nombre+" están dañados y su uso no está permitido.");
    			else 
        			System.out.println("Una unidad está dañada y su uso no está permitido.");
    			//LOG
    		}
        	limpiar_atributos();
    	}
    }

	public void characters(char[] ch, int inicio, int longitud) {
		valor = new String(ch, inicio, longitud);
		valor = valor.replaceAll("[\t\n];","");
		
		switch(campo) {
			case "Nombre":
				nombre = valor;
				break;
			case "Descripción":
				descripcion = valor;
				break;
			case "Ataque":
				at = Integer.parseInt(valor);
				break;
			case "Defensa":
				def = Integer.parseInt(valor);
				break;
			case "Vida":
				pg = Integer.parseInt(valor);
				break;
			case "Habilidad":
				habilidades.add(valor);
				break;
		}
	}
	
	private boolean comprobar_atributos() {
		
		/*Hay unidades sin habilidades*/
		if(nombre !=null && descripcion!=null && at != -1 && def !=-1 && pg != 0) 
			return true;			
		return false;		
	}
	private void limpiar_atributos()
	{
		nombre = null;
		descripcion = null;
		at = -1;
		def = -1;
		pg = 0;
	}
	
	private HashMap<String,ArrayList<Rectangle>> obtener_animaciones(Image image) {
		ArrayList<Rectangle> coordenadas = new ArrayList<Rectangle>();
		HashMap<String,ArrayList<Rectangle>> movimientos = new HashMap<String,ArrayList<Rectangle>>();		
		Rectangle sprite;
		
		int x = 0;
		int y = 0;
		int w = 33;
		int h = 32;
		
		for(int e = 0; e<4; e++)
		{
			for(int i = 0; i<3;i++)//bucle derecha
			{		
			sprite = new Rectangle(x,y,w,h);
			coordenadas.add(sprite);	
			x = x+w;
			}
			x = 0;
			y = y +h;
			
			switch(e) {
				case 0: movimientos.put("ABAJO", coordenadas);	//Hashmap con la clave avajo formado por un arraylist con las coordenadas del movimiento abajo de la imagen
				break;
				case 1: movimientos.put("IZQUIERDA", coordenadas);	//Hashmap con la clave avajo formado por un arraylist con las coordenadas del movimiento abajo de la imagen
				break;
				case 2: movimientos.put("DERECHA", coordenadas);	//Hashmap con la clave avajo formado por un arraylist con las coordenadas del movimiento abajo de la imagen
				break;
				case 3: movimientos.put("ARRIBA", coordenadas);	//Hashmap con la clave avajo formado por un arraylist con las coordenadas del movimiento abajo de la imagen
				break;
			}
			coordenadas = new ArrayList<Rectangle>();
		}				
		return movimientos;
	}
}
