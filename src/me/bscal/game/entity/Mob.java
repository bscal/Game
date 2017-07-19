package me.bscal.game.entity;

import java.util.Random;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public abstract class Mob extends Entity implements GameObject{

	protected Rectangle rect;
	protected Rectangle collisionRect = null;
	protected Sprite entity;
	protected AnimatedSprite animatedSprite = null;
	@SuppressWarnings("unused")
	private int animationLength = 0;
	private int layer = 1;
	protected int direction = 3;	//Right, Left, Up, Down
	protected int speed = 8;
	protected final Random r = new Random();
	
	public Mob() {}
	
	public Mob(Sprite sprite, int animationLength, int x, int y) {
		entity = sprite;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		rect = new Rectangle(x, y, sprite.getWidth(), sprite.getWidth());
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else if(entity != null) {
			renderer.renderSprite(entity, rect.x, rect.y, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, false);
		}
	}

	public void update(Game game) {
		
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return false;
	}

	public Rectangle getRectangle() {
		return rect;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getLayer() {
		return layer;
	}
	
}
