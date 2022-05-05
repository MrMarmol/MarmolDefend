package bean.idioma;

import java.io.IOException;
import java.util.Properties;

public class Idioma extends Properties{
	 
    private static final long serialVersionUID = 1L;
    private String idioma;
    
    public Idioma(){

    	getProperties("../../archivos/configuracion/configuracion.properties");
    	
        switch(getProperty("idioma")){
            case "Espa�ol":
                    getProperties("../../archivos/idioma/idioma_esp.properties");
                    idioma = "Espa�ol";
                    break;
            case "Ingl�s":            	
                    getProperties("../../archivos/idioma/idioma_eng.properties");
                    idioma = "Ingl�s";
                    break;
            default:
                getProperties("../../archivos/idioma/idioma_eng.properties");
        }

    }
 
    private void getProperties(String idioma) {
        try {
            this.load( getClass().getResourceAsStream(idioma) );
        } catch (IOException ex) {

        }
   }
    public String getIdioma() {
    	return idioma;
    }
}