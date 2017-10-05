package me.bscal.game.entity.mob;

import java.util.List;
import java.util.Random;

import me.bscal.game.Game;
import me.bscal.game.entity.LivingEntity;
import me.bscal.game.entity.Player;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.mapping.Node;
import me.bscal.game.util.Vector2i;

public abstract class NPC extends LivingEntity{
	
	protected int xa, ya, xPos, yPos;
	protected final Random r = new Random();
	protected List<Node> path = null;
	
	public NPC() {}
	
	public void init() {
		if(collisionRect == null) {
			collisionRect = new Rectangle(rect.x, rect.y, rect.width, rect.height);
		}
		Game.getAddedEntities().add(this);
	}
	
	/**
	 * @deprecated
	 * Use HumanEntity's checkCollision(Game game) instead.
	 */
	public void checkAICollision(Game game, Player player) {
		Rectangle axis = new Rectangle(collisionRect.x, rect.y + yCollisionOffset, collisionRect.width, collisionRect.height);
		Rectangle pRect = player.getRectangle();
		
		if(!game.getMap().checkCollision(axis, layer, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, layer, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, layer + 1, game.getXZoom(), game.getYZoom())) {
			
			if(rect.x < pRect.x) {
				collisionRect.x++;
			}
			if(rect.x > pRect.x) {
				collisionRect.x--;
			}
			xa = collisionRect.x - xCollisionOffset;
		}
		
		axis.x = rect.x + xCollisionOffset;
		axis.y = collisionRect.y;
		axis.width = collisionRect.width;
		axis.height = collisionRect.height;
			
		if(!game.getMap().checkCollision(axis, 0, game.getXZoom(), game.getYZoom()) && 
				!game.getMap().checkCollision(axis, 1, game.getXZoom(), game.getYZoom()) &&
				!game.getMap().checkCollision(axis, 2, game.getXZoom(), game.getYZoom())) {
			if(rect.y < pRect.y) {
				collisionRect.y++;
			}
			if(rect.y > pRect.y) {
				collisionRect.y--;
			}
			ya = collisionRect.y - yCollisionOffset;
		}
	}
	
	public boolean doNothing() {
		return false;
	}
	
	public boolean chooseAction(Game game) {
		//TODO Make
		return false;
	}
	
	public void move(Game game) {
		boolean moved = false;
		int newDirection = direction;
		time++;
		movePathfind(game);
		
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
			if(collisionRect == null) {
				rect.x += xa;
				rect.y += ya;
			}
			else {
				collisionRect.x += xa;
				collisionRect.y += ya;
				rect.x = collisionRect.x;
				rect.y = collisionRect.y;
			}
			if(animatedSprite != null) animatedSprite.update(game);
		}
		else {
			if(animatedSprite != null) animatedSprite.reset();
		}
	}
	
	public void ambientMove(Game game) {
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
			collisionRect.x += xPos;
			collisionRect.y += yPos;
			checkCollision(game);
			if(animatedSprite != null) animatedSprite.update(game);
		}
		else {
			if(animatedSprite != null) animatedSprite.reset();
		}
	}
	
	public void movePathfind(Game game) {
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
		}
	}
}
