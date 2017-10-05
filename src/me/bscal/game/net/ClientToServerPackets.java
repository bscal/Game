package me.bscal.game.net;

import me.bscal.game.Game;
import me.bscal.serialization.QVDatabase;

/**
 * Class that holds QVDatabase's and sends them to the server through the client
 * Most methods in this class are based around asking the server to do things
 * and/or asking for additional info (Commands / Time on server).
 * 
 * @author Jon
 *
 */
public class ClientToServerPackets {

	private ClientToServerPackets() {
	}

	/**
	 * Sends a QVDatabase to the server to sync the entity spawn with all other
	 * ClientPlayers.
	 * 
	 * @param database Database to send
	 */
	public static void serverSpawnEntity(QVDatabase database) {
		Game.getClientPlayer().send(database);
	}

	/**
	 * Takes in <code>EntityType</code> and Amount of entity and creates a
	 * QVDatabase to send to the server.
	 * 
	 * @param entityType
	 *            The type of Entity to send. Use <code>EntityType</code> for
	 *            type list.
	 * @param amount
	 *            Amount of specified type.
	 */
	public static void serverSpawnEntity(int entityType, int amount) {

	}

}
