package me.bscal.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import me.bscal.game.Game;
import me.bscal.game.entity.Player;
import me.bscal.game.entity.projectile.ProjectileEntity;
import me.bscal.serialization.QVDatabase;

public class Client {

	public final static int UNKNOWN_HOST = 1;
	public final static int SOCKET_EXCEPTION = 2;
	private int errorCode;
	private Game game;
	private boolean listening = false;
	private Thread listenThread;

	private String ipAddress;
	private int port;

	private InetAddress serverAddress;

	private DatagramSocket socket;
	private byte[] data = new byte[1024];

	/**
	 * @param host
	 *            Formatted 127.0.0.1:9182
	 */
	public Client(String host, Game game) {
		String[] hostSplit = host.split(":");
		if (hostSplit.length > 2) {
			errorCode = UNKNOWN_HOST;
			return;
		}
		ipAddress = hostSplit[0];
		try {
			port = Integer.getInteger(hostSplit[1]);
		} catch (NumberFormatException e) {
			errorCode = UNKNOWN_HOST;
			return;
		}
		this.game = game;
	}

	/**
	 * @param host
	 *            Formatted 127.0.0.1
	 * @param port
	 *            Formatted 9182
	 */
	public Client(String host, int port, Game game) {
		this.ipAddress = host;
		this.port = port;
		this.game = game;
	}

	public int connect() {
		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			errorCode = UNKNOWN_HOST;
			return UNKNOWN_HOST;
		}
		start();
		sendConnectionPacket();
		return 0;
	}

	private int start() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			errorCode = SOCKET_EXCEPTION;
			return SOCKET_EXCEPTION;
		}
		listening = true;
		listenThread = new Thread(() -> listen(), "GameClient-ListenThread");
		listenThread.start();
		System.out.println("Client is listening...");
		return 0;
	}

	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(data, 1024);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			process(packet);
		}
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();

		if (new String(data, 0, 3).equals("VDB")) {
			assert (data[3] == QVDatabase.VERSION);
			QVDatabase database = QVDatabase.deserialize(data);
			String name = database.getName();
			if (name.equals("Game")) {
				processDatabase(database);
			} else if (name.equals("ID")) {
				game.setPlayerID(database.getRootObject().getRootField().getInt());
			} else if (name.equals("Proj")) {
				assert (database.findObject("0") != null);
				ProjectileEntity.deserializeProjectile(database.getRootObject());
			} else if (name.equals("Logout")) {
				Player p = game.getPlayer(database.getRootObject().getRootField().getInt());
				//game.getPlayers().remove(p);
				Game.getRemovedEntities().add(p);
			}
		}
	}

	private void processDatabase(QVDatabase database) {
		game.deserializeFromServer(database);
	}

	public int disconnect() {
		assert (!socket.isClosed());
		byte[] data = new byte[] { 91, 1 };
		send(data);
		return 0;
	}

	private void sendConnectionPacket() {
		assert (!socket.isClosed());
		byte[] data = new byte[] { 91, 0 };
		send(data);
	}

	// public void sendLoginPacket(String username, String password, boolean
	// rememeber) {
	// //TODO Salt and hash password send username and hashed password to server
	// compare.
	// byte[] data = new byte[username.length()];
	// }

	public void send(byte[] data) {
		assert (!socket.isClosed());
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(QVDatabase database) {
		assert (!socket.isClosed());
		byte[] data = new byte[database.size];
		database.getBytes(data, 0);
		send(data);
	}

	public int getErrorCode() {
		return errorCode;
	}

}
