package procesos.lectorXml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.unidades.unidades.Unidad;


public class LecturaBestiario {
	
	private XMLReader procesadorXML;
	private GestionContenidoUnidades gestor;
	private GestionContenidoHabilidades gestorH;
	private GestionContenidoEdificios gestorE;
	private InputSource fileXML;

	public LecturaBestiario()
	{}
	
	public HashMap<String,Unidad> lectura_unidades() {
		try {
			procesadorXML = XMLReaderFactory.createXMLReader(); /*Lee el xml*/
			gestor = new GestionContenidoUnidades();
			procesadorXML.setContentHandler(gestor); /*Se le asocia nuestro gestor*/
			
			switch(DatosGlobales.idioma.getIdioma()) 
			{
				case "Español":
					fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlBestiarioEsp")); 
					break;
				case "Inglés":
					fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlBestiarioEng")); 
					break;
				default : 
					fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlBestiarioEsp")); 
			}				
			procesadorXML.parse(fileXML); /*Iniciamos el recorrido del xml*/
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (IOException parse) {
			parse.printStackTrace();
		}
		return gestor.getUnidades();
	}	
	public  HashMap<String,String> lectura_descripcion_habilidades() {
		try {
			procesadorXML = XMLReaderFactory.createXMLReader(); /*Lee el xml*/
			gestorH = new GestionContenidoHabilidades();
			procesadorXML.setContentHandler(gestorH); /*Se le asocia nuestro gestor*/
			
			switch(DatosGlobales.idioma.getIdioma()) 
			{
				case "Español":
					fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlHabilidadesEsp")); 
					break;
				case "Inglés":
					fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlBestiarioEng")); 
					break;
				default : 
					fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlHabilidadesEsp")); 
			}				
			procesadorXML.parse(fileXML); /*Iniciamos el recorrido del xml*/
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (IOException parse) {
			parse.printStackTrace();
		}
		return gestorH.getDescripciones();
	}	
	
	public ArrayList<Edificio> lectura_edificios(){
		try {
		procesadorXML = XMLReaderFactory.createXMLReader(); /*Lee el xml*/
		gestorE = new GestionContenidoEdificios();
		procesadorXML.setContentHandler(gestorE); /*Se le asocia nuestro gestor*/		
		fileXML = new InputSource(DatosGlobales.rutas.getProperty("xmlEdificios")); 
		procesadorXML.parse(fileXML); /*Iniciamos el recorrido del xml*/
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (IOException parse) {
			parse.printStackTrace();
		}
	return gestorE.getEdificios();
	}
}


