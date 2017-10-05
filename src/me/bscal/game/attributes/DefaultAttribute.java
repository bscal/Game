package me.bscal.game.attributes;

import me.bscal.game.entity.Entity;

public class DefaultAttribute {

	private final Attribute str;
	private final Attribute agl;
	private final Attribute intel;
	
	public DefaultAttribute(Entity e) {
		str = new Attribute("Strength", new String[] {"Increases Physical Damage and Health"});
		agl = new Attribute("Agility", new String[] {"Increases Attack Speed and Movement Speed"});
		intel = new Attribute("Intelligance", new String[] {"Increases Magic Damage and Mana Pool"});
	}
	
}
