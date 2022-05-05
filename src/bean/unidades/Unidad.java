package bean.unidades;

import java.util.ArrayList;

import javafx.scene.image.Image;
import procesos.animacion.Animacion;

public class Unidad {
	
	private String nombre;
	private String descripcion;
	private Image cara;
	private Image portada;
	private Animacion animacion;
	private int at;
	private int def;
	private int pg;
	private ArrayList<String> habilidades;
	private String bando;
	
	public Unidad(String nombre, String descripcion, Image cara, Image portada, Animacion animacion, int at, int def, int pg,
			ArrayList<String> habilidades, String bando) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cara = cara;
		this.portada = portada;
		this.animacion = animacion;
		this.at = at;
		this.def = def;
		this.pg = pg;
		this.habilidades = habilidades;
		this.bando = bando;
	}
	public void recibir_ataque(int damage) {
		pg -=damage-def;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public Image getCara() {
		return cara;
	}
	public Image getPortada(){
		return portada;
	}
	public Animacion getAnimacion() {
		return animacion;
	}
	public int getAt() {
		return at;
	}
	public void setAt(int at) {
		this.at = at;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getPg() {
		return pg;
	}
	public void setPg(int pg) {
		this.pg = pg;
	}
	public ArrayList<String> getHabilidades() {
		return habilidades;
	}
	public void setHabilidades(ArrayList<String> habilidades) {
		this.habilidades = habilidades;
	}
	public String getBando() {
		return bando;
	}
	public void setBando(String bando) {
		this.bando = bando;
	}
	
	public String habilidadesToString() {
		String Shabilidades = "";
		for(String habilidad : habilidades)
			Shabilidades += habilidad+",";
		return Shabilidades;
	}
	
	public String toString() {
		if(habilidades == null)
		return "Unidad : "+nombre+
				"\nDescripcion : "+descripcion+
				"\nCara : "+cara.impl_getUrl()+	
				"\nPortada : "+portada.impl_getUrl()+				
				"\nAtaque : "+at+
				"\nDefensa : "+def+
				"\nVida : "+pg+
				"\nBando : "+bando+".";
		else
		return "Unidad : "+nombre+
				"\nDescripcion : "+descripcion+
				"\ncara : "+cara.impl_getUrl()+		
				"\nPortada : "+portada.impl_getUrl()+				
				"\nAtaque : "+at+
				"\nDefensa : "+def+
				"\nVida : "+pg+
				"\nHabilidades : "+habilidadesToString()+
				"\nBando : "+bando+".";		
	}
	

}
