package me.bscal.game.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import me.bscal.game.Game;

public class WindowHandler implements WindowListener{

	private Game game;
	
	public WindowHandler(Game game) {
		this.game = game;
		this.game.addWindowListener(this);
	}
	
	public void windowOpened(WindowEvent e) {	
	}

	public void windowClosing(WindowEvent e) {
		game.getClientPlayer().disconnect();
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

}
