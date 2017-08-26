package me.bscal.game.events.eventTypes;

public class MouseReleasedEvent extends MouseButtonEvent{

	public MouseReleasedEvent(int x, int y, int button) {
		super(button, x, y, EventType.MOUSE_RELEASED);
	}
	
}
