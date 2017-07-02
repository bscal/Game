package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.Rectangle;
import me.bscal.game.Render;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public abstract class Spell implements GameObject{

	protected Rectangle rect;
	protected Rectangle hostRect;
	protected Sprite spellSprite;
	protected AnimatedSprite animatedSprite = null;
	protected int direction = 0;	//Right, Left, Up, Down
	protected int animationLength = 0;
	protected int speed = 15;
	protected int lifespan = 0;
	protected int maxLifespan = 45;

	public Spell() {}
	
	public Spell(Sprite sprite, Rectangle spawn, int animationLength) {
		this.spellSprite = sprite;
		this.hostRect = spawn;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		rect = new Rectangle(hostRect.x, hostRect.y, 16, 16);
		rect.createGraphics(3, 25676);
	}
	
	public Spell(Sprite sprite, Rectangle spawn, int animationLength, int xOffset, int yOffset) {
		this.spellSprite = sprite;
		this.hostRect = spawn;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		rect = new Rectangle(hostRect.x + xOffset, hostRect.y + yOffset, 16, 16);
		rect.createGraphics(3, 25676);
	}
	
	protected abstract void onDestroy(Game game);
	
	//abstract void onCollision(Game game);
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else if(spellSprite != null) {
			renderer.renderSprite(spellSprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else {
			renderer.renderRectangle(rect, xZoom, yZoom, false);
		}
	}
	
	public Rectangle getRectangle() {
		return rect;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLifespan() {
		return lifespan;
	}

	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}

	public int getMaxLifespan() {
		return maxLifespan;
	}

	public void setMaxLifespan(int maxLifespan) {
		this.maxLifespan = maxLifespan;
	}
}
