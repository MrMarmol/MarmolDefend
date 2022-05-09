package bean.edificios;

import java.util.ArrayList;

import bean.botones.BotonBestiario;
import bean.recurso.Recurso;
import bean.tablero.casillas.Casilla;
import bean.unidades.Unidad;
import procesos.acciones.unidades.alojar.*;
import javafx.scene.image.Image;

public class Edificio {
	
	private String nombre;
	private ArrayList<Casilla> casillas;
	private ArrayList<BotonBestiario> produccion_unidades;
	private Image imagen;
	private double x;
	private double y;
	private int num_alojamientos;
	private ArrayList<AccionAlojar> alojamientos;
	
	//El coste para producirlo, el botonEdificio accederá a estos datos. El oro solo para comprar y la comida solo para producir
	private ArrayList<Recurso> coste;
	private Recurso metal;
	private Recurso madera;

	
	//Constructor de modelo
	public Edificio(Image imagen, String nombre) {
		this.imagen = imagen;
		this.nombre = nombre;
		casillas = new ArrayList<Casilla>();
		coste = new ArrayList<Recurso>();
		metal = new Recurso("METAL", 100);
		madera = new Recurso("MADERA", 100);
		coste.add(metal);
		coste.add(madera);		
	}
	//Constructor completo
	public Edificio(Image imagen, double x, double y,String nombre) {
		this.imagen = imagen;
		this.x = x;
		this.y = y;
		casillas = new ArrayList<Casilla>();
		this.nombre = nombre;
	}
	//Constructor para desvincularse del botonEdificio
	public Edificio(Edificio edificio) {		
		this.imagen = edificio.getImagen();
		this.x = edificio.getX();
		this.y = edificio.getY();
		this.nombre = edificio.getNombre();
		casillas = new ArrayList<Casilla>();
		coste = new ArrayList<Recurso>();
		metal = new Recurso("METAL", 100);
		madera = new Recurso("MADERA", 100);
		coste.add(metal);
		coste.add(madera);
		num_alojamientos = 3;
		alojamientos = new ArrayList<AccionAlojar>();
	}
	public String getNombre() {
		return nombre;
	}
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}
	public ArrayList<BotonBestiario> getProducionUnidades() {
		return produccion_unidades;
	}
	public Image getImagen() {
		return imagen;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}

	public ArrayList<Recurso> getCosteRecursos() {
		return coste;
	}
	public boolean comprobar_alojamiento() {
		if(alojamientos.size()<num_alojamientos) 
			return true;		
		return false;
	}
	public void alojar(AccionAlojar alojamiento) {
		alojamientos.add(alojamiento);				
	}
		
	public ArrayList<AccionAlojar> getUnidadesAlojadas(){
		return alojamientos;
	}
	public boolean contiene_unidad(Unidad unidad_alojada) {
		for(AccionAlojar alojamiento : alojamientos)
			if(alojamiento.getUnidad().equals(unidad_alojada))
				return true;
		return false;
	}
	public void desalojar(Unidad unidad) {
		for(AccionAlojar alojamiento : alojamientos)
			if(alojamiento.getUnidad().equals(unidad)) 
				alojamientos.remove(alojamiento);							
	}
}
