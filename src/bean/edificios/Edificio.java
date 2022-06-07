package bean.edificios;

import java.util.ArrayList;

import bean.botones.BotonBestiario;
import bean.recurso.Recurso;
import bean.tablero.casillas.Casilla;
import bean.unidades.unidades.Unidad;
import javafx.scene.image.Image;

public class Edificio {
	
	private String nombre;
	private ArrayList<Casilla> casillas;
	private Image imagen;
	private Image portada;
	private double x;
	private double y;
	private int num_alojamientos;
	protected ArrayList<Unidad> unidades_alojadas;
	
	//El coste para producirlo, el botonEdificio accederá a estos datos. El oro solo para comprar y la comida solo para producir
	private ArrayList<Recurso> coste;

	private int at;
	private int def;
	private int pg;
	private int pg_actuales;
	private String bando;
	
	//Constructor de modelo
	public Edificio(Image imagen, String nombre, Image portada, int at, int def, int pg, int num_alojamientos, ArrayList<Recurso> coste) {
		this.imagen = imagen;
		this.nombre = nombre;
		this.portada = portada;
		casillas = new ArrayList<Casilla>();
		this.coste = coste;

		this.at = at;
		this.def = def;
		this.pg = pg;
		this.pg_actuales = this.pg;
		this.num_alojamientos = num_alojamientos;
	}
	//Constructor lienzo
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
		coste = edificio.getCosteRecursos();
		
		num_alojamientos = edificio.getNumAlojamientos();
		unidades_alojadas = new ArrayList<Unidad>();
		this.at = edificio.getAt();
		this.def = edificio.getDef();
		this.pg = edificio.getPg();
		this.pg_actuales = this.pg;
	}
	public int getNumAlojamientos() {
		return num_alojamientos;
	}
	public String getNombre() {
		return nombre;
	}
	public ArrayList<Casilla> getCasillas() {
		return casillas;
	}
	public int getAt() {
		return at;
	}
	public int getDef() {
		return def;
	}
	public int getPg() {
		return pg_actuales;
	}
	public void recibir_damage(int damage) {
		pg_actuales-=damage;	
	}
	public Image getPortada() {
		return portada;
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
	public void setBando(String bando) {
		this.bando = bando;
	}
	public String getBando() {
		return bando;
	}

	public ArrayList<Recurso> getCosteRecursos() {
		return coste;
	}
	public boolean comprobar_alojamiento() {
		if(unidades_alojadas.size()<num_alojamientos) 
			return true;		
		return false;
	}
	public void alojar(Unidad unidad_alojada) {
		unidades_alojadas.add(unidad_alojada);				
	}
		
	public ArrayList<Unidad> getUnidadesAlojadas(){
		return unidades_alojadas;
	}
	public boolean contiene_unidad(Unidad unidad) {
		for(Unidad unidad_alojada : unidades_alojadas)
			if(unidad_alojada.equals(unidad))
				return true;
		return false;
	}
	public void desalojar(Unidad unidad) {
		for(Unidad unidad_alojada : unidades_alojadas)
			if(unidad_alojada.equals(unidad)) {
				unidades_alojadas.remove(unidad_alojada);
				break;
			}
	}
	public String toString() {
		String string ="";
		for(Recurso recurso : coste)
			string+="recurso = "+recurso.getRecurso()+", cantidad = "+recurso.getCantidad();
		return "Nombre : "+nombre+"Coste : "+string;
		
	}
}
