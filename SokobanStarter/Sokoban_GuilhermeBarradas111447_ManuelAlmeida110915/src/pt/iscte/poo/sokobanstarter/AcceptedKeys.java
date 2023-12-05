package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

public enum AcceptedKeys {
    SPACE(KeyEvent.VK_SPACE),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT);

	private final int keyCode;

	private AcceptedKeys(int keyCode) {
		this.keyCode = keyCode;
	}

	private int getKeyCode() {
		return keyCode;
	}

	// Método para verificar se uma tecla é aceita
	public static boolean isAcceptedKey(int key) {
		for (AcceptedKeys acceptedKey : values()) {
			if (acceptedKey.getKeyCode() == key) {
				return true;
			}
		}
		return false;
	}
}
