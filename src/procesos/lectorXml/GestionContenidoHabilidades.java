package procesos.lectorXml;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class GestionContenidoHabilidades extends DefaultHandler {

	private HashMap<String,String> descripciones;
	private String nombre;
	private String descripcion;
	private String campo;
	
	public GestionContenidoHabilidades() {
		super();
		descripciones = new HashMap<String,String>();		
	}
	public HashMap<String,String> getDescripciones() {
		return descripciones;
	}
	
	public void startDocument() {
		System.out.println("Lectura de habilidades");
	}
	public void endDocument() {
		System.out.println("Final del bestiario");
	}
	public void startElement(String uri,String nombre, String nombreC, Attributes atts) {
		campo = nombre;
					
	}
    public void endElement(String uri, String localName, String qName){

    	if(descripcion!=null&&nombre!=null) {
    		descripciones.put(nombre, descripcion);
    		System.out.println(nombre);
    		nombre=null;
    		descripcion=null;
    	}
    }

	public void characters(char[] ch, int inicio, int longitud) {

		switch(campo) {
			case "Nombre":
				nombre =new String(ch, inicio, longitud);
				break;
			case "Descripción":
				descripcion = new String(ch, inicio, longitud);
				break;			
		}
	}
	


	
}
