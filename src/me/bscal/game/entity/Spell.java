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
	
	public Spell(Sprite sprite, int animationLength, Entity caster) {
		this(sprite, animationLength, caster.rect);
		this.caster = caster;
		this.direction = caster.getDirection();
	}

	public Spell(Sprite sprite, int animationLength, Rectangle origin) {
		this.sprite = sprite;
		this.castRect = origin;
		this.animationLength = animationLength;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		rect = new Rectangle(castRect.getCenterX(), castRect.getCenterY(), 16, 16);
		collisionRect = new Rectangle(rect.x, rect.y, 15, 15);
	}
	
	public Entity getCaster() {
		return caster != null ? caster : null;
	}

	public double getDamage() {
		return damage;
	}
	
}
