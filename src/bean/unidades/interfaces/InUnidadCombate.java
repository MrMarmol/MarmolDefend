package bean.unidades.interfaces;

import bean.unidades.Unidad;
import bean.unidades.UnidadCombate;
import procesos.acciones.unidades.atacar.AccionAtaque;

public interface InUnidadCombate {

	public String atacar(Unidad unidad) ;
	public void usar_habilidad();
}
