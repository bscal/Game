package me.bscal.game.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUIManager;
import me.bscal.game.GUI.GUITextList;
import me.bscal.game.debug.Console;
import me.bscal.game.events.eventTypes.ChatEvent;

public class KeyboardListener implements KeyListener, FocusListener{
	
	private Game game;
	public boolean[] keys = new boolean[100];
	private GUITextList chat;
	private me.bscal.game.events.EventListener listener;
	
	public KeyboardListener(Game game) {
		this.game = game;
		this.listener = game;
		chat = GUIManager.chat;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(chat.textBox.isTyping) {
			assert(chat.textBox.text != null);
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				ChatEvent event = new ChatEvent(chat, chat.textBox.text, Game.getPlayer());
				listener.onEvent(event);
//				chat.addMessage(chat.textBox.text);
//				chat.sendToServer(chat.textBox.text);
				chat.textBox.clearText();
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				chat.textBox.deleteCurrentChar();
				return;
			}
			if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED) return;
			chat.textBox.appendText(e.getKeyChar());
			return;
		}
		
		if(key < keys.length) {
			keys[key] = true;
		}
		
		if(keys[KeyEvent.VK_CONTROL] && keys[KeyEvent.VK_S]) {
			saveGame();
		}
		
		else if(keys[KeyEvent.VK_Z]) {
			GUIManager.chat.parent.isVisible = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key < keys.length) {
			keys[key] = false;
		}
		
	}

	public void keyTyped(KeyEvent e) {
	}

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
		for(int i = 0; i < keys.length ; i++) {
			keys[i] = false;
		}
	}
	
	public boolean up()
	{
		return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
	}

	public boolean down()
	{
		return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
	}

	public boolean left()
	{
		return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	}

	public boolean right()
	{
		return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	}

	private void saveGame() {
		game.saveGame();
	}
}
