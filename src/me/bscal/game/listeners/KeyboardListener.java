package me.bscal.game.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.bscal.game.Game;

public class KeyboardListener implements KeyListener, FocusListener{
	
	private Game game;
	public boolean[] keys = new boolean[100];
	private boolean isCtrl = false;
	private boolean spacePressed = false;
	
	public KeyboardListener(Game game) {
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key < keys.length) {
			keys[key] = true;
		}
		
		if(key == KeyEvent.VK_SPACE && spacePressed == false) {
			spacePressed = true;
		}
		
		if(keys[KeyEvent.VK_CONTROL] && keys[KeyEvent.VK_S]) {
			saveGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key < keys.length) {
			keys[key] = false;
		}
		
		if(keys[KeyEvent.VK_CONTROL]) {
			isCtrl = false;
		}
		
		if(key == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
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
	
	public boolean isCtrlDown() {
		return isCtrl;
	}

	private void saveGame() {
		game.saveGame();
	}
}
