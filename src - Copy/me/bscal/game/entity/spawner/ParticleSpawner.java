package me.bscal.game.entity.spawner;

import me.bscal.game.Game;
import me.bscal.game.entity.particle.Particle;
import me.bscal.game.entity.spawner.Spawner;

public class ParticleSpawner extends Spawner{

	public ParticleSpawner(int x, int y, int life, int amount) {
		super(x, y, SpawnerType.PARTICLE);
		for(int i = 0; i < amount; i++) {
			Particle p = new Particle(x, y, 50);
			Game.getAddedEntities().add(p);
		}
	}

	public void update(Game game) {}
}
