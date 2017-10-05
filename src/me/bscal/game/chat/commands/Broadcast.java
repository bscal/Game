package me.bscal.game.chat.commands;

import me.bscal.game.GUI.GUIManager;
import me.bscal.game.chat.CommandHandler;
import me.bscal.game.events.eventTypes.ChatEvent;

public class Broadcast implements CommandHandler{
	
	private final String cmd = "broadcast";
	
	public boolean onCommand(ChatEvent e) {
		if(e.getCommand().getName().equalsIgnoreCase(cmd)) {
			GUIManager.chat.addMessage("[Broadcast] This is a test!");
			return true;
		}
		return false;
	}

}
