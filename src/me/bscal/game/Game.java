package me.bscal.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import me.bscal.game.GUI.GUIManager;
import me.bscal.game.chat.ChatCommandHandler;
import me.bscal.game.chat.commands.Broadcast;
import me.bscal.game.debug.Console;
import me.bscal.game.entity.GameObject;
import me.bscal.game.entity.Player;
import me.bscal.game.entity.mob.Skeleton;
import me.bscal.game.events.Event;
import me.bscal.game.events.Event.EventType;
import me.bscal.game.events.EventListener;
import me.bscal.game.events.eventTypes.ChatEvent;
import me.bscal.game.graphics.Render;
import me.bscal.game.listeners.KeyboardListener;
import me.bscal.game.listeners.MouseClickListener;
import me.bscal.game.listeners.WindowHandler;
import me.bscal.game.mapping.Map;
import me.bscal.game.mapping.Tiles;
import me.bscal.game.net.Client;
import me.bscal.game.net.player.NetPlayer;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.serialization.QVDatabase;
import me.bscal.serialization.QVObject;

public class Game extends JFrame implements Runnable, EventListener {
	private static final long serialVersionUID = 1L;
	
	public static int width = 1200;
	public static int height = width / 16 * 9;
	public static final int XZOOM = 2;
	public static final int YZOOM = 2;
	public static final int ALPHA = 0xFFCCFF00; //0xFF defines Hex color codes
	public static final String PATH = "resources/";
	public static final String VERSION = "1.3";
	
	private Thread thread;
	private boolean isRunning = false;
	private Canvas canvas = new Canvas();
	private Render renderer;
	private Tiles tiles;
	private Map map;
	private KeyboardListener listener;
	private MouseClickListener mouseListener;
	private ChatCommandHandler chatHandler;
	
	private static Player player;
	private static Client client;
	private static GUIManager GUIManager;
	
	private static List<GameObject> entities		 = new ArrayList<GameObject>();
	private static List<GameObject> entitiesToRemove = new ArrayList<GameObject>();
	private static List<GameObject> entitiesToAdd	 = new ArrayList<GameObject>();
	private List<Integer> fpsList 					 = new ArrayList<Integer>();
	public List<Player> players 					 = new ArrayList<Player>();
	
	private int selectedTileID = 3;
	private int selectedLayer = 0;
	public static boolean SDKMode = false;
	
	public Game() {
		this.setTitle("Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, width, height);
		this.setLocationRelativeTo(null);
		canvas.setBounds(0, 0, width, height);
		this.add(canvas);
		this.pack();
		this.setVisible(true);
		canvas.createBufferStrategy(2);
		renderer = new Render(canvas.getWidth(), canvas.getHeight());
		init();
	}
	
	private void init() {
		float start = System.nanoTime();
		String workingDir = System.getProperty("user.dir");
		System.out.println("Current working directory : " + workingDir);
		
		//Loads SpriteSheet
		new SpriteHandler();
		GUIManager = new GUIManager();
		mouseListener = new MouseClickListener(this);
		listener = new KeyboardListener(this);
		new WindowHandler(this);
		chatHandler = new ChatCommandHandler();
		
		//Tiles/Map
		File tilesFile = new File(Game.class.getResource("resources/Tiles.txt").getPath());
		File mapFile = new File(Game.class.getResource("resources/Map.txt").getPath());
		tiles = new Tiles(tilesFile, SpriteHandler.tileSheet);
		map = new Map(mapFile, tiles);
		//Initialize entities
		AnimatedSprite animatedPlayer = new AnimatedSprite(SpriteHandler.playerSheet, 3);
		player = new Player(this, animatedPlayer, 8);
		for(int i = 0; i < 1; i++) {
			//entitiesToAdd.add(new Zombie(new AnimatedSprite(SpriteHandler.playerSheet, 3), 8));
			//entitiesToAdd.add(new Archer());
			//entitiesToAdd.add(new Dummy(new AnimatedSprite(SpriteHandler.playerSheet, 3), 8));
		}
		
		//Server connection
		client = new Client("localhost", 8192, this);
		client.connect();
		
		entities.add(player);
		//Initialize listeners
		canvas.addKeyListener(listener);
		canvas.addFocusListener(listener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
		addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent e) {}

			public void componentMoved(ComponentEvent e) {}

			public void componentResized(ComponentEvent e) {
				getRenderer().getCamera().width = canvas.getWidth() - renderer.CAMERA_GUI_OFFSET;
				getRenderer().getCamera().height = canvas.getHeight();
				width = canvas.getWidth();
				height = canvas.getHeight();
			}

			public void componentShown(ComponentEvent e) {}
		});
		canvas.requestFocus();
		
		chatHandler.registerCommand(new Broadcast());
		Skeleton s = new Skeleton(80, 80);
		s.init();
		float finish = System.nanoTime();
		System.out.println("Startup took: " + ((finish - start) / 1000000) + " ms | " + ((finish - start) / 1000000000) + " seconds");
	}
	
	public void update() {
		for(int i = 0; i < tiles.tileList.size(); i++) {
			tiles.tileList.get(i).update();
		}
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
		}
		GUIManager.update(this);
		updateEntities();
	}
	
	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics g = bufferStrategy.getDrawGraphics();
		
		map.render(renderer, getEntities(), XZOOM, YZOOM);
		GUIManager.render(renderer, g, XZOOM, YZOOM);
		renderer.render(g);
		GUIManager.renderAfterEffect(renderer, g, XZOOM, YZOOM);
		//map.renderToMinimap(renderer, g, entities, XZOOM, YZOOM);
//		g.drawLine(renderer.getCamera().width/2, 0, renderer.getCamera().width/2, height);
//		g.drawLine(0, height/2, width, height/2);
		g.dispose();
		bufferStrategy.show();
		renderer.clear();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double delta = 0;
		int fps = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		final long OPTIMAL_TIME = 1000000000 / 30;
	
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / (double)OPTIMAL_TIME;
			lastTime = now;
			if(delta >= 1) {
				update();
				serializeAndSendToServer();
				updates++;
				delta--;
			}
			render();
			fps++;
			if(System.currentTimeMillis() - timer > 1000L) {
				timer += 1000L;
				Runtime runtime = Runtime.getRuntime();
				long memory = runtime.totalMemory() - runtime.freeMemory();
			    System.out.println("Used memory in megabytes: " + memory/1048576);
				System.out.println("Fps: " + fps + " | Ticks: " + updates + " | Entites: " + entities.size());
				fpsList.add(fps);
				fps = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	public void calculatePreformance() {
		Collections.sort(fpsList);
		Collections.reverse(fpsList);
		int mean = 0;
		int total = 0;
		for(int i = 0; i < fpsList.size(); i++) {
			total += fpsList.get(i);
			mean++;
		}
		System.out.println("Max FPS: " + fpsList.get(0));
		System.out.println("Min FPS: " + fpsList.get(fpsList.size() - 1));
		System.out.println("Mean FPS: " + total/mean);
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		game.start();
	}
	
	public KeyboardListener getKeyListener() {
		return listener;
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
		System.out.println("Game Saved!");
		map.saveMap();
	}
	
	public static List<GameObject> getEntities() {
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
	
	public static List<GameObject> getRemovedEntities() {
		return entitiesToRemove;
	}
	
	public static List<GameObject> getAddedEntities() {
		return entitiesToAdd;
	}


	public void setSelectedTile(int id) {
		selectedTileID = id;
	}
	
	public int getSelectedTileID() {
		return selectedTileID;
	}
	
	public int getSelectedLayer() {
		return selectedLayer;
	}
	
	public static GUIManager getGUI() {
		return GUIManager;
	}
	
	public Tiles getTiles() {
		return tiles;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns a player at a giving id. -1 will return client player.
	 * @param id of the player
	 * @return
	 */
	public Player getPlayer(int id) {
		if(id == -1) {
			return player;
		}
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getID() == id) {
				return players.get(i);
			}
		}
		return null;
	}
	
	public Player getPlayer(String name) {
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).getName().equals(name)) {
				return players.get(i);
			}
		}
		return null;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void onEvent(Event event) {
		if(event.getEventType() == EventType.CHAT_EVENT) {
			ChatEvent e = (ChatEvent) event;
			chatHandler.dispatch(e);
		}
		else {
			player.onEvent(event);
		}
	}
	
	public static Client getClientPlayer() {
		return client;
	}
	
	public void setPlayerID(int id) {
		player.id = id;
		System.out.println(player.id);
	}
	
	public void serializeAndSendToServer() {
		QVDatabase database = new QVDatabase("Game");
		QVObject obj = new QVObject("State");
		database.addObject(obj);
		QVObject o = new QVObject("Player");
		player.serialize(o);
		database.addObject(o);
		client.send(database);
	}
	
	public void deserializeFromServer(QVDatabase database) {
		if(database.getName().equals("Game")) {
			QVObject obj = database.findObject("Player");
			Player p;
			if(players.contains(getPlayer(obj.findField("id").getInt()))) {
				p = getPlayer(obj.findField("id").getInt());
			}
			else {
				AnimatedSprite animatedPlayer = new AnimatedSprite(SpriteHandler.playerSheet, 3);
				p = new NetPlayer(this, animatedPlayer, 8);
			}
			p.deserialize(obj);
		}
	}
}