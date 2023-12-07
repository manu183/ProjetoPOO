package pt.iscte.poo.sokobanstarter;

import java.util.Objects;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	protected GameEngine gameEngine = GameEngine.getInstance();
	protected ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

	private Point2D position;
	private String name;
	private int layer;

	private boolean isTransposable = false;

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

	// Para os objetos Movable de modo a atualizar a sua posição no mapa
	protected void setPosition(Point2D newPosition) {
		this.position = newPosition;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setTransposable(boolean isTransposable) {
		this.isTransposable = isTransposable;
	}

	public boolean getTransposable() {
		return isTransposable;
	}

	// Remover-se a ele próprio do tabuleiro de jogo
	public void removeElement() {
		gameEngine.gameMap.removeElement(this);
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
			return new Caixote(position);
		case 'X':
			return new Alvo(position);
		case 'E':
			return new Empilhadora(position);
		case 'B':
			return new Bateria(position);
		case 'O':
			return new Buraco(position);
		case 'P':
			return new Palete(position);
		case 'M':
			return new Martelo(position);
		case '%':
			return new ParedeRachada(position);
		case 'T':
			return new Teleporte(position);
		case 'S':
			return new Sorteio(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating GameElement element");
		}
	}

	@Override
	public String toString() {
		return getPosition() + ":" + getName() + ", isTransposable:" + getTransposable();
	}
	
	
	//Saber se os um GameElement é igual a este
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		GameElement other = (GameElement) obj;
		return layer == other.layer && isTransposable == other.isTransposable
				&& Objects.equals(position, other.position) && Objects.equals(name, other.name);
	}

}