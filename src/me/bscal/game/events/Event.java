package me.bscal.game.events;

public class Event {

	public enum EventType {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED,
		CHAT_EVENT
	}
	
	private EventType type;
	boolean handled;
	
	protected Event(EventType type) {
		this.type = type;
	}
	
	public EventType getEventType() {
		return type;
	}
	
}
