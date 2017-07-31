package me.bscal.game.entity;

import me.bscal.game.Game;
import me.bscal.game.entity.spell.Fireball;
import me.bscal.game.entity.spell.Iceblast;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.listeners.KeyboardListener;
import me.bscal.game.listeners.MouseClickListener;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.game.util.Cooldown;

public class Player extends HumanEntity{
	
	public Player(Sprite sprite, int animationLength) {
		this.sprite = sprite;
		this.animationLength = animationLength;
		this.speed = 15;
		this.layer = 1;
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		updateDirection();
		rect = new Rectangle(0, 0, 20, 26);
		collisionRect = new Rectangle(0, 0, 10*Game.XZOOM, 18*Game.YZOOM);
		xCollisionOffset = 14;
		yCollisionOffset = 20;
	}

	@Override
	public void update(Game game) {
		KeyboardListener keyboard = game.getKeyListener();
		MouseClickListener mouse = game.getMouseListener();
		
		collisionRect.x = rect.x;
		collisionRect.y = rect.y;
		
		move(game, keyboard);
		updateCooldowns();
		playerActions(game, keyboard, mouse);
		updateCamera(game, game.getRenderer().getCamera());
	}
	
	private void move(Game game, KeyboardListener keyboard) {
		boolean moved = false;
		int newDirection = direction;
		
		if(keyboard.left()) {
			collisionRect.x -= speed;
			newDirection = 1;
			moved = true;
		}
		if(keyboard.right()) {
			collisionRect.x += speed;
			newDirection = 0;
			moved = true;
		}
		if(keyboard.up()) {
			collisionRect.y -= speed;
			newDirection = 2;
			moved = true;
		}
		if(keyboard.down()) {
			if(!keyboard.isCtrlDown()) {
				collisionRect.y += speed;
			}
			newDirection = 3;
			moved = true;
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(moved) {	
			collisionRect.x += xCollisionOffset;
			collisionRect.y += yCollisionOffset;
			checkCollision(game);
			animatedSprite.update(game);
		}
		else {
			animatedSprite.reset();
		}
	}

	private void playerActions(Game game, KeyboardListener keyboard, MouseClickListener mouse) {
		if(mouse.getButton() == 1 && !isOnCooldown("fireball")) {
			AnimatedSprite animatedFireball = new AnimatedSprite(SpriteHandler.fireball, 3);
			
			Fireball spell = new Fireball(animatedFireball, this, 5, rect.x, rect.y, 
					getProjectileDirection(mouse.getX(), mouse.getY(), Game.width/2.0, Game.height/2.0));
			
			Game.getAddedEntities().add(spell);
			this.projectiles.add(spell);
			cooldowns.add(new Cooldown("fireball", this, 5));
		}
		
		else if(mouse.getButton() == 3 && !isOnCooldown("iceblast")) {
			AnimatedSprite animatedFrostbolt = new AnimatedSprite(SpriteHandler.frostbolt, 2);
			
			Iceblast spell = new Iceblast(animatedFrostbolt, this, 5, rect.x, rect.y, 
					getProjectileDirection(mouse.getX(), mouse.getY(), Game.width/2.0, Game.height/2.0));
			
			Game.getAddedEntities().add(spell);
			this.projectiles.add(spell);
			cooldowns.add(new Cooldown("iceblast", this, 5));
		}
	}
	
	public void updateCamera(Game game, Rectangle camera) {
		camera.x = rect.x + (rect.width/2*Game.XZOOM) - (camera.width / 2);
		camera.y = rect.y + (rect.height/2*Game.YZOOM) - (camera.height / 2);
	}
}

//Axis Collision Check || Allows player so slide side to side/up and down while moving forward in that direction
//POSSIBLE TO USE AT LATER TIME ???
//
//Rectangle axis = new Rectangle(collisionRect.x, pRect.y + yCollisionOffset, collisionRect.width, collisionRect.height);
//
//if(!game.getMap().checkCollision(axis, layer, game.getXZoom(), game.getYZoom()) && 
//		!game.getMap().checkCollision(axis, layer + 1, game.getXZoom(), game.getYZoom())) {
//	pRect.x = collisionRect.x - xCollisionOffset;
//	}
//
//axis.x = pRect.x + xCollisionOffset;
//axis.y = collisionRect.y;
//axis.width = collisionRect.width;
//axis.height = collisionRect.height;
//
//if(!game.getMap().checkCollision(axis, layer, game.getXZoom(), game.getYZoom()) && 
//		!game.getMap().checkCollision(axis, layer + 1, game.getXZoom(), game.getYZoom())) {
//	pRect.y = collisionRect.y - yCollisionOffset;
//}
