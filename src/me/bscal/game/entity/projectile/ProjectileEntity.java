package me.bscal.game.entity.projectile;

import me.bscal.game.Game;
import me.bscal.game.entity.Entity;
import me.bscal.game.entity.Spell;
import me.bscal.game.entity.spell.Fireball;
import me.bscal.game.entity.spell.Iceblast;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.serialization.QVField;
import me.bscal.serialization.QVObject;

public class ProjectileEntity extends Spell implements Projectile {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected double newX, newY;
	protected int projType = 0;

	private ProjectileEntity() {
		xOrigin = yOrigin = 0;
	}

	public ProjectileEntity(int x, int y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
	}

	public ProjectileEntity(Sprite sprite, int animationLength, int x, int y, double dir) {
		super(sprite, animationLength, new Rectangle(x, y, 0, 0));
		xOrigin = x;
		yOrigin = y;
		angle = dir;
	}

	public void launch() {
		newX = speed * Math.cos(angle);
		newY = speed * Math.sin(angle);
		rect.dx = rect.x;
		rect.dy = rect.y;
	}

	public void move() {
		rect.dx += newX;
		rect.dy += newY;
		rect.x = (int) rect.dx;
		rect.y = (int) rect.dy;
	}

	public ProjectileEntity setOwner(Entity e) {
		this.caster = e;
		return this;
	}

	public ProjectileEntity setProjectileType(int type) {
		this.projType = type;
		return this;
	}

	public void serializeProjectile(QVObject o, int mx, int my) {
		o.addField(QVField.createInt("x", xOrigin));
		o.addField(QVField.createInt("y", yOrigin));
		o.addField(QVField.createInt("type", projType));
		o.addField(QVField.createInt("x0", mx));
		o.addField(QVField.createInt("y0", my));
		o.addField(QVField.createInt("x1", Render.camera.width / 2));
		o.addField(QVField.createInt("y1", Render.camera.height / 2));
	}

	public static void deserializeProjectile(QVObject o) {
		int type = o.findField("type").getInt();
		int x0 = o.findField("x0").getInt();
		int y0 = o.findField("y0").getInt();
		int x1 = o.findField("x1").getInt();
		int y1 = o.findField("y1").getInt();

		ProjectileEntity proj;

		if (type == FIREBALL) {
			proj = new Fireball(SpriteHandler.fireball.getSprite(0, 0), 0, o.findField("x").getInt(),
					o.findField("y").getInt(), getProjectileDirection(x0, y0, x1, y1));
		} else if (type == ICEBLAST) {
			proj = new Iceblast(SpriteHandler.frostbolt.getSprite(0, 0), 0, o.findField("x").getInt(),
					o.findField("y").getInt(), getProjectileDirection(x0, y0, x1, y1));
		} else {
			proj = new ProjectileEntity();
		}

		Game.getAddedEntities().add(proj);
	}

	public void update(Game game) {
		assert (false);
	}

	public void printProjectile() {
		System.out.println("Projectile:");
		System.out.println("\txOrigin: " + xOrigin);
		System.out.println("\tyOrigin: " + yOrigin);
		System.out.println("\tAngle: " + angle);
		System.out.println("\tType: " + projType);
	}
}
