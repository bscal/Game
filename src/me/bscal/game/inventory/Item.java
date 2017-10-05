package me.bscal.game.inventory;

import me.bscal.game.sprites.Sprite;

public class Item {
	
	public enum Quality {
		COMMON, UNCOMMON, RARE, LEGENDARY;
	}

	public int type;
	public int amount;
	
	public String name;
	public Sprite icon;
	public Quality quality;
	public int slot;
	public int levelRequired;
	public int maxStackSize;
	
	public String[] lore;
	//TODO public Map<Stat, Short> attributes;
	
	public Item() {
		this(0, 1);
	}
	
	public Item(int type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public Item setType(int type) {
		this.type = type;
		return this;
	}
	
	public Item setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public Item setName(String name) {
		this.name = name;
		return this;
	}
	
	public Item setSprite(Sprite sprite) {
		this.icon = sprite;
		return this;
	}
	
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public void setLvlRequired(int lvlRequired) {
		this.levelRequired = lvlRequired;
	}
	
	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}
	
	public void setLore(String[] lore) {
		this.lore = lore;
	}
	
}
