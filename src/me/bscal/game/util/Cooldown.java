package me.bscal.game.util;

public class Cooldown {

	private String id;
	private double length;
	private boolean onCooldown = true;
	private int updates = 0;
	
	public Cooldown(String id, double length) {
		this.id = id;
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
	
	public double getRemainingTime() {
		return length;
	}
	
	public boolean onCooldown() {
		return onCooldown;
	}
}
