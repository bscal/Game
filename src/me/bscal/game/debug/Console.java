package me.bscal.game.debug;

import java.awt.Color;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUITextList;

public class Console extends GUITextList{

	public enum Type {
		INFO, WARNING, ERROR
	}
	
	private static final Color ERROR = Color.RED;
	private static final Color WARNING = Color.ORANGE;
	private static final Color INFO = Color.WHITE;
	private static final String ERROR_PRE = "[Error] ";
	
	private static Console console;
	
	public Console() {
		super(Game.width/2, 24, 512, 640, 32, 4);
		super.hasBorder = true;
		Color c = Color.gray;
		color = c;
		borderColor = color.darker();
		console = this;
	}
	
//	public static Console getConsole() {
//		if(console == null) {
//			console = new Console();
//		}
//		return console;
//	}
	
	public static void printConsole(String message) {
		printConsole(Type.INFO, message);
	}
	
	public static void printConsole(Type type, String message) {
		switch (type) {
		
		case ERROR:
			console.fontColor = ERROR;
			message = ERROR_PRE + message;
			break;
		case INFO:
			console.fontColor = INFO;
			break;
		case WARNING:
			console.fontColor = WARNING;
			break;
		default:
			assert(false);
			break;
		}
		
		console.addMessage(message);
	}

}
