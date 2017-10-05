package me.bscal.game.events.eventTypes;

import me.bscal.game.GUI.GUITextList;
import me.bscal.game.chat.Command;
import me.bscal.game.entity.Entity;
import me.bscal.game.events.Event;

public class ChatEvent extends Event{

	private String message;
	private Entity sender;
	private GUITextList textbox;
	private Command cmd;
	
	private boolean cancelled = false;
	
	public ChatEvent(GUITextList textbox, String msg, Entity e) {
		super(EventType.CHAT_EVENT);
		this.message = msg;
		this.sender = e;
		this.textbox = textbox;
		if(msg.startsWith("/")) {
			cancelled = true;
			cmd = new Command(msg, e);
		}
	}
	
	public Command getCommand() {
		return cmd;
	}
	
	public GUITextList getTextBox() {
		return textbox;
	}
	
	public void setCancelled(boolean canncelled) {
		this.cancelled = canncelled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Entity getSender() {
		return sender;
	}
}
