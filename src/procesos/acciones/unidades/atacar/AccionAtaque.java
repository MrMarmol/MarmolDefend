package procesos.acciones.unidades.atacar;

import bean.unidades.Unidad;
import bean.unidades.UnidadCombate;

public class AccionAtaque {

	public String atacar(UnidadCombate uc,Unidad objetivo) {
		int def = objetivo.getDef();		
		int damage = uc.getAt()-objetivo.getDef();
		
		if(damage>0)
			objetivo.setPg(objetivo.getPg()-damage);
		else 
			objetivo.setPg(objetivo.getPg()-1);
		
		if(objetivo.getPg()>0)
			return uc.getNombre()+" atacó a "+objetivo.getNombre()+" (-"+damage+" pg)";
		else 
			return uc.getNombre()+" a matado a "+objetivo.getNombre();
	}

}
