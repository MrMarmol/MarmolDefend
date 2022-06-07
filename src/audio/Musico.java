package audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import bean.datos_globales.DatosGlobales;
import procesos.acciones.AccionesDTO.Accion;
import sun.audio.*;

public class Musico {
	
	//El músico solo puede producir tres sonidos simultáneamente
	private static AudioStream musica;
	private static AudioStream efecto;
	private static AudioStream accion;
	
	
	public static void reproducir_musica(String campo_propiedad)
	{
		if(musica!=null)
			detener_musica();
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
	public static void reproducir_habilidad(String habilidad)
	{
		try {
			String nom = StringUtils.stripAccents(habilidad.toLowerCase().replace(" ",""));
			efecto = new AudioStream (new FileInputStream(new File(DatosGlobales.rutas.getProperty("musHabilidad")+nom+".wav")));
			AudioPlayer.player.start(efecto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public static void reproducir_accion(String sonido)  {
		try {
			sonido = sonido.toLowerCase();
			accion = new AudioStream (new FileInputStream(new File(DatosGlobales.rutas.getProperty("musAccion")+sonido+".wav")));
			AudioPlayer.player.start(accion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
