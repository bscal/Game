package me.bscal.game.mapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import me.bscal.game.Game;
import me.bscal.game.GUI.GUIComponent;
import me.bscal.game.GUI.GUIPanel;
import me.bscal.game.entity.Entity;
import me.bscal.game.entity.GameObject;
import me.bscal.game.entity.Player;
import me.bscal.game.entity.particle.Particle;
import me.bscal.game.entity.projectile.Projectile;
import me.bscal.game.graphics.Rectangle;
import me.bscal.game.graphics.Render;
import me.bscal.game.util.Vector2i;

public class Map {

	private Scanner scanner;
	private Tiles tileSet;
	private int fillID = -1;

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return 1;
			if (n1.fCost > n0.fCost)
				return -1;
			return 0;
		}
	};

	private List<MappedTile> mappedTiles = new ArrayList<MappedTile>();
	private GridBlock[][] grids;
	private int startX, startY;
	private int gridWidth = 6;
	private int gridHeight = 6;
	private int blockPixelWidth = gridWidth << 4;
	private int blockPixelHeight = gridHeight << 4;
	private int numLayers;
	private int xIncrement = 16 * Game.XZOOM;
	private int yIncrement = 16 * Game.YZOOM;

	private File mapFile;

	public Map(File mapFile, Tiles tileSet) {
		this.tileSet = tileSet;
		this.mapFile = mapFile;
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;

		try {
			scanner = new Scanner(mapFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("//")) {
					// Finds filler tile from file
					if (line.contains(":")) {
						String[] split = line.split(":");
						if (split[0].equalsIgnoreCase("FILL")) {
							fillID = Integer.parseInt(split[1]);
							continue;
						}
					}
					// Maps the tile from files
					String[] split = line.split(",");
					if (split.length >= 4) {
						MappedTile mappedTile = new MappedTile(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
								Integer.parseInt(split[2]), Integer.parseInt(split[3]));

						if (mappedTile.x < minX) {
							minX = mappedTile.x;
						}
						if (mappedTile.x > maxX) {
							maxX = mappedTile.x;
						}
						if (mappedTile.y < minY) {
							minY = mappedTile.y;
						}
						if (mappedTile.y > maxY) {
							maxY = mappedTile.y;
						}

						// if(numLayers <= mappedTile.layer) {
						// numLayers = mappedTile.layer + 1;
						// }
						numLayers = 5;

						mappedTiles.add(mappedTile);
					}
				}
			}
			if (mappedTiles.size() == 0) {
				minX = -gridWidth * 6;
				minY = -gridHeight * 6;
				maxX = gridWidth * 6;
				maxY = gridHeight * 6;
			}

			startX = minX;
			startY = minY;
			int blockWidth = maxX + gridWidth - minX;
			int blockHeight = maxY + gridHeight - minY;
			grids = new GridBlock[blockWidth][blockHeight];

			for (int i = 0; i < mappedTiles.size(); i++) {
				MappedTile mappedTile = mappedTiles.get(i);
				int gridX = (mappedTile.x - minX) / gridWidth;
				int gridY = (mappedTile.y - minY) / gridWidth;
				assert (gridX >= 0 && gridX < grids.length && gridY >= 0 && gridY < grids[0].length);
				if (grids[gridX][gridY] == null) {
					grids[gridX][gridY] = new GridBlock();
				}
				grids[gridX][gridY].addTile(mappedTile);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	public void saveMap() {
		if (mapFile.exists()) {
			mapFile.delete();
		}
		try {
			mapFile.createNewFile();
			PrintWriter printer = new PrintWriter(mapFile);

			printer.println("//Two slashes mark a comment!");
			printer.println("//Fill:ID - the id of the tile to fill that whole screen");
			printer.println("//TileID,X,Y - id of tile, x position, y position");

			if (fillID >= 0) {
				printer.println("Fill:" + fillID);
			}
			for (int i = 0; i < mappedTiles.size(); i++) {
				MappedTile tile = mappedTiles.get(i);
				printer.println(tile.layer + "," + tile.id + "," + tile.x + "," + tile.y);
			}
			printer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkCollision(Rectangle rect, int layer, int xZoom, int yZoom) {
		int tileWidth = xZoom << 4;
		int tileHeight = yZoom << 4;

		int topLeftX = (rect.x - 64) / tileWidth;
		int topLeftY = (rect.y - 64) / tileHeight;
		int bottomRightX = (rect.x + rect.width + 64) / tileWidth;
		int bottomRightY = (rect.y + rect.height + 64) / tileHeight;

		for (int x = topLeftX; x < bottomRightX; x++) {
			for (int y = topLeftY; y < bottomRightY; y++) {
				MappedTile tile = getTile(layer, x, y);
				if (tile != null) {
					int type = tileSet.getCollisionType(tile.id); // Collision
																	// Type
					// Collision Types goes by 0 = All sides, 1 = Top, 2 = left,
					// 3 = Bottom, 4 = Right, 5 = LowerHalf
					// 6 = All sides but flat (Projectiles/Flying can pass over)

					if (type == 0) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth, tile.y * tileHeight, tileWidth,
								tileHeight);
						if (tileRect.intersects(rect)) {
							return true;
						}
					} else if (type == 1) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth, tile.y * tileHeight + 1, tileWidth, 16);
						if (tileRect.intersects(rect)) {
							return true;
						}
					} else if (type == 2) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth - 1, tile.y * tileHeight, 16, tileHeight);
						if (tileRect.intersects(rect)) {
							return true;
						}
					} else if (type == 3) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth, tile.y * tileHeight + tileHeight,
								tileWidth, 16);
						Rectangle newRect = new Rectangle(rect.x, rect.y + rect.height, rect.width, 2);
						if (tileRect.intersects(newRect)) {
							return true;
						}
					} else if (type == 4) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth + tileWidth - 16, tile.y * tileHeight, 16,
								tileHeight);
						if (tileRect.intersects(rect)) {
							return true;
						}
					} else if (type == 5) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth, tile.y * tileHeight + tileHeight - 16,
								tileWidth, 42);
						Rectangle newRect = new Rectangle(rect.x, rect.y + rect.height, rect.width, 1);
						if (tileRect.intersects(newRect)) {
							return true;
						}
					} else if (type == 6) {
						Rectangle tileRect = new Rectangle(tile.x * tileWidth, tile.y * tileHeight, tileWidth,
								tileHeight);
						if (tileRect.intersects(rect)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void setTile(int layer, int tileX, int tileY, int id) {
		if (layer >= numLayers)
			numLayers = layer + 1;

		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if (mappedTile.x == tileX && mappedTile.y == tileY) {
				mappedTile.id = id;
				return;
			}
		}

		MappedTile mappedTile = new MappedTile(layer, id, tileX, tileY);
		mappedTiles.add(mappedTile);

		// Add to blocks
		int blockX = (tileX - startX) / gridWidth;
		int blockY = (tileY - startY) / gridHeight;
		if (blockX >= 0 && blockY >= 0 && blockX < grids.length && blockY < grids[0].length) {
			if (grids[blockX][blockY] == null)
				grids[blockX][blockY] = new GridBlock();

			grids[blockX][blockY].addTile(mappedTile);
		} else {
			int newMinX = startX;
			int newMinY = startY;
			int newLengthX = grids.length;
			int newLengthY = grids[0].length;

			if (blockX < 0) {
				int increaseAmount = blockX * -1;
				newMinX = startX - gridWidth * increaseAmount;
				newLengthX = newLengthX + increaseAmount;
			} else if (blockX >= grids.length)
				newLengthX = grids.length + blockX;

			if (blockY < 0) {
				int increaseAmount = blockY * -1;
				newMinY = startY - gridHeight * increaseAmount;
				newLengthY = newLengthY + increaseAmount;
			} else if (blockY >= grids[0].length)
				newLengthY = grids[0].length + blockY;

			GridBlock[][] newBlocks = new GridBlock[newLengthX][newLengthY];

			for (int x = 0; x < grids.length; x++)
				for (int y = 0; y < grids[0].length; y++)
					if (grids[x][y] != null) {
						newBlocks[x + (startX - newMinX) / gridWidth][y
								+ (startY - newMinY) / gridHeight] = grids[x][y];
					}

			grids = newBlocks;
			startX = newMinX;
			startY = newMinY;
			blockX = (tileX - startX) / gridWidth;
			blockY = (tileY - startY) / gridHeight;
			if (grids[blockX][blockY] == null)
				grids[blockX][blockY] = new GridBlock();
			grids[blockX][blockY].addTile(mappedTile);
		}
	}

	public MappedTile getTile(int layer, int tileX, int tileY) {
		int blockX = (tileX - startX) / gridWidth;
		int blockY = (tileY - startY) / gridHeight;

		if (blockX < 0 || blockX >= grids.length || blockY < 0 || blockY >= grids[0].length) {
			return null;
		}

		GridBlock gridBlock = grids[blockX][blockY];
		if (gridBlock == null) {
			return null;
		}
		return gridBlock.getTile(layer, tileX, tileY);
	}

	public void removeTile(int layer, int tileX, int tileY) {
		for (int i = 0; i < mappedTiles.size(); i++) {
			MappedTile mappedTile = mappedTiles.get(i);
			if (mappedTile.layer == layer && mappedTile.x == tileX && mappedTile.y == tileY) {
				mappedTiles.remove(i);

				int blockX = (tileX - startX) / gridWidth;
				int blockY = (tileY - startY) / gridHeight;
				assert (blockX >= 0 && blockX < grids.length && blockY >= 0 && blockY < grids[0].length);
				grids[blockX][blockY].removeTile(mappedTile);

			}
		}
	}

	public void render(Render renderer, List<GameObject> entities, int xZoom, int yZoom) {
		if (fillID >= 0) {
			Rectangle camera = renderer.getCamera();
			for (int y = camera.y - yIncrement - (camera.y % yIncrement); y < camera.y
					+ camera.height; y += yIncrement) {
				for (int x = camera.x - xIncrement - (camera.x % xIncrement); x < camera.x
						+ camera.width; x += xIncrement) {
					tileSet.renderTile(fillID, renderer, x, y, xZoom, yZoom);
				}
			}
		}

		for (int layer = 0; layer < numLayers; layer++) {
			int topLeftX = renderer.getCamera().x;
			int topLeftY = renderer.getCamera().y;
			int bottomRightX = renderer.getCamera().x + renderer.getCamera().width;
			int bottomRightY = renderer.getCamera().y + renderer.getCamera().height;

			int leftBlockX = (topLeftX / xIncrement - startX - 16) / gridWidth;
			int blockX = leftBlockX;
			int blockY = (topLeftY / yIncrement - startY - 16) / gridHeight;
			int pixelX = topLeftX;
			int pixelY = topLeftY;

			while (pixelX < bottomRightX && pixelY < bottomRightY) {
				if (blockX >= 0 && blockY >= 0 && blockX < grids.length && blockY < grids[0].length) {
					if (grids[blockX][blockY] != null) {
						grids[blockX][blockY].render(renderer, layer, xIncrement, yIncrement, xZoom, yZoom);
					}
				}

				blockX++;
				pixelX += blockPixelWidth;

				if (pixelX > bottomRightX) {
					pixelX = topLeftX;
					blockX = leftBlockX;
					blockY++;
					pixelY += blockPixelHeight;
					if (pixelY > bottomRightY)
						break;
				}
			}

			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i).getLayer() == layer) {
					entities.get(i).render(renderer, xZoom, yZoom);
				}

				else if (entities.get(i).getLayer() + 1 == layer) {
					Rectangle rect = entities.get(i).getRectangle();

					int tileBelowX = rect.x / xIncrement;
					int tileBelowX2 = (int) Math.floor((rect.x + rect.width / 2 * xZoom * 1.0) / xIncrement);
					int tileBelowX3 = (int) Math.floor((rect.x + rect.width * xZoom * 1.0) / xIncrement);

					int tileBelowY = (int) Math.floor((rect.y + rect.height * yZoom * 1.0) / yIncrement);

					if (getTile(layer, tileBelowX, tileBelowY) == null
							&& getTile(layer, tileBelowX2, tileBelowY) == null
							&& getTile(layer, tileBelowX3, tileBelowY) == null) {
						entities.get(i).render(renderer, xZoom, yZoom);
					}
				}
			}
		}

		for (int i = 0; i < entities.size(); i++)
			if (entities.get(i).getLayer() == Integer.MAX_VALUE)
				entities.get(i).render(renderer, xZoom, yZoom);

	}

	public List<Node> findPath(Vector2i start, Vector2i end) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, end));
		openList.add(current);

		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(end)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4)
					continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xDir = (i % 3) - 1;
				int yDir = (i / 3) - 1;
				MappedTile newTile = getTile(0, x + xDir, y + yDir);
				if (newTile == null)
					continue;
				if (tileSet.isSolid(newTile.id))
					continue;
				Vector2i newVector = new Vector2i(x + xDir, y + yDir);
				double gCost = current.gCost + (getDistance(current.tile, newVector) == 1 ? 1 : 0.95);
				double hCost = getDistance(newVector, end);
				Node node = new Node(newVector, current, gCost, hCost);
				if (isVectorInList(closedList, newVector) && gCost >= node.gCost)
					continue;
				if (!isVectorInList(openList, newVector) || gCost < node.gCost) {
					openList.add(node);
				}
			}
		}
		closedList.clear();
		return null;
	}

	private boolean isVectorInList(List<Node> list, Vector2i v) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).tile.equals(v)) {
				return true;
			}
		}
		return false;
	}

	private double getDistance(Vector2i start, Vector2i end) {
		double dx = start.getX() - end.getX();
		double dy = start.getY() - end.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	public List<Entity> getNearbyEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		Rectangle rect = e.getRectangle();
		for (int i = 0; i < Game.getEntities().size(); i++) {
			GameObject o = Game.getEntities().get(i);
			if (o instanceof GUIComponent || o instanceof GUIPanel || o instanceof Particle || o instanceof Projectile)
				continue;
			Entity entity = (Entity) Game.getEntities().get(i);
			if (entity == e)
				continue;
			Rectangle entityRect = entity.getRectangle();

			int distanceX = entityRect.x - rect.x;
			int distanceY = entityRect.y - rect.y;
			double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));

			if (distance <= radius) {
				result.add(entity);
			}
		}
		return result;
	}

	public List<Player> getNearbyPlayers(Entity e, int radius) {
		List<Entity> entities = getNearbyEntities(e, radius);
		List<Player> result = new ArrayList<Player>();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Player && entities.get(i) != e) {
				result.add((Player) entities.get(i));
			}
		}
		return result;
	}

	class MappedTile {
		public int layer, id, x, y;
		public boolean solid;

		public MappedTile(int layer, int id, int x, int y) {
			this.layer = layer;
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}

	@SuppressWarnings("unchecked")
	class GridBlock {
		public ArrayList<MappedTile>[] mappedTilesByLayer;

		public GridBlock() {
			mappedTilesByLayer = new ArrayList[numLayers];
			for (int i = 0; i < mappedTilesByLayer.length; i++)
				mappedTilesByLayer[i] = new ArrayList<MappedTile>();
		}

		public void render(Render renderer, int layer, int tileWidth, int tileHeight, int xZoom, int yZoom) {
			if (mappedTilesByLayer.length > layer) {
				ArrayList<MappedTile> mappedTiles = mappedTilesByLayer[layer];
				for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
					MappedTile mappedTile = mappedTiles.get(tileIndex);
					tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight,
							xZoom, yZoom);
				}
			}
		}

		public void addTile(MappedTile tile) {
			if (mappedTilesByLayer.length <= tile.layer) {
				ArrayList<MappedTile>[] newTilesByLayer = new ArrayList[tile.layer + 1];

				int i = 0;
				for (i = 0; i < mappedTilesByLayer.length; i++) {
					newTilesByLayer[i] = mappedTilesByLayer[i];
				}
				for (; i < newTilesByLayer.length; i++) {
					newTilesByLayer[i] = new ArrayList<MappedTile>();
				}
				mappedTilesByLayer = newTilesByLayer;
			}
			mappedTilesByLayer[tile.layer].add(tile);
		}

		public void removeTile(MappedTile tile) {
			mappedTilesByLayer[tile.layer].remove(tile);
		}

		public MappedTile getTile(int layer, int tileX, int tileY) {
			for (MappedTile tile : mappedTilesByLayer[layer]) {
				if (tile.x == tileX && tile.y == tileY) {
					return tile;
				}
			}
			return null;
		}
	}
}
