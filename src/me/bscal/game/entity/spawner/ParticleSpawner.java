package me.bscal.game.entity.spawner;

import me.bscal.game.Game;
import me.bscal.game.entity.particle.Particle;
import me.bscal.game.entity.spawner.Spawner;
import me.bscal.game.graphics.Render;

public class ParticleSpawner extends Spawner{

	public ParticleSpawner(int x, int y, int life, int amount) {
		super(x, y, SpawnerType.PARTICLE);
		for(int i = 0; i < amount; i++) {
			Particle p = new Particle(x, y, 50);
			Game.getAddedEntities().add(p);
		}
	}
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		if(animatedSprite != null) {
			renderer.renderSprite(animatedSprite, rect.x, rect.y, xZoom, yZoom, false);
		}
		else if(sprite != null) {
			renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, false);
		}
	}
}
