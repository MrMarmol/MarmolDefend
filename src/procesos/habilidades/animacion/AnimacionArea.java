package procesos.habilidades.animacion;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class AnimacionArea {
	private int fps;
	private int duracion;
	private Image spriteSheet;
	private int size_sprite;
	private int num_sprites;
	private Rectangle sprite;
	private Rectangle sprites[];
		
	public AnimacionArea(Image spriteSheet, int size_sprite, int num_sprites, int duracion) {
		super();
		this.spriteSheet = spriteSheet;		
		this.size_sprite = size_sprite;
		this.num_sprites = num_sprites;
		this.duracion = duracion;
		sprites = new Rectangle[num_sprites];
		obtener_sprites();
	}
		

	public Image getSpriteSheet() {
		return spriteSheet;
	}
	public int getSize_sprite() {
		return size_sprite;
	}
	public int getNum_sprites() {
		return num_sprites;
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
			if(fps<porciones*(i+1)) 
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

}
