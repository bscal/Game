package me.bscal.game.chat;

import java.util.ArrayList;
import java.util.List;

import me.bscal.game.GUI.GUIManager;
import me.bscal.game.events.eventTypes.ChatEvent;

public class ChatCommandHandler {

	private List<CommandHandler> commands = new ArrayList<CommandHandler>();
	
	public ChatCommandHandler() {
	}
	
	public void registerCommand(CommandHandler cmd) {
		commands.add(cmd);
	}
	
	public void unregisterCommand(CommandHandler cmd) {
		commands.remove(cmd);
	}
	
	public void dispatch(ChatEvent e) {
		if(e.getCommand() != null) {
			for(CommandHandler c : commands) {
				if(c.onCommand(e)) {
					return;
				}
			}
		}
		else if(!e.isCancelled()) {
			if(e.getTextBox().equals(GUIManager.chat)) {
				GUIManager.chat.addMessage(e.getMessage());
				GUIManager.chat.sendToServer(e.getMessage());
			}
			else if(e.getTextBox().equals(GUIManager.console)) {
				GUIManager.console.addMessage(e.getMessage());
			}
		}
	}
	
	public boolean checkBlacklistedWords(ChatEvent e) {
		//TODO make this
		return false;
	}
}
