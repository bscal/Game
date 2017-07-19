package me.bscal.game.entity.particle;

import java.util.Random;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class Particle extends Entity{

	protected final Random r = new Random();
	
	private int life;
	private int time = 0;
	protected double xa, ya, za; 
	protected double xx, yy, zz;
	
	public Particle(int x, int y, int life) {
		this.xx = x;
		this.yy = y;
		this.life = life;
		rect = new Rectangle(x, y, 3, 3);
		rect.createGraphics(51435);
		layer = 0;
		
		this.xa = r.nextGaussian();
		this.ya = r.nextGaussian();
		this.zz = 1.0;
	}
	
	@Override
	public void render(Render renderer, int xZoom, int yZoom) {
		rect.x = (int) xx;
		rect.y = (int) yy - (int) zz;
		renderer.renderRectangle(rect, xZoom, yZoom, false);
	}

	@Override
	public void update(Game game) {
		time++;
		if(time > 9999) {
			time = 0;
		}
		if(time > life) {
			remove();
		}
		
		if(!checkCollision(game, rect)) {
			za -= 0.1;
			if(zz > 0) {
				zz = 0;
				za *= -1;
			}
			xx += xa;
			yy += ya;
			zz += za;
		}
	}

}
