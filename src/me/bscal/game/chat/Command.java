package me.bscal.game.chat;

import me.bscal.game.entity.Entity;

public class Command {

	private String[] args;
	private String cmd;
	private Entity sender;
	
	public Command(String msg, Entity e) {
		String[] split = msg.split(" ");
		this.cmd = split[0].replace("/", "");
		this.args = new String[split.length - 1];
		for(int i = 1; i < split.length; i++) {
			args[i - 1] = split[i];
		}
	}
	
	public Entity getSender() {
		return sender;
	}
	
	public String[] getArguments() {
		return args;
	}
	
	public String getName() {
		return cmd;
	}
	
}
