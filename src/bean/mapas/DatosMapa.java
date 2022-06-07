package bean.mapas;

import java.util.ArrayList;

import bean.tablero.Key;

public class DatosMapa {
	private double largo;
	private double ancho;
	private ArrayList<Key> obstaculos;
	private double[] coordenadasCastilloHost;
	private double[] coordenadasCastilloCliente;

	
	
	public DatosMapa(double largo, double ancho, ArrayList<Key> obstaculos, double [] coordenadasCastilloHost,
			double[] coordenadasCastilloCliente) {
		super();
		this.largo = largo;
		this.ancho = ancho;
		this.obstaculos = obstaculos;
		this.coordenadasCastilloHost = coordenadasCastilloHost;
		this.coordenadasCastilloCliente = coordenadasCastilloCliente;
	}

	public DatosMapa(double largo, double ancho, ArrayList<Key> obstaculos ) {
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
	public ArrayList<Key> getObstaculos() {
		return obstaculos;
	}

	public double[] getCoordenadasCastilloHost() {
		return coordenadasCastilloHost;
	}

	public double[] getCoordenadasCastilloCliente() {
		return coordenadasCastilloCliente;
	}



	
}
