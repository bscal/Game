package me.bscal.game.events.eventTypes;

import me.bscal.game.events.Event;

public class MouseMoveEvent extends Event{

	private int x, y;
	private boolean dragged;
	
	public MouseMoveEvent(int x, int y, boolean dragged) {
		super(EventType.MOUSE_MOVED);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isDragged() {
		return dragged;
	}
	
}
