package bean.rutas;

import java.io.IOException;
import java.util.Properties;

public class Rutas extends Properties{
	 
    private static final long serialVersionUID = 1L;
 
    public Rutas(){
    	getProperties("rutas.properties");
    }
 
    private void getProperties(String idioma) {
        try {
            this.load( getClass().getResourceAsStream(idioma) );
        } catch (IOException ex) {

        }
   }
}
