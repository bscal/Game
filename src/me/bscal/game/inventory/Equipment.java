package me.bscal.game.inventory;

public class Equipment extends Inventory{

	private final static int EQUIPMENT_SIZE = 11;
	
	public final static int CURSOR_SLOT 	= 0;
	public final static int MAIN_HAND 		= 1;
	public final static int OFF_HAND 		= 2;
	public final static int HELMET			= 3;
	public final static int CHEST			= 4;
	public final static int LEGGINGS		= 5;
	public final static int GLOVES 			= 6;
	public final static int BOOTS 			= 7;
	public final static int RING_SLOT_ONE 	= 8;
	public final static int RING_SLOT_TWO 	= 9;
	public final static int NECKLACE 		= 10;
	
	public Equipment() {
		super(EQUIPMENT_SIZE);
	}
	
}
