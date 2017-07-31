package me.bscal.game.entity;

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
	
	public Spell(Sprite sprite, Entity caster, int animationLength) {
		this.sprite = sprite;
		this.castRect = caster.getRectangle();
		this.caster = caster;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		direction = caster.getDirection();
		updateDirection();
		rect = new Rectangle(castRect.getCenterX(), castRect.getCenterY(), 16, 16);
		collisionRect = new Rectangle(rect.x, rect.y, 15, 15);
	}

	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Entity getCaster() {
		return caster;
	}

	public Rectangle getCollisionRect() {
		return collisionRect;
	}

	public double getDamage() {
		return damage;
	}
	
}
