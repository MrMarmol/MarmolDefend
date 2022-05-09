package bean.tablero.casillas;

import bean.tablero.obstaculos.Obstaculo;
import bean.unidades.Unidad;
import javafx.scene.control.Button;

public class CasillaObstaculo {	
	
	private double x;
	private double y;
	private Obstaculo obstaculo;
	
	public CasillaObstaculo(double x, double y, Obstaculo obstaculo) {
		this.obstaculo = obstaculo;
	}
	
	public Obstaculo getObstaculo() {
		return obstaculo;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
}



