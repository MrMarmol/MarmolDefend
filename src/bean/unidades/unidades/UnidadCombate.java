package bean.unidades.unidades;

import java.util.ArrayList;

import bean.edificios.Edificio;
import bean.unidades.sprites.SpritesUnidad;
import javafx.scene.image.Image;
import procesos.habilidades.Habilidad;

public class UnidadCombate extends Unidad {
		

	public UnidadCombate(Unidad unidad) {
		super(unidad);
	}
	public String atacar(Unidad objetivo) {
		int damage = getFullAt()-objetivo.getFullDef();
		
		if(damage>0)
			objetivo.recibir_damage(damage);
		else 
			objetivo.recibir_damage(1);
		
		if(objetivo.getPg()>0)
			return getNombre()+" atacó a "+objetivo.getNombre()+" (-"+damage+" pg)";
		else 
			return getNombre()+" a matado a "+objetivo.getNombre();			
	}
	public String atacar(Unidad objetivo, int damage_extra) {
		int damage = getFullAt()-objetivo.getFullDef()+damage_extra;
		
		if(damage>0)
			objetivo.recibir_damage(damage);
		else 
			objetivo.recibir_damage(1);
		
		if(objetivo.getPg()>0)
			return getNombre()+" atacó a "+objetivo.getNombre()+" (-"+damage+" pg)";
		else 
			return getNombre()+" a matado a "+objetivo.getNombre();			
	}
	
	public String atacar(Edificio objetivo) {
		int damage = getFullAt()-objetivo.getDef();
		
		if(damage>0)
			objetivo.recibir_damage(damage);
		else 
			objetivo.recibir_damage(1);
		
		if(objetivo.getPg()>0)
			return getNombre()+" atacó a "+objetivo.getNombre()+" (-"+damage+" pg)";
		else 
			return getNombre()+" ha destruido a "+objetivo.getNombre();			
	}
}
