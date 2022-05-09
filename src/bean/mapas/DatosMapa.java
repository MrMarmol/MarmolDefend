package bean.mapas;

import java.util.ArrayList;

import bean.tablero.obstaculos.Obstaculo;

public class DatosMapa {
	private double largo;
	private double ancho;
	private ArrayList<Obstaculo> obstaculos;

	
	public DatosMapa(double largo, double ancho, ArrayList<Obstaculo> obstaculos ) {
		this.largo = largo;
		this.ancho = ancho;
		this.obstaculos = obstaculos;
	}

	public double getLargo() {
		return largo;		
	}
	public double getAncho() {
		return ancho;
	}
	public ArrayList<Obstaculo> getObstaculos() {
		return obstaculos;
	}
}
