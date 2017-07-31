package me.bscal.game.entity.spawner;

import me.bscal.game.entity.Entity;
import me.bscal.game.graphics.Rectangle;

public abstract class Spawner extends Entity{

	public enum SpawnerType {
		PARTICLE, MOB
	}
	
	@SuppressWarnings("unused")
	private SpawnerType type;
	
	public Spawner(int x, int y, SpawnerType type) {
		this.type = type;
		isInvulnerable = true;
		rect = new Rectangle(x, y, 0, 0);
	}
}
