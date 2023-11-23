package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	protected GameEngine gameEngine = GameEngine.getInstance();
	protected ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

	private Point2D position;
	private String name;
	private int layer;
	
	private boolean isTransposable=false;

	protected GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;

	}


	@Override
	public String getName() {
		return name;
	}

	protected void setName(String newName) {
		this.name = newName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	// Para os objetos Movable
	protected void setPosition(Point2D newPosition) {
		this.position = newPosition;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
	
	public void setTransposable(boolean isTransposable) {
		this.isTransposable=isTransposable;
	}
	
	public boolean getTransposable() {
		return isTransposable;
	}
	
	// Criação de Objetos através da char que o identifica
	public static GameElement createElement(char key, Point2D position) {

		switch (key) {
		case '#':
			return new Parede(position);
		case ' ':
			return new Chao(position);
		case '=':
			return new Vazio(position);
		case 'C':
			return Movable.createMovable(key, position);
		case 'X':
			return new Alvo(position);
		case 'E':
			return Movable.createMovable(key, position);
		case 'B':
			return new Bateria(position);
		case 'O':
			return new Buraco(position);
		case 'P':
			return Movable.createMovable(key, position);
		case 'M':
			return new Martelo(position);
		case '%':
			return new ParedeRachada(position);
		case 'T':
			return new Teleporte(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating GameElement element");
		}
	}

	// Criação de Objetos através do seu nome
	public static GameElement createElement(String name, Point2D position) {

		switch (name) {
		case "Parede":
			return new Parede(position);
		case "Chao":
			return new Chao(position);
		case "Vazio":
			return new Vazio(position);
		case "Caixote":
			return Movable.createMovable(name, position);
		case "Alvo":
			return new Alvo(position);
		case "Empilhadora_R":
			return Movable.createMovable(name, position);
		case "Bateria":
			return new Bateria(position);
		case "Buraco":
			return new Buraco(position);
		case "Palete":
			return Movable.createMovable(name, position);
		case "ParedeRachada":
			return new ParedeRachada(position);
		case "Teleporte":
			return new Teleporte(position);
		default:
			throw new IllegalArgumentException("Unrecognized name " + name + " for creating GameElement element");
		}
	}
	
	public void removeElement() {
		gameEngine.gameMap.removeElement(this);
	}
	

	@Override
	public String toString() {
		return getPosition() + ":" + getName();
	}

}
