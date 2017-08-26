package me.bscal.game.events;

import me.bscal.game.events.Event.EventType;

public class EventDispatcher {

	private Event event;
	
	public EventDispatcher(Event event) {
		this.event = event;
	}
	
	public void dispatch(EventType type, EventHandler handler) {
		if(event.handled) return;
		
		if(event.getEventType() == type) {
			event.handled = handler.onEvent(event);
		}
	}
	
}
