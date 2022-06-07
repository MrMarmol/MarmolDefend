package procesos.lectorXml;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import bean.datos_globales.DatosGlobales;
import bean.edificios.*;
import bean.recurso.Recurso;
import bean.unidades.sprites.SpritesUnidad;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;

public class GestionContenidoEdificios extends DefaultHandler {
	
	private ArrayList<Edificio> edificios;
	private boolean edificio_combate;
	private String campo;
	private String valor;
	
	private String nombre;
	private int pg;
	private int alojamientos;
	
	private String recurso_nombre;
	private int recurso_cantidad;
	private ArrayList<Recurso>coste;
	
	//combate
	private ArrayList<Unidad> unidades_produccion;
	//produccion
	private Recurso ganancia;
	private String ganancia_nombre;
	private int ganancia_cantidad;	
	
	public GestionContenidoEdificios() {
		super();
		edificios = new ArrayList<Edificio>();		
	}
	public ArrayList<Edificio> getEdificios() {
		
			 
		return edificios;
	}
	
	public void startDocument() {
		System.out.println("Lectura de edificios");
	}
	public void endDocument() {
		System.out.println("Final del bestiario");
	}
	public void startElement(String uri,String nombre, String nombreC, Attributes atts) {
		switch(nombre) {
			case "combate":
				edificio_combate = true;
				break;
			case "produccion":
				edificio_combate = false;
				break;
			case "unidades":
				unidades_produccion = new ArrayList<Unidad>();
				break;
			case "recursos":
				coste = new ArrayList<Recurso>();
				break;
			default : 
				campo = nombre;			
		}				
	}
    public void endElement(String uri, String localName, String qName){

    	if(localName.equals("recurso")) {
    		coste.add(new Recurso(recurso_nombre,recurso_cantidad));
    	}
    	if(localName.equals("ganancia")) 
    		ganancia = new Recurso(ganancia_nombre, ganancia_cantidad);
    		

    	
    	if(localName.equals("edificio")) {
    		if(comprobar_atributos()) { 
    			//El nombre sin acentos, sin espacio y en minúsculas.
    			String nombre_imagen = StringUtils.stripAccents(nombre.toLowerCase().replace(" ",""));    			   
    			Edificio edificio;
    			
    				if(edificio_combate) {
    					System.out.println(unidades_produccion.size());
    					edificio = new EdificioCombate(
    							new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+nombre_imagen+".png"),
    							nombre,
    							new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+nombre_imagen+".png"),
    							0,0,pg,alojamientos,
    							coste,
    							unidades_produccion
    							);}
    				else
    					edificio = new EdificioProduccion(
    							new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+nombre_imagen+".png"),
    							nombre,
    							new Image(DatosGlobales.rutas.getProperty("imgEdificioHumanos")+nombre_imagen+".png"),
    							0,0,pg,alojamientos,
    							coste,
    							ganancia
    							);
    				if(edificio instanceof EdificioCombate)
    					for(Unidad unidad : ((EdificioCombate)edificio).getProduccion_unidades())
    						System.out.println(unidad.toString());
    				edificios.add(edificio);
    		}
    		else {
    			if(nombre != null)
    				System.out.println("Los datos del edificio "+nombre+" están dañados y su uso no está permitido.");
    			else 
        			System.out.println("Una unidad está dañada y su uso no está permitido.");
    			//LOG
    		}
        	limpiar_atributos();
    	}
    }

	public void characters(char[] ch, int inicio, int longitud) {
		valor = new String(ch, inicio, longitud);

		switch(campo) {
			case "nombre":
				nombre = valor;
				break;
			case "pg":
				pg = Integer.parseInt(valor);
				break;
			case "alojamiento":
				alojamientos = Integer.parseInt(valor);
				break;
			case "unidad":
				unidades_produccion.add(DatosGlobales.unidades.get(valor));
				break;
			case "nombre_recurso":
				recurso_nombre = valor.toUpperCase();
				break;
			case "cantidad_recurso":
				recurso_cantidad = Integer.parseInt(valor);
				break;
			case "nombre_ganancia":
				ganancia_nombre = valor.toUpperCase();
				break;
			case "cantidad_ganancia":
				ganancia_cantidad = Integer.parseInt(valor);
				break;								
		}
	}
	
	private boolean comprobar_atributos() {
		
		if(nombre ==null && pg==0 && alojamientos == 0 && (coste==null || coste.size()==0)) 
			return false;
		if(edificio_combate && (unidades_produccion==null || unidades_produccion.size()==0))
			return false;
		if(!edificio_combate && ganancia==null)
			return false;

		
		return true;		
	}
	private void limpiar_atributos()
	{
		nombre = null;
		pg = 0;
		alojamientos = 0;
		unidades_produccion = new ArrayList<Unidad>();
		coste = new ArrayList<Recurso>();
	}
	
	
}


