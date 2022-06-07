package bean.unidades.unidades;

import java.util.ArrayList;

import bean.datos_globales.DatosGlobales;
import bean.recurso.Recurso;
import bean.unidades.sprites.SpritesUnidad;
import javafx.scene.image.Image;
import procesos.habilidades.Habilidad;

public class Unidad {
	
	private String nombre;
	private String descripcion;
	private Image cara;
	private Image portada;
	private SpritesUnidad spritesUnidad;
	private int at;
	private int def;
	private int mana;
	private int mana_actual;
	private int bonus_at;
	private int bonus_def;
	private int pgTotales;
	private int pg;
	private int mov;
	private int movTotal;
	private ArrayList<Habilidad> habilidades;
	private ArrayList<Recurso> coste;
	private String bando;	
	private boolean accion_realizada;
	
	public Unidad(String nombre, String descripcion, Image cara, Image portada, SpritesUnidad spritesUnidad, int at, int def, int pg, int mov, int mana, 
			ArrayList<String> habilidades, ArrayList<Recurso> coste,String bando) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cara = cara;
		this.portada = portada;
		this.spritesUnidad = spritesUnidad;
		this.at = at;
		this.def = def;
		this.pgTotales = pg;
		this.pg = pgTotales;
		this.mov = mov;
		movTotal = mov;
		this.mana = mana;
		mana_actual = mana;
		this.habilidades = DatosGlobales.obtener_habilidades(habilidades);
		this.coste = coste;
		this.bando = bando;
		bonus_at = 0;
		bonus_def= 0;
	}	
	
	public Unidad(Unidad unidad) {
		super();
		this.nombre = unidad.getNombre();
		this.descripcion = unidad.getDescripcion();
		this.cara = unidad.getCara();
		this.portada = unidad.getPortada();
		this.spritesUnidad = new SpritesUnidad(unidad.getSpritesUnidad());
		this.at = unidad.getAt();
		this.def = unidad.getDef();
		this.pgTotales = unidad.getPgTotales();
		this.pg = pgTotales;
		this.mov = unidad.getMov();
		movTotal = mov;
		this.mana = unidad.getMana();
		mana_actual = mana;
		this.habilidades = unidad.getHabilidades();
		this.coste = unidad.getCoste();
		this.bando = unidad.getBando();
		bonus_at = 0;
		bonus_def= 0;
	}
	public boolean getAccion() {
		return accion_realizada;
	}
	public void accion_realizada() {
		this.accion_realizada = true;
	}
	public void setAccionRealizada(boolean valor) 
	{
		this.accion_realizada = valor;
	}
	public void reducir_mov() {
		this.mov--;
	}

	public void recuperar_mov() {
		mov = movTotal;
	}
	
	public void recuperarMana() {		
		mana_actual+=3;
		if(mana_actual>mana)
			mana_actual = mana;
	}
	public ArrayList<Recurso> getCoste() {
		return coste;
	}

	public int getPgTotales() {
		return pgTotales;
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
	public SpritesUnidad getAnimacion() {
		return spritesUnidad;
	}
	
	public int getAt() {
		return at;
	}

	public int getDef() {
		return def;
	}

	public int getFullAt() {
		return at+bonus_at;
	}

	public int getFullDef() {
		return def+bonus_def;
	}
	
	public SpritesUnidad getSpritesUnidad() {
		return spritesUnidad;
	}

	public int getMana() {
		return mana_actual;
	}
	public int getMov() {
		return mov;
	}
	public void setMov(int mov) {
		this.mov = mov;
	}
	public void reducir_mana(int coste) {
		mana_actual-=coste;
	}
	public int getPg() {
		return pg;
	}
	public void setPg(int pg) {
		this.pg = pg;
	}
	public ArrayList<Habilidad> getHabilidades() {
		return habilidades;
	}
	public void setHabilidades(ArrayList<Habilidad> habilidades) {
		this.habilidades = habilidades;
	}
	public String getBando() {
		return bando;
	}
	public void setBando(String bando) {
		this.bando = bando;
	}
	public void aumentar_def(int bonus_def) {
		this.bonus_def+=bonus_def;
	}
	public void aumentar_at(int bonus_at) {
		this.bonus_at+=bonus_at;
	}
	public void reducir_def(int bonus_def) {
		this.bonus_def-=bonus_def;
	}
	public void reducir_at(int bonus_at) {
		this.bonus_at-=bonus_at;
	}
	public void recibir_damage(int damage) {
		pg -= damage;
	}
	public void curar(int pg) {
		if(this.pg+pg >pgTotales)
			this.pg = pgTotales;
		else this.pg +=pg;
	}
	public String habilidadesToString() {
		String Shabilidades = "";
		for(Habilidad habilidad : habilidades)
			Shabilidades += habilidad.getNombre()+",";
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
