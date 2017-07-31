package me.bscal.game.mapping;

import me.bscal.game.util.Vector2i;

public class Node {

	public Vector2i tile;
	protected Node parent;
	protected double fCost, gCost, hCost;
	
	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
}
