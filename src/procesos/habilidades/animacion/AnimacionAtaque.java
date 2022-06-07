package procesos.habilidades.animacion;


import bean.tablero.casillas.Casilla;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class AnimacionAtaque {

	private int fps;
	private int duracion;
	private Image spriteSheet;
	private int size_sprite;
	private int num_sprites;
	private Rectangle sprite;
	private Rectangle sprites[];
	private Casilla ataque;
	private int contador;
	
	public AnimacionAtaque(int duracion, Image spriteSheet, int size_sprite, int num_sprites) {
		super();
		this.spriteSheet = spriteSheet;		
		this.size_sprite = size_sprite;
		this.num_sprites = num_sprites;
		this.duracion = duracion;
		sprites = new Rectangle[num_sprites];
		obtener_sprites();
	}
	
	public Image getImagen() {
		return spriteSheet;
	}

	public Casilla getAtaque() {
		return ataque;
	}

	public void setAtaque(Casilla casillaAtacada) {
		ataque = casillaAtacada;
	}
	public void obtener_sprites() {
		
		double x = 0;
		double y = 0;
	
		for(int i = 0; i<num_sprites;i++) {
			sprite = new Rectangle(x,y,size_sprite,size_sprite);
			sprites[i] = sprite;
			x+=size_sprite;
		}
	}
	public Rectangle obtener_sprite() {
		int porciones = duracion/num_sprites;
		
		for(int i = 0; i<num_sprites; i++) 
			if(fps<porciones*i) 
				return sprites[i];	
		
		return sprites[0];
		
	}
	public void sumar_fps() {
		fps++;
	}
	
	public boolean comprobar_animacion_terminada() {
		if(fps == duracion)
			return true;
		return false;
	}		

	public void finalizar() {
		ataque = null;
		fps = 0;
		
	}
}
