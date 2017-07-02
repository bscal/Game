package me.bscal.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import me.bscal.game.GUI.GUI;
import me.bscal.game.GUI.GUIButton;
import me.bscal.game.GUI.SDKButton;
import me.bscal.game.GameState.State;
import me.bscal.game.entity.GameObject;
import me.bscal.game.entity.Player;
import me.bscal.game.listeners.KeyboardListener;
import me.bscal.game.listeners.MouseClickListener;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.SpriteSheet;

public class Game extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7240204747443743168L;
	public final int WIDTH = 1200;
	public final int HEIGHT = 900;
	private final int XZOOM = 3;
	private final int YZOOM = 3;
	public static int alpha = 0xFFCCFF00; //0xFF defines Hex color codes
	private final String path = "resources/";
	
	private Canvas canvas = new Canvas();
	private Render renderer;
	private SpriteSheet sheet;
	private SpriteSheet playerSheet;
	private SpriteSheet fireball;
	private SpriteSheet explosion;
	private Tiles tiles;
	private Map map;
	private KeyboardListener listener;
	private MouseClickListener mouseListener;
	
	//private GameState state;
	
	private ArrayList<GameObject> entities = new ArrayList<GameObject>();
	private ArrayList<GameObject> entitiesToRemove = new ArrayList<GameObject>();
	private ArrayList<GameObject> entitiesToAdd = new ArrayList<GameObject>();
	private Player player;
	private int selectedTileID = 3;
	
	public Game() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		this.add(canvas);
		this.pack();
		this.setVisible(true);
		init();
	}
	
	private void init() {
		float start = System.nanoTime();
		
		canvas.createBufferStrategy(3);
		renderer = new Render(getWidth(), getHeight());
		//Loads GameState
		//state = new GameState(State.GAME);	//TODO Set this to main menu/make main menu
		//Loads SpriteSheet
		BufferedImage sheetImg = loadImage(path + "Tiles1.png");
		sheet = new SpriteSheet(sheetImg);
		sheet.init(16, 16);
		//AnimatedSprites Sheet
		BufferedImage playerImg = loadImage(path + "Player.png");
		playerSheet = new SpriteSheet(playerImg);
		playerSheet.init(20, 26);
		//Fireball Sheet
		BufferedImage fireballImg = loadImageAlpha(path + "Fireball.png");
		fireball = new SpriteSheet(fireballImg);
		fireball.init(16, 16);
		//Explosion Sheet
		BufferedImage explosionImg = loadImage(path + "Explosion.png");
		explosion = new SpriteSheet(explosionImg);
		explosion.init(16, 16);
		//Tiles/Map
		tiles = new Tiles(new File("Tiles.txt"), sheet);
		map = new Map(new File("Map.txt"), tiles);
		//AnimatedSprites Player
		AnimatedSprite animatedPlayer = new AnimatedSprite(playerSheet, 3);
		//Load GUI and SDKGUI
		/*
		GUIButton[] buttons = new GUIButton[tiles.size()];
		Sprite[] tileList = tiles.getSprites();
		
		
		for(int i = 0; i < buttons.length; i++) {
			Rectangle tileRect = new Rectangle(0, i*(16 * XZOOM + 2), 16*XZOOM, 16*YZOOM);
			buttons[i] = new SDKButton(this, i, tileList[i], tileRect);
		}
		
		GUI gui = new GUI(buttons, 5, 5, true);
		*/
		
		//Initialize entities
		//entities = new GameObject[2];
		//Players sprite *
		player = new Player(animatedPlayer, 8);
		entities.add(player);
		//entities.add(gui);
		//Initialize listeners and adds to canvas
		mouseListener = new MouseClickListener(this);
		listener = new KeyboardListener(this);
		canvas.addKeyListener(listener);
		canvas.addFocusListener(listener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
		
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentResized(ComponentEvent e) {
				getRenderer().getCamera().width = canvas.getWidth();
				getRenderer().getCamera().height = canvas.getHeight();
			}

			@Override
			public void componentShown(ComponentEvent e) {}
		});
		
		float finish = System.nanoTime();
		System.out.println("Startup took: " + ((finish - start) / 1000000) + " ms | " + ((finish - start) / 1000000000) + " seconds");
	}
	
	public void update() {
		//for(int i = 0; i < entities.length; i++) {
		//	entities[i].update(this);
		//}
		for(GameObject entity : entities) {
			entity.update(this);
		}
		updateEntities();
	}
	
	public BufferedImage loadImage(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formated = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formated.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formated;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage loadImageAlpha(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formated = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			formated.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formated;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		
		map.render(renderer, XZOOM, YZOOM);
		//for(int i = 0; i < entities.length; i++) {
		//	entities[i].render(renderer, XZOOM, YZOOM);
		//}
		for(GameObject entity : entities) {
			entity.render(renderer, XZOOM, YZOOM);
		}
		
		renderer.render(graphics);
		
		graphics.dispose();
		bufferStrategy.show();
		renderer.clear();
	}

	public void run() {
		@SuppressWarnings("unused")
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		//int i = 0;
		//int x = 0;
	
		long lastTime = System.nanoTime(); //long 2^63
		final double fr = 30;
		double ns = 1000000000 / fr;
		double delta = 0;
		
		int fps = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
	
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			fps++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("Fps: " + fps + " Ticks: " + updates);
				fps = 0;
				updates = 0;
			}
		}
	}
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
	
	public KeyboardListener getKeyListener() {
		return listener;
	}
	
	public MouseClickListener getMouseListener() {
		return mouseListener;
	}
	
	public Render getRenderer() {
		return renderer;
	}
	
	public int getXZoom() {
		return XZOOM;
	}
	
	public int getYZoom() {
		return XZOOM;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void saveGame() {
		map.saveMap();
	}
	
	//public GameObject[] getEntities() {
	//	return entities;
	//}
	
	public ArrayList<GameObject> getEntities() {
		return entities;
	}
	
	private void updateEntities() {
		if(!entitiesToRemove.isEmpty()) {
			Iterator<GameObject> i = entities.iterator();
			while (i.hasNext()) {
				GameObject o = i.next();
				if(entitiesToRemove.contains(o)) {
				   i.remove();
				}
			}
			entitiesToRemove.clear();
		}
		if(!entitiesToAdd.isEmpty()) {
			entities.addAll(entitiesToAdd);
			entitiesToAdd.clear();
		}
	}
	
	public ArrayList<GameObject> getRemovedEntities() {
		return entitiesToRemove;
	}
	
	public ArrayList<GameObject> getAddedEntities() {
		return entitiesToAdd;
	}


	public void setSelectedTile(int id) {
		selectedTileID = id;
	}
	
	public int getSelectedTileID() {
		return selectedTileID;
	}
	
	public SpriteSheet getFireball() {
		return fireball;
	}
	
	public SpriteSheet getExplosion() {
		return explosion;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}