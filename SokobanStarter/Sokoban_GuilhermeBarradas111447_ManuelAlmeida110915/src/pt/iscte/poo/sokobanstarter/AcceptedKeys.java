package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;

//Representa as teclas que sao aceites no jogo
public enum AcceptedKeys {
    SPACE(KeyEvent.VK_SPACE),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT),
    ESC(KeyEvent.VK_ESCAPE);

	private final int keyCode;

	private AcceptedKeys(int keyCode) {
		this.keyCode = keyCode;
	}

	private int getKeyCode() {
		return keyCode;
	}

	// Metodo para verificar se uma tecla e aceite
	public static boolean isAcceptedKey(int key) {
		for (AcceptedKeys acceptedKey : values()) {
			if (acceptedKey.getKeyCode() == key) {
				return true;
			}
		}
		return false;
	}
}
