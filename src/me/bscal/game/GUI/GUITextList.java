package me.bscal.game.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUIButton.Action;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.serialization.QVDatabase;
import me.bscal.serialization.QVObject;
import me.bscal.serialization.QVString;

public class GUITextList extends GUIBox {

	private List<Rectangle> content = new ArrayList<Rectangle>();
	private LinkedList<String> messages = new LinkedList<String>();
	public GUITextBox textBox;

	public Color fontColor;
	public Color colorAlt;
	public boolean isVisible = true;
	public boolean lineColorAlternation = true;

	private final int numOfMessages;
	private int yTextOffset, x, y;

	public GUITextList(int x, int y, int width, int height, int numOfMessages, int yTextOffset) {
		super(x, y, width, height);
		this.numOfMessages = numOfMessages + 1;	// For the textbox at the bottom
		this.x = x + 2;
		this.y = y + 4;
		this.yTextOffset = yTextOffset;
		init();
	}

	public void init() {
		yOffset = rect.height / numOfMessages;
		for (int i = 0; i < numOfMessages; i++) {
			if (i == numOfMessages - 1) {
				textBox = new GUITextBox(new Rectangle(x, y - 4, rect.width - 2, yOffset));
				textBox.action = Action.TEXT;
				continue;
			}
			content.add(new Rectangle(x, y - 4, rect.width - 2, yOffset));
			y += yOffset;
		}
		colorAlt = color.brighter();
		yTextOffset += yOffset/2;
	}

	public void addMessage(String s) {
		if(s.equals(" ") || s.equals("")) return;
		if(s.length() > 255) s = s.substring(0, 255);
		messages.push(s);
	}

	public void sendToServer(String s) {
		QVDatabase database = new QVDatabase("QV");
		QVObject o = new QVObject("chat");
		o.addString(QVString.create("s", s.toCharArray()));
		database.addObject(o);
		Game.getClientPlayer().send(database);
	}

	public void deserialize(QVDatabase database) {
		addMessage(database.getRootObject().getRootString().getString());
	}

	public void render(Render renderer, Graphics g, int xZoom, int yZoom) {
		if (isVisible) {
			if (hasBorder) {
				g.setColor(borderColor);
				g.fillRect(rect.x - 4, rect.y - 4, rect.width + 8, rect.height + 8);
			}
			g.setColor(color);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			g.setColor(fontColor);
			for (int i = 0; i < content.size(); i++) {
//				Shows the rectangles of text lines.
//				Rectangle r = content.get(i);
//				g.drawRect(r.x, r.y, r.width, r.height);
				if (i > messages.size() - 1) continue;
				Rectangle r = content.get(i);
				g.drawString(messages.get(i), r.x, r.y + yTextOffset);
				
			}
			//Renders the GUITextBox
			g.drawString(textBox.text, textBox.rect.x, textBox.rect.y + yTextOffset);
			g.setColor(borderColor);
			g.drawRect(textBox.rect.x, textBox.rect.y, textBox.rect.width, textBox.rect.height);
			//textBox.render(renderer, g, xZoom, yZoom);
		}
	}

	public void update(Game game) {
		textBox.update(game);
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		return textBox.handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
	}

}
