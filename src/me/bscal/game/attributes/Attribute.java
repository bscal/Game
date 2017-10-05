package me.bscal.game.attributes;

import me.bscal.game.sprites.Sprite;

public class Attribute {
	
	private static final int DEFAULT_MAX = 99;
	
	public Sprite icon;
	protected final String name;
	protected final String[] desciption;
	
	public int value;
	public final int maxValue;
	
	public Attribute(String name, String[] desc) {
		this(name, desc, 0, DEFAULT_MAX, null);
	}
	
	public Attribute(String name, String[] desc, int value, int maxValue) {
		this(name, desc, value, maxValue, null);
	}
	
	public Attribute(String name, String[] desc, int value, int maxValue, Sprite icon) {
		this.icon = icon;
		this.name = name;
		this.desciption = desc;
		this.value = value;
		this.maxValue = maxValue;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getDesciption() {
		return desciption;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getMaxValue() {
		return maxValue;
	}
	
	public boolean equals(String name) {
		return this.name.equals(name);
	}
	
}
