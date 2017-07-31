package me.bscal.game.entity.mob;

import java.util.Random;

import me.bscal.game.Game;
import me.bscal.game.entity.HumanEntity;
import me.bscal.game.entity.Player;
import me.bscal.game.graphics.Rectangle;

public abstract class NPC extends HumanEntity{
	
	protected int xa;
	protected int ya;
	protected final Random r = new Random();
	
	public NPC() {}
	
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
}
