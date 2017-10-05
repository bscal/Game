package me.bscal.game.GUI;

import java.awt.Graphics;

import me.bscal.game.Game;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;

public class GUITextBox extends GUIGraphicsButton {
	
	public String text = "";
	public boolean isTyping = false;

	public GUITextBox(Rectangle rect) {
		super(rect);
	}

	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		g.setColor(color);
		g.drawString(text, rect.x, rect.y + rect.height/2 + 6);
		if (image != null) {
			g.drawImage(image, rect.x, rect.y, null);
		}
	}

	public void update(Game game) {
	}

	public void appendText(char key) {
		text += key;
	}
	
	public void deleteCurrentChar() {
		if(text.length() > 0) {
			text = text.substring(0, text.length() - 1);
		}
	}

	public void clearText() {
		text = "";
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle Camera, int xZoom, int yZoom) {
		if(mouseRectangle.intersects(rect)) {
			clicked();
			clicked = true;
			return clicked;
		}
		isTyping = false;
		clicked = false;
		return clicked;
	}
	
	public void clicked() {
		if (action != null) {
			if (action == Action.EXIT) {
				GUIManager.delete(parent);
			} else if (action == Action.TEXT) {
				isTyping = true;
			}
		}
	}

	public void hovered() {
	}

	public void released() {
	}

	public void exited() {
	}

}
