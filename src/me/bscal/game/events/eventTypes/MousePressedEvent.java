package me.bscal.game.events.eventTypes;

public class MousePressedEvent extends MouseButtonEvent	{

	public MousePressedEvent(int x, int y, int button) {
		super(button, x, y, EventType.MOUSE_PRESSED);
	}
	
}
