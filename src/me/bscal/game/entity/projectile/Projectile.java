package me.bscal.game.entity.projectile;

import me.bscal.serialization.QVObject;

public interface Projectile {
	
	public final static short FIREBALL = 1;
	public final static short ICEBLAST = 2;
	
	public void launch();
	
	public void serializeProjectile(QVObject o, int mx, int my);
	
	public static void deserializeProjectile(QVObject o) {};
	
}
