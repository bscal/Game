package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.serialization.QVField;
import me.bscal.serialization.QVObject;

public abstract class LivingEntity extends Entity{
	
	protected Rectangle collisionRect;
	protected int xCollisionOffset = 0;
	protected int yCollisionOffset = 0;
	protected float health, mana;
	protected float maxHealth = 50;
	protected float maxMana = 20;
	protected int level = 0;
	
	public LivingEntity() {}
	
	public void checkCollision(Game game) {
		checkCollision(game, collisionRect);
//		Rectangle axis = new Rectangle(collisionRect.x, rect.y + yCollisionOffset, collisionRect.width, collisionRect.height);
//		
//		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
//				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
//				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
//			rect.x = collisionRect.x - xCollisionOffset;
//		}
//		
//		axis.x = rect.x + xCollisionOffset;
//		axis.y = collisionRect.y;
//		axis.width = collisionRect.width;
//		axis.height = collisionRect.height;
//		
//		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
//				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
//				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
//			rect.y = collisionRect.y - yCollisionOffset;
//		}
	}
	
	public void checkCollision(Game game, Rectangle r) {
		Rectangle axis = new Rectangle(r.x, rect.y + yCollisionOffset, r.width, r.height);
		
		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
			rect.x = r.x - xCollisionOffset;
		}
		
		axis.x = rect.x + xCollisionOffset;
		axis.y = r.y;
		axis.width = r.width;
		axis.height = r.height;
		
		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
			rect.y = r.y - yCollisionOffset;
		}
	}
	
	public void serialize(QVObject o) {
		super.serialize(o);
		o.addField(QVField.createFloat("Health", health));
		o.addField(QVField.createFloat("Mana", mana));
	}
	
	public void deserialize(QVObject o) {
		super.deserialize(o);
		this.health = o.findField("Health").getFloat();
		this.mana = o.findField("Mana").getFloat();
	}
}
