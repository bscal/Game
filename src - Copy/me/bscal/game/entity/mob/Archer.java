package me.bscal.game.entity.mob;

import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;
import me.bscal.game.entity.spell.Iceblast;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.game.util.Cooldown;
import me.bscal.game.util.Vector2i;

public class Archer extends NPC{

	int xPos = 0;
	int yPos = 0;
	int time = 0;
	
	public Archer() {
		animatedSprite = new AnimatedSprite(SpriteHandler.playerSheet, 3);
		animationLength = 8;
		rect = new Rectangle(50, 50, 20, 26);
		collisionRect = new Rectangle(50, 50, 18, 24);
	}
	
	@Override
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
		updateCooldowns();
		shootClosest(game);
	}

	private void move(Game game) {
		collisionRect.x += xPos;
		collisionRect.y += yPos;
		checkCollision(game);
	}

	private void shootClosest(Game game) {
		List<Entity> entities = game.getMap().getNearbyEntities(this, 80);
		double min = 0;
		Rectangle target = null;
		
		for(int i = 0; i < entities.size(); i++) {
			Rectangle e = entities.get(i).getRectangle();
			double distance = new Vector2i(rect.x, rect.y).getDistance(new Vector2i(e.x, e.y));
			if(i == 0 || distance < min) {
				min = distance;
				target = e;
			}
		}
		
		if(target != null) {
			double angle = getProjectileDirection(target.x, target.y, rect.x, rect.y);
			Sprite sprite = new Sprite(SpriteHandler.frostbolt, 0, 0, 16, 16);
			sprite.rotate(angle);
			Iceblast fb = new Iceblast(sprite, this, 5, rect.x, rect.y, angle);
			if(!isOnCooldown(fb.getName())) {
				fb.init();
				cooldowns.add(new Cooldown(fb.getName(), this, 5));
			}
		}
	}
	
}
