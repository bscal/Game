package me.bscal.game.chat;

import me.bscal.game.events.eventTypes.ChatEvent;

public interface CommandHandler {

	public boolean onCommand(ChatEvent e);
	
}
