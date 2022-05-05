package procesos.animacion;

import bean.tablero.casillas.Casilla;
import bean.unidades.Unidad;

public class UnidadEnMovimiento {
	
	private Casilla casillaOrigen;
	private Casilla casillaDestino;
	private Unidad unidad;
	private int pasos;
	private int paso;
	private String direccion;
	
	public UnidadEnMovimiento(Casilla casillaOrigen, Casilla casillaDestino, Unidad unidad, String direccion) {
		super();
		this.casillaOrigen = casillaOrigen;
		this.casillaDestino = casillaDestino;
		this.unidad = unidad;
		this.direccion = direccion;
		pasos = 0;
	}
	
	public Casilla getCasillaOrigen() {
		return casillaOrigen;
	}
	public Casilla getCasillaDestino() {
		return casillaDestino;
	}
	public Unidad getUnidad() {
		return unidad;
	}
	public int paso() {
		return pasos;
	}
	public void sumarPaso() {
		pasos+=paso;
	}
	public void setDireccion(String movimiento) {
		this.direccion = movimiento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setPaso(int paso) {
		this.paso = paso;
	}



}
