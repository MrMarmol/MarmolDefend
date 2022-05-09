package bean.recurso;

public class Recurso {
	public String recurso;
	public int cantidad;
	
	public Recurso(String recurso, int cantidad) {
		this.recurso = recurso;
		this.cantidad = cantidad;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
