package audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import bean.datos_globales.DatosGlobales;
import sun.audio.*;

public class Musico {
	
	private static AudioStream musica;
	private static AudioStream efecto;
	
	public Musico() {}
	
	public static void reproducir_musica(String campo_propiedad)
	{
		try {
			musica = new AudioStream (new FileInputStream(new File(DatosGlobales.rutas.getProperty(campo_propiedad))));
			AudioPlayer.player.start(musica);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void detener_musica() 
	{
		AudioPlayer.player.stop(musica);
	}
	public static void reproducir_efecto(String campo_propiedad)
	{
		try {
			efecto = new AudioStream (new FileInputStream(new File(DatosGlobales.rutas.getProperty(campo_propiedad))));
			AudioPlayer.player.start(efecto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
