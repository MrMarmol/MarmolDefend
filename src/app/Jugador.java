package app;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.edificios.Edificio;
import bean.recurso.Recurso;
import bean.unidades.Unidad;

public class Jugador {
	
	private Recurso oro;
	private Recurso metal;
	private Recurso comida;
	private Recurso madera;
	
	private ArrayList<Unidad> unidades;
	private ArrayList<Edificio> edificios;
	
	public Jugador() {
		super();
		oro = new Recurso(DatosGlobales.ORO,300);
		metal = new Recurso(DatosGlobales.METAL,300);;
		madera = new Recurso(DatosGlobales.MADERA,300);;
		comida = new Recurso(DatosGlobales.COMIDA,300);;
	}
	public int getOro() {
		return oro.getCantidad();
	}
	public void setOro(int oro) {
		this.oro.setCantidad(oro);
	}
	public int getMetal() {
		return metal.getCantidad();
	}
	public void setMetal(int metal) {
		this.metal.setCantidad(metal);
	}
	public int getComida() {
		return comida.getCantidad();
	}
	public void setComida(int comida) {
		this.comida.setCantidad(comida);
	}
	public int getMadera() {
		return madera.getCantidad();
	}
	public void setMadera(int madera) {
		this.madera.setCantidad(madera);;
	}
	public ArrayList<Unidad> getUnidades() {
		return unidades;
	}
	public void setUnidades(ArrayList<Unidad> unidades) {
		this.unidades = unidades;
	}
	public ArrayList<Edificio> getEdificios() {
		return edificios;
	}
	public void setEdificios(ArrayList<Edificio> edificios) {
		this.edificios = edificios;
	}	
	public void restar_recurso(String recurso, int cantidad) {
		
		switch(recurso) {
			case DatosGlobales.ORO: 	
				oro.setCantidad(oro.getCantidad()-cantidad);
				break;
			case DatosGlobales.MADERA: 	
				madera.setCantidad(madera.getCantidad()-cantidad);
				break;
			case DatosGlobales.COMIDA: 	
				comida.setCantidad(comida.getCantidad()-cantidad);
				break;
			case DatosGlobales.METAL: 	
				metal.setCantidad(metal.getCantidad()-cantidad);
				break;
		}
	}

	

}
