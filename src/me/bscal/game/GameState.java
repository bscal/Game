package me.bscal.game;

public class GameState {

	public enum State {
		MAIN_MENU, SETTINGS, GAME, NONE
	}
	
	private State state = State.MAIN_MENU;
	
	public GameState() {}
	
	public GameState(State state) {
		this.state = state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
}
