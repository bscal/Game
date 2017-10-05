package me.bscal.game.inventory;

public class Inventory {

	protected final static int DEFAULT_SIZE = 16;
	protected final static int ALL = -1;

	public Item[] items;

	public Inventory() {
		this(DEFAULT_SIZE);
	}

	public Inventory(int size) {
		this.items = new Item[size];
	}

	public void resizeInventory(int size) {
		Item[] newItems = new Item[size];
		for (int i = 0; i < items.length; i++) {
			newItems[i] = items[i];
		}
		items = newItems;
	}

	public void addItem(Item item) {
		for (int i = 0; i < items.length; i++) {
			Item currentItem = items[i];
			if (currentItem.type != 0 || currentItem.type != item.type) {
				continue;
			}
			if (currentItem.maxStackSize >= currentItem.amount + item.amount) {
				items[i] = item;
				return;
			}
		}
	}

	public boolean removeItem(int type, int amount) {
		for (int i = 0; i < items.length; i++) {
			Item currentItem = items[i];
			if (currentItem.type == type) {
				if (amount == ALL) {
					currentItem.amount = 0;
					return true;
				} else if (currentItem.amount <= amount) {
					return false;
				} else {
					currentItem.amount -= amount;
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsItem(int type) {
		return containsItem(type, 1);
	}

	public boolean containsItem(int type, int amount) {
		for (int i = 0; i < items.length; i++) {
			Item currentItem = items[i];
			if (currentItem.type == type && currentItem.amount == amount) {
				return true;
			}
		}
		return false;
	}

	public Item[] getItems() {
		return items;
	}

	public Item getItem(int slot) {
		return items[slot];
	}

}
