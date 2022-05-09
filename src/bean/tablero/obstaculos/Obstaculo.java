package bean.tablero.obstaculos;

public class Obstaculo {

	private int ix;
	private int fx;
	private int iy;
	private int fy;
	private int ancho;
	private int alto;
	
	public Obstaculo(int ix, int fx, int iy, int fy) {
		super();
		this.ix = ix;
		this.fx = fx;
		this.iy = iy;
		this.fy = fy;
		ancho = fx - ix;
		alto = fy - iy;
	}
	public int getIx() {
		return ix;
	}
	public int getFx() {
		return fx;
	}
	public int getIy() {
		return iy;
	}
	public int getFy() {
		return fy;
	}

	public boolean contacto(double x, double y, double w, double h) {
		
		if(x > ix + ancho)
			return false;
		if(x+w < ix)
			return false;
		if(y > iy+this.alto)
			return false;
		if(y+h < iy)
			return false;

		return true;			
	}
}


