package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;

public abstract class LivingEntity extends Entity{
	
	protected Rectangle collisionRect;
	protected int xCollisionOffset = 0;
	protected int yCollisionOffset = 0;
	protected double health, mana;
	protected double maxHealth = 50;
	protected double maxMana = 20;
	
	public LivingEntity() {}

	public void checkCollision(Game game) {
		Rectangle axis = new Rectangle(collisionRect.x, rect.y + yCollisionOffset, collisionRect.width, collisionRect.height);
		
		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
			rect.x = collisionRect.x - xCollisionOffset;
		}
		
		axis.x = rect.x + xCollisionOffset;
		axis.y = collisionRect.y;
		axis.width = collisionRect.width;
		axis.height = collisionRect.height;
		
		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
			rect.y = collisionRect.y - yCollisionOffset;
		}
	}
}
