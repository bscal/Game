package me.bscal.game.entity.mob;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.util.Vector2i;

public class Zombie extends NPC{
	
	public Zombie(Sprite sprite, int animationLength) {
		this.animatedSprite = (AnimatedSprite) sprite;
		this.animationLength = animationLength;
		rect = new Rectangle(15, 15, 20, 26);
		collisionRect = new Rectangle(15, 15, 18, 24);
		speed = 3;
		updateDirection();
	}
	
	public void render(Render renderer, int xZoom, int yZoom) {
		renderer.renderSprite(animatedSprite, 0xFF7200, 0xFF732611, rect.x, rect.y, xZoom, yZoom, false);
	}
	
	public void update(Game game) {
		boolean moved = false;
		int newDirection = direction;
		time++;
		move(game);
		
		if(xa < 0) {
			newDirection = 1;
			moved = true;
		}
		if(xa > 0) {
			newDirection = 0;
			moved = true;
		}
		if(ya < 0) {
			newDirection = 2;
			moved = true;
		}
		if(ya > 0) {
			newDirection = 3;
			moved = true;
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(moved) {
			collisionRect.x += xa;
			collisionRect.y += ya;
			rect.x = collisionRect.x;
			rect.y = collisionRect.y;
			animatedSprite.update(game);
		}
		else {
			animatedSprite.reset();
		}
	}
	
	public void move(Game game) {
		xa = 0;
		ya = 0;
		int px = game.getPlayer(-1).getRectangle().x - 2;
		int py = game.getPlayer(-1).getRectangle().y;
		Vector2i start = new Vector2i(rect.x >> 5, rect.y >> 5);
		Vector2i destination = new Vector2i(px >> 5, py >> 5);
		if(time % 5 == 0) {
			path = game.getMap().findPath(start, destination);
		}
		if(path != null) {
			//If a there is a Node to travel too the NPC will travel to the Node.
			if(path.size() > 0) {
				Vector2i vector = path.get(path.size() - 1).tile;
				if(rect.x < vector.getX() << 5) {
					xa++;
				}
				if(rect.x > vector.getX() << 5) {
					xa--;
				}
				if(rect.y < vector.getY() << 5 - 24) {
					ya++;
				}
				if(rect.y > vector.getY() << 5 - 24) {
					ya--;
				}
			}
			//Else travel to the nearest player.
//			else {
//				List<Player> entities = game.getMap().getNearbyPlayers(this, 50);
//				if(entities.size() > 0) {
//					Rectangle pRect = entities.get(0).getRectangle();
//					if(rect.x < pRect.x) {
//						xa++;
//					}
//					if(rect.x > pRect.x) {
//						xa--;
//					}
//					if(rect.y < pRect.y) {
//						ya++;
//					}
//					if(rect.y > pRect.y) {
//						ya--;
//					}
//				}
//			}
		}
	}

}
