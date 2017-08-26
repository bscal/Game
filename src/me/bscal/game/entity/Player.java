package me.bscal.game.entity;

import java.awt.Font;
import java.awt.event.MouseEvent;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUIButton;
import me.bscal.game.GUI.GUIButtonComponent;
import me.bscal.game.GUI.GUIGraphicsButton;
import me.bscal.game.GUI.GUILoadingBar;
import me.bscal.game.GUI.GUIManager;
import me.bscal.game.GUI.GUIPanel;
import me.bscal.game.GUI.GUIPanel.BarType;
import me.bscal.game.GUI.GUIText;
import me.bscal.game.GUI.SDKButton;
import me.bscal.game.entity.projectile.ProjectileEntity;
import me.bscal.game.entity.projectile.Projectile;
import me.bscal.game.entity.spell.Iceblast;
import me.bscal.game.events.Event;
import me.bscal.game.events.Event.EventType;
import me.bscal.game.events.EventDispatcher;
import me.bscal.game.events.EventListener;
import me.bscal.game.events.eventTypes.MousePressedEvent;
import me.bscal.game.events.eventTypes.MouseReleasedEvent;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.listeners.KeyboardListener;
import me.bscal.game.sprites.AnimatedSprite;
import me.bscal.game.sprites.Sprite;
import me.bscal.game.sprites.SpriteHandler;
import me.bscal.game.util.Cooldown;
import me.bscal.serialization.QVDatabase;
import me.bscal.serialization.QVField;
import me.bscal.serialization.QVObject;

public class Player extends LivingEntity implements EventListener{
	
	private GUIManager gui;
	private GUILoadingBar hpBar;
	private GUILoadingBar manaBar;
	private GUILoadingBar xpBar;
	private GUIText hpText;
	private GUIText manaText;
	private GUIText xpText;
	private double xp = 0;
	private double xpNeededToLevel = 100;
	public int id;
	
	public Player() {}
	
	public Player(Game game, Sprite sprite, int animationLength) {
		this.sprite = sprite;
		this.animationLength = animationLength;
		this.layer = 1;
		this.rect = new Rectangle(0, 0, 20, 26);
		this.collisionRect = new Rectangle(0, 0, 8*Game.XZOOM, 12*Game.YZOOM);
		this.name = "bscal1";
		
		//Player UI
		Font barFont = new Font("Constantia", Font.PLAIN, 14);
		gui = Game.getGUI();
		GUIPanel panel = new GUIPanel(new Rectangle(Game.width - 256, 0, 256, Game.height), 0xff9c9c9c).setBar(BarType.SIDE);
		GUIPanel panel1 = new GUIPanel(new Rectangle(0, 0, 0, 0)).setVisibilty(false).setBar(BarType.HORIZONTAL);
		GUIText text = new GUIText(name, 16, 216);
		text.setColor(0);
		text.setFont(new Font("Constantia", Font.BOLD, 22));
		text.setParent(panel);
		hpBar = new GUILoadingBar(new Rectangle(16, 232, panel.rect.width - 32, 20));
		hpBar.setParent(panel);
		hpBar.setColors(0xff878787, 0xffff3d3d);
		hpBar.setBorder(2, 0xff404040);
		manaBar = new GUILoadingBar(new Rectangle(16, 264, panel.rect.width - 32, 20));
		manaBar.setParent(panel);
		manaBar.setColors(0xff878787, 0xff3853ff);
		manaBar.setBorder(2, 0xff404040);
		xpBar = new GUILoadingBar(new Rectangle(-384, -24, 512, 16), true);
		xpBar.setParent(panel1);
		xpBar.setColors(0xff878787, 0xfff2b200);
		xpBar.setBorder(2, 0xff404040);
		hpText = new GUIText("", hpBar.rect.x + 2, hpBar.rect.y + 14);
		hpText.setColor(0xffffffff);
		hpText.setFont(barFont);
		hpText.setParent(panel);
		manaText = new GUIText("", manaBar.rect.x + 2, manaBar.rect.y + 14);
		manaText.setColor(0xffffffff);
		manaText.setFont(barFont);
		manaText.setParent(panel);
		xpText = new GUIText("", -212, -12, true);
		xpText.setColor(0xffffffff);
		xpText.setFont(barFont);
		xpText.setParent(panel1);
		GUIGraphicsButton[] actionBar = new GUIGraphicsButton[9];
		for(int i = 0; i < actionBar.length; i++) {
			Rectangle buttonRect = new Rectangle(-356 + (i * (24 * Game.XZOOM + 3)), -80, 48, 48);
			actionBar[i] = new GUIGraphicsButton(buttonRect).setColors(0xd0858585, 0xf5d4d4d4);
			actionBar[i].setParent(panel1);
		}		
		if(Game.SDKMode) {
			GUIButton[] buttons = new GUIButton[game.getTiles().size()];
			Sprite[] tileList = game.getTiles().getSprites();
			for(int i = 0; i < buttons.length; i++) {
				Rectangle tileRect = new Rectangle(0, i*(16 * Game.XZOOM + 2), 16*Game.XZOOM, 16*Game.YZOOM);
				buttons[i] = new SDKButton(i, tileList[i], tileRect).setGameInstance(game);
			}
			GUIButtonComponent buttonGUI = new GUIButtonComponent(buttons, 5, 5, true);
			panel.add(buttonGUI);
		}
		gui.addAfterEffect(panel1);
		gui.add(panel);
		
		if(sprite != null && sprite instanceof AnimatedSprite) {
			animatedSprite = (AnimatedSprite) sprite;
		}
		updateDirection();
		xCollisionOffset = 14;
		yCollisionOffset = 28;
		
		//Player Default Stats
		speed = 8;
		health = maxHealth;
		mana = maxMana;
		game.players.add(this);
	}
	
	public Player setName(String name) {
		this.name = name;
		return this;
	}
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(EventType.MOUSE_PRESSED, (Event e) -> onMousePress((MousePressedEvent) e));
		dispatcher.dispatch(EventType.MOUSE_RELEASED, (Event e) -> onMouseRelease((MouseReleasedEvent) e));
	}
	
	public void update(Game game) {
		KeyboardListener keyboard = game.getKeyListener();
		
		collisionRect.x = rect.x;
		collisionRect.y = rect.y;
		
		isMoving = false;
		int newDirection = direction;
		
		if(keyboard.left()) {
			collisionRect.x -= speed;
			newDirection = 1;
			isMoving = true;
		}
		if(keyboard.right()) {
			collisionRect.x += speed;
			newDirection = 0;
			isMoving = true;
		}
		if(keyboard.up()) {
			collisionRect.y -= speed;
			newDirection = 2;
			isMoving = true;
		}
		if(keyboard.down()) {
			if(!keyboard.isCtrlDown()) {
				collisionRect.y += speed;
			}
			newDirection = 3;
			isMoving = true;
		}
		
		if(newDirection != direction) {
			direction = newDirection;
			updateDirection();
		}
		
		if(isMoving) {	
			collisionRect.x += xCollisionOffset;
			collisionRect.y += yCollisionOffset;
			checkCollision(game);
			animatedSprite.update(game);
		}
		else {
			animatedSprite.reset();
		}
		
		updatePlayer();
		updateCooldowns();
		updateCamera(game, game.getRenderer().getCamera());
	}
	
	private void updatePlayer() {
//		health++;
//		if(health >= maxHealth) {
//			health = 0;
//		}
		hpBar.setProgress(health/maxHealth);
		hpText.setText("Health: " + health + " / " + maxHealth);
		manaBar.setProgress(mana/maxMana);
		manaText.setText("Mana: " + mana + " / " + maxMana);
		xpBar.setProgress(xp/xpNeededToLevel);
		xpText.setText("Experience: " + xp + " / " + xpNeededToLevel);
	}
	
	public void updateCamera(Game game, Rectangle camera) {
		camera.x = rect.x + (rect.width/2*Game.XZOOM) - (camera.width / 2);
		camera.y = rect.y + (rect.height/2*Game.YZOOM) - (camera.height / 2);
	}
	
	public boolean onMousePress(MousePressedEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(e.getButton() == MouseEvent.BUTTON1 && !isOnCooldown("fireball")) {
			sendProjectileToServer(x, y, Projectile.FIREBALL);
			cooldowns.add(new Cooldown("fireball", 5));
			return true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3 && !isOnCooldown("iceblast")) {
			sendProjectileToServer(x, y, Projectile.ICEBLAST);
			cooldowns.add(new Cooldown("iceblast", 5));
			return true;
		}
		return false;
	}
	
	public boolean onMouseRelease(MouseReleasedEvent e) {
		return false;
	}
	
	private void sendProjectileToServer(int x, int y, int type) {
		QVDatabase database = new QVDatabase("Proj");
		QVObject obj = new QVObject("0");
		ProjectileEntity proj = new ProjectileEntity(rect.x, rect.y, 0)
						.setProjectileType(type);
		proj.serializeProjectile(obj, x, y);
		database.addObject(obj);
		
		Game.client.send(database);
	}
	
	public void serialize(QVObject o) {
		super.serialize(o);
		o.addField(QVField.createDouble("xp", xp));
		o.addField(QVField.createInt("id", id));
	}
	
	public void deserialize(QVObject o) {
		super.deserialize(o);
		this.xp = o.findField("xp").getDouble();
		this.id = o.findField("id").getInt();
	}

	
}
