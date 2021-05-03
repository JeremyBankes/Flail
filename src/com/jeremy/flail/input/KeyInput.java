package com.jeremy.flail.input;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class KeyInput implements KeyListener {

	public int doubleTapTime = 300;

	public enum KeyInputEventType {

		PRESS, DOUBLE_PRESS, RELEASE, TYPE;

	}

	public interface KeyInputEventCallback {

		public abstract void keyInput(KeyInputEventType type, int code, char character, int modifiers, boolean consumed);

	}

	private final HashSet<KeyInputEventCallback> callbacks = new HashSet<KeyInputEventCallback>();
	private final HashSet<Integer> pressed = new HashSet<Integer>();
	private final HashSet<Integer> justPressed = new HashSet<Integer>();
	private KeyEvent lastReleaseEvent;

	public void attach(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	public boolean isPressed(int button) {
		return pressed.contains(button);
	}

	public boolean isJustPressed(int button) {
		return justPressed.contains(button);
	}

	public boolean add(KeyInputEventCallback callback) {
		return callbacks.add(callback);
	}

	public boolean remove(KeyInputEventCallback callback) {
		return callbacks.remove(callback);
	}

	public void tick() {
		justPressed.clear();
	}

	@Override
	public void keyTyped(KeyEvent event) {
		callbacks.forEach(callback -> {
			callback.keyInput(KeyInputEventType.TYPE, event.getKeyChar(), event.getKeyChar(), event.getModifiersEx(), false);
		});
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (lastReleaseEvent != null && lastReleaseEvent.getKeyCode() == event.getKeyCode()
				&& event.getWhen() - lastReleaseEvent.getWhen() < doubleTapTime) {
			callbacks.forEach(callback -> {
				callback.keyInput(KeyInputEventType.DOUBLE_PRESS, event.getKeyCode(), event.getKeyChar(), event.getModifiersEx(), false);
			});
		}
		callbacks.forEach(callback -> {
			callback.keyInput(KeyInputEventType.PRESS, event.getKeyCode(), event.getKeyChar(), event.getModifiersEx(), false);
		});
		if (!isPressed(event.getKeyCode()))
			justPressed.add(event.getKeyCode());
		pressed.add(event.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent event) {
		callbacks.forEach(callback -> {
			callback.keyInput(KeyInputEventType.RELEASE, event.getKeyCode(), event.getKeyChar(), event.getModifiersEx(), false);
		});
		pressed.remove(event.getKeyCode());
		lastReleaseEvent = event;
	}

}
