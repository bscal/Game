package me.bscal.game.events.eventTypes;

import me.bscal.game.events.Event;

public class MouseButtonEvent extends Event{

	protected int x, y;
	protected int button;
	
	protected MouseButtonEvent(int button, int x, int y, EventType type) {
		super(type);
		this.button = button;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getButton() {
		return button;
	}
	
}
