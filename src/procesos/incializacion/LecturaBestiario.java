package procesos.incializacion;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import bean.datos_globales.DatosGlobales;
import bean.unidades.Unidad;


public class LecturaBestiario {
	
	private XMLReader procesadorXML;
	private GestionContenido gestor;
	private InputSource fileXML;

	public LecturaBestiario()
	{}
	
	public ArrayList<Unidad> lectura() {
		try {
			procesadorXML = XMLReaderFactory.createXMLReader(); /*Lee el xml*/
			gestor = new GestionContenido();
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
}


