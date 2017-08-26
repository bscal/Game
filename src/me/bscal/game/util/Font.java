package me.bscal.game.util;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import me.bscal.game.graphics.Render;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.game.sprites.SpriteSheet;

public class Font {

	private static SpriteSheet sheet;
	private static Sprite[] characters;

	//Index goes from 0-99
	private static String charIndex = " !Â#$%&Â()*+,-Â/" + //
			"0123456789;:<=>?" + //
			"@ABCDEFGHIJKLMNO" + //
			"PQRSTUVWXYZ[\\]^_" + //
			"`abcdefghijklmno" + //
			"pqrstuvwxyz{|}.~" + //
			"\'«\"»";
	

	public Font() {
		sheet = new SpriteSheet(SpriteHandler.loadImage("resources/fonts/Font.png"));
		sheet.init(16, 16);
		characters = sheet.getLoadedSprites();
	}
	
	public Font(File file) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("CONSTAN.TTF")));
			java.awt.Font f = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("CONSTAN.TTF"));
			f.deriveFont(java.awt.Font.BOLD, 16);
		} catch (FontFormatException | IOException e) {
			System.out.println("Error loading Font file " + file);
			e.printStackTrace();
		}
		
	}

	public void render(String text, int x, int y, Render renderer, int xZoom, int yZoom) {
		render(text, x, y, 0, 0xfffffff, renderer, xZoom, yZoom);
	}
	
	public void render(String text, int x, int y, int color, Render renderer, int xZoom, int yZoom) {
		render(text, x, y, 0, color, renderer, xZoom, yZoom);
	}
	
	public void render(String text, int x, int y, int spacing, int color, Render renderer, int xZoom, int yZoom) {
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++) {
			xOffset = i * 12;
			xOffset += spacing;
			int yOffset = 0; 
			char c = text.charAt(i);
			int index = charIndex.indexOf(c);
			
			if(c == 'l' || c == 'i' || c == '.') xOffset += -3;				//Decreases spacing.
			if(c == 'w') xOffset += 1;										//Increases Spacing.
			if(c == 'y' || c == 'p' || c == 'q') yOffset += 1;				//Lowers y
			if(c == '\n') {
				xOffset = 0;
				line++;
				continue;
			}
			
			//System.out.println(c + " | " + index);
			renderer.renderString(characters[index], x + xOffset, y + line * 20 + yOffset, 1, 1, color, true);
		}
	}
	
}
