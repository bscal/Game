package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public abstract class Spell extends Entity implements GameObject{

	protected Rectangle castRect;
	protected int lifespan = 0;
	protected int maxLifespan = 45;
	protected double damage, mana, cooldown;
	protected Entity caster;
	protected Rectangle collisionRect;

	public Spell() {}
	
	public Spell(Sprite sprite, Rectangle spawn, int animationLength) {
		this.sprite = sprite;
		this.castRect = spawn;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		rect = new Rectangle(castRect.getCenterX(), castRect.getCenterY(), 16, 16);
	}
	
	public Spell(Sprite sprite, Entity caster, int animationLength) {
		this.sprite = sprite;
		this.castRect = caster.getRectangle();
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		direction = caster.getDirection();
		updateDirection();
		rect = new Rectangle(castRect.getCenterX(), castRect.getCenterY(), 16, 16);
	}
	
	public void onDestroy(Game game) {}

	public void onCast(Game game) {}

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
	
	public Rectangle getCastRect() {
		return castRect;
	}

	public void setCastRect(Rectangle castRect) {
		this.castRect = castRect;
	}

	public Entity getCaster() {
		return caster;
	}

	public void setCaster(Entity caster) {
		this.caster = caster;
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public void setCollisionRect(Rectangle collisionRect) {
		this.collisionRect = collisionRect;
	}

	
	
}
