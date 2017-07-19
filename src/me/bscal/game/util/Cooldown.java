package me.bscal.game.util;

import me.bscal.game.entity.Entity;

public class Cooldown {

	private String id;
	private Entity caster;
	private double length;
	private boolean onCooldown = true;
	private int updates = 0;
	
	public Cooldown(String id, Entity caster, double length) {
		this.id = id;
		this.caster = caster;
		this.length = length;
	}
	
	public void update() {
		updates++;
		if(updates > 30 && onCooldown) {
			length--;
			if(length == 0) {
				onCooldown = false;
				
			}
		}
	}
	
	public String getId() {
		return id;
	}
	
	public Entity getCaster() {
		return caster;
	}
	
	public double getRemainingTime() {
		return length;
	}
	
	public boolean onCooldown() {
		return onCooldown;
	}
}
