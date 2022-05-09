package procesos.cargar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bean.mapas.DatosMapa;
import bean.mapas.Rio;
import bean.tablero.obstaculos.Arbol;
import bean.tablero.obstaculos.Montana;
import bean.tablero.obstaculos.Obstaculo;
import bean.datos_globales.DatosGlobales;

public class CargadoMapa {
//es el que lee los datos del mapa que se elige para el controlador ConstructorFPartida
	
	private String ruta_mapa;
	private String largo;
	private String ancho;
	private String datos[];
	private Rio rio;
	private Arbol arbol;
	private Montana montana;
	private ArrayList<Obstaculo> obstaculos;
	private String linea;
	
	public CargadoMapa(String mapa) {
		obstaculos = new ArrayList<Obstaculo>();
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

		while ((linea = bf.readLine()) != null) {
			System.out.println(linea);
			
			switch(linea.charAt(0)) {
				
				case 'h':
					largo = linea.replace("h","");
					break;
				case 'w':
					ancho = linea.replace("w","");
					break;
				case 'r':
					linea = linea.replace("r","");
					datos = linea.split(",");
					rio = new Rio(
							Integer.parseInt(datos[0]),
							Integer.parseInt(datos[1]),
							Integer.parseInt(datos[2]),
							Integer.parseInt(datos[3])
							);
					obstaculos.add(rio);
				case 'a':
					linea = linea.replace("a","");
					datos = linea.split(",");
					arbol = new Arbol(
							Integer.parseInt(datos[0]),
							Integer.parseInt(datos[1]),
							Integer.parseInt(datos[2]),
							Integer.parseInt(datos[3])
							);
					obstaculos.add(arbol);
				case 'm':
					linea = linea.replace("m","");
					datos = linea.split(",");
					montana = new Montana(
							Integer.parseInt(datos[0]),
							Integer.parseInt(datos[1]),
							Integer.parseInt(datos[2]),
							Integer.parseInt(datos[3])
							);
					obstaculos.add(montana);
			}
		}
		return new DatosMapa(Double.parseDouble(largo), Double.parseDouble(ancho), obstaculos);
	}
}

