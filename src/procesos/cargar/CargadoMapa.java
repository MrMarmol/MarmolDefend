package procesos.cargar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bean.mapas.DatosMapa;
import bean.tablero.Key;
import bean.tablero.casillas.Casilla;
import bean.datos_globales.DatosGlobales;

public class CargadoMapa {
//es el que lee los datos del mapa que se elige para el controlador ConstructorPartida
	
	private String ruta_mapa;
	private String largo;
	private String ancho;
	private String[] coordenadasCastillo;
	private double  castilloHostX;
	private double  castilloHostY;
	private double  castilloClienteX;
	private double  castilloClienteY;
	private String datos[];

	
	private ArrayList<Key> casillas_obstaculo;
	double x;
	double y;
	
	private String linea;
	
	public CargadoMapa(String mapa) {
		casillas_obstaculo = new ArrayList<Key>();
		ruta_mapa = DatosGlobales.rutas.getProperty("mapa")+mapa+".txt";
	}
	
	public String getLargo() {
		return largo;
	}

	public String getAncho() {
		return ancho;
	}


	public DatosMapa cargar_mapa() throws IOException {
		System.out.println(ruta_mapa);		
		BufferedReader bf = new BufferedReader(new FileReader(new File(ruta_mapa)));
		largo = bf.readLine().replace("h", "");
		ancho = bf.readLine().replace("w","");
		
		//Coordenadas del castillo inicial de los jugadores(h:host,c:cliente)
		coordenadasCastillo = bf.readLine().replace("s","").split(",");
		castilloHostX = Double.parseDouble(coordenadasCastillo[0]);
		castilloHostY = Double.parseDouble(coordenadasCastillo[1]);
		coordenadasCastillo = bf.readLine().replace("c","").split(",");
		castilloClienteX = Double.parseDouble(coordenadasCastillo[0]);
		castilloClienteY = Double.parseDouble(coordenadasCastillo[1]);
		
		

		while ((linea = bf.readLine()) != null) {
			
			System.out.println(linea);
			datos = linea.split(",");
			x = Double.parseDouble(datos[0]);
			y = Double.parseDouble(datos[1]);			
			casillas_obstaculo.add(new Key(x, y));
			
			
			
		}
		return new DatosMapa(Double.parseDouble(largo), Double.parseDouble(ancho), casillas_obstaculo, new double[] {castilloHostX, castilloHostY}, 
				new double[] {castilloClienteX, castilloClienteY});
	}
}

