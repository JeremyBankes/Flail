package com.jeremy.flail.state;

import java.util.HashMap;

import com.jeremy.flail.graphics.Renderer;

public class StateManager {

	private static StateManager instance;

	private State currentState;

	private final HashMap<Class<? extends State>, State> states = new HashMap<>();

	public void register(State state) {
		states.put(state.getClass(), state);
	}

	public <T extends State> T getState(Class<T> stateClass) {
		return stateClass.cast(states.get(stateClass));
	}

	public <T extends State> T enter(Class<T> stateClass) {
		if (hasCurrentState()) {
			currentState.exit();
		}
		currentState = getState(stateClass);
		currentState.enter();
		return stateClass.cast(currentState);
	}

	public boolean hasCurrentState() {
		return currentState != null;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public void tick() {
		if (hasCurrentState()) {
			getCurrentState().tick();
		}
	}

	public void render(Renderer renderer) {
		if (hasCurrentState()) {
			getCurrentState().render(renderer);
		}
	}

	public static StateManager getInstance() {
		return instance == null ? instance = new StateManager() : instance;
	}

}
