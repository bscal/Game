package me.bscal.game.entity.mob;

import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.entity.Player;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.sprites.SpriteHandler;

public class Skeleton extends NPC{

//	private SpriteSheet sprite = SpriteHandler.playerSheet;
	
	public Skeleton(int x, int y) {
		rect = new Rectangle(x, y, 16, 20);
		sprite = SpriteHandler.playerSheet.getSprite(1, 1);
		maxHealth = 10;
		speed = 5.0f;
	}
	
	public void update(Game game) {
		if(!chooseAction(game)) {
			List<Player> players = game.getMap().getNearbyPlayers(this, 1);
			if(players.isEmpty()) {
				ambientMove(game);
			}
			else {
				move(game);
			}
		}
		
	}

}
