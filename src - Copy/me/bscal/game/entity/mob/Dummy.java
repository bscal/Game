package me.bscal.game.entity.mob;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;

public class Dummy extends NPC{
	
	private int xPos = 0;
	private int yPos = 0;
	private int time = 0;
	
	public Dummy(Sprite sprite, int animationLength) {
		this.animatedSprite = (AnimatedSprite) sprite;
		this.animationLength = animationLength;
		rect = new Rectangle(150, 150, 20, 26);
		collisionRect = new Rectangle(rect.x, rect.x, 10*Game.XZOOM, 18*Game.YZOOM);
		speed = 5;
		updateDirection();
		
	}
	
	public void update(Game game) {
		boolean moved = false;
		int newDirection = direction;
		time++;
		
		if(time % (r.nextInt(60) + 20) == 0) {
			xPos = r.nextInt(3) - 1;
			yPos = r.nextInt(3) - 1;
			if(r.nextInt(4) == 0) {
				xPos = 0;
				yPos = 0;
			}
		}
		
		if(xPos < 0) {
			newDirection = 1;
			moved = true;
		}
		if(xPos > 0) {
			newDirection = 0;
			moved = true;
		}
		if(yPos < 0) {
			newDirection = 2;
			moved = true;
		}
		if(yPos > 0) {
			newDirection = 3;
			moved = true;
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(moved) {
			move(game);
			animatedSprite.update(game);
			
		}
		else {
			animatedSprite.reset();
		}
	}
	
	private void move(Game game) {
		collisionRect.x += xPos;
		collisionRect.y += yPos;
		checkCollision(game);
	}
}
