package io;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import util.UpdateContext;

public class InputHandler {

	static class KeyState {

		private final int keyCode;

		private long lastPressed;
		private long lastReleased;

		public KeyState(int keyCode) {
			this.keyCode = keyCode;
		}
	}

	private long currentFrameTime;
	private long lastFrameTime;

	private final KeyState[] keyStates = new KeyState[0xFF];

	public InputHandler() {

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {

			if (ke.getID() == KeyEvent.KEY_PRESSED || ke.getID() == KeyEvent.KEY_RELEASED) {

				long when = ke.getWhen();
				int keyCode = ke.getKeyCode();

				if (keyStates[keyCode] == null) {
					keyStates[keyCode] = new KeyState(keyCode);
				}

				if (ke.getID() == KeyEvent.KEY_PRESSED) {
					if (!isKey(keyCode)) {
						keyStates[keyCode].lastPressed = when;
					}
				} else {
					if (isKey(keyCode)) {
						keyStates[keyCode].lastReleased = when;
					}
				}
			}

			return false;
		});
	}

	public boolean isKeyReleased(int keyCode) {

		if (keyStates[keyCode] == null) {
			return false;
		}

		return keyStates[keyCode].lastReleased > keyStates[keyCode].lastPressed
				&& keyStates[keyCode].lastReleased >= lastFrameTime
				&& keyStates[keyCode].lastReleased < currentFrameTime;
	}

	public boolean isKeyPressed(int keyCode) {

		if (keyStates[keyCode] == null) {
			return false;
		}

		return keyStates[keyCode].lastPressed > keyStates[keyCode].lastReleased
				&& keyStates[keyCode].lastPressed >= lastFrameTime && keyStates[keyCode].lastPressed < currentFrameTime;
	}

	public boolean isKey(int keyCode) {

		if (keyStates[keyCode] == null) {
			return false;
		}

		return keyStates[keyCode].lastPressed > keyStates[keyCode].lastReleased
				&& keyStates[keyCode].lastPressed < lastFrameTime;
	}

	public void update(UpdateContext context) {
		this.currentFrameTime = context.time;
		this.lastFrameTime = context.lastFrameTime;
	}
}
