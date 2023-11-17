package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	protected GameEngine gameEngine = GameEngine.getInstance();
	protected ImageMatrixGUI gui;

	private Point2D position;
	private String name;
	private int layer;

	public GameElement(Point2D position, String name, int layer) {
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

	// Criar um objeto filho de GameELement
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
		case '%':
			return new Parede(position);
		case 'T':
			return new Teleporte(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating GameElement element");
		}
	}

	@Override
	public String toString() {
		return "(" + getPosition() + "):" + getName();
	}

}
