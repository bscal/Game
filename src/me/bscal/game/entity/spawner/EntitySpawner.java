package me.bscal.game.entity.spawner;

import me.bscal.game.Game;
import me.bscal.game.entity.LivingEntity;
import me.bscal.game.entity.mob.EntityType;
import me.bscal.game.entity.mob.Skeleton;

public class EntitySpawner extends Spawner{

	public EntitySpawner(int x, int y, int life, int amount, int entityType) {
		super(x, y, SpawnerType.MOB);
		for(int i = 0; i < amount; i++) {
			LivingEntity e = getType(entityType);
			e.init();
		}
	}

	public void update(Game game) {
	}

	private LivingEntity getType(int type) {
		LivingEntity e;
		switch (type) {
		
		case EntityType.SKELETON:
			e = new Skeleton(super.x, super.y);
			e.init();
		
		default:
			assert(false);
			return null;
		}
	}
	
}
