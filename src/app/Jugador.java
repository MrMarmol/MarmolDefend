package app;

import java.util.ArrayList;

import bean.edificios.Edificio;
import bean.unidades.Unidad;

public class Jugador {
	
	private int oro;
	private int metal;
	private int comida;
	private int madera;
	
	private ArrayList<Unidad> unidades;
	private ArrayList<Edificio> edificios;
	
	public Jugador() {
		super();
		oro = 500;
		metal = 500;
		madera = 500;
		comida = 500;
	}
	public int getOro() {
		return oro;
	}
	public void setOro(int oro) {
		this.oro = oro;
	}
	public int getMetal() {
		return metal;
	}
	public void setMetal(int metal) {
		this.metal = metal;
	}
	public int getComida() {
		return comida;
	}
	public void setComida(int comida) {
		this.comida = comida;
	}
	public int getMadera() {
		return madera;
	}
	public void setMadera(int madera) {
		this.madera = madera;
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

	

}
