package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;

public class GameManager {
	private int level;
	private String userName;
	
	
	//Esta classe serve para controlar sobre tudo o que seja aumentar nível,
	//guardar um ficheiro com as melhores pontuações, etc
	
	private static GameManager INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameManager(int level, String userName) {
		this.level=level;
		this.userName=userName;
		this.gui=ImageMatrixGUI.getInstance();
		start_game();
	}

	// Implementacao do singleton para o GameEngine
	public static GameManager getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameManager(0,"");
		return INSTANCE;
	}
	
	public void start_game() {
		drawGUI();
	}
	
	public void drawGUI() {
		gui.setSize(40, 40);
		Scanner scanner = new Scanner(System.in);
		gui.setMessage("Set you username:");
		String userName = scanner.nextLine();
		scanner.close();
		gui.registerObserver(null);
		gui.go();
	}
	
	public void level_up() {
		level++;
	}


	

}
