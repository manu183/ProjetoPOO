package pt.iscte.poo.sokobanstarter;

import java.util.Objects;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	protected GameEngine gameEngine = GameEngine.getInstance(); // Instância do GameEngine
	protected ImageMatrixGUI gui = ImageMatrixGUI.getInstance(); // Instância da GUI

	private Point2D position;
	private String name;
	private int layer;

	private boolean isTransposable = false; // atributo boleano que guarda se um objeto GameElement é ou não
											// transposível

	protected GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;

	}

	// Método para obter o nome
	@Override
	public String getName() {
		return name;
	}

	// Método para definir o nome
	protected void setName(String newName) {
		this.name = newName;
	}

	// Método para obter a posição
	@Override
	public Point2D getPosition() {
		return position;
	}

	// Método para definir a posição
	protected void setPosition(Point2D newPosition) {
		this.position = newPosition;
	}

	// Método para obter a camada
	@Override
	public int getLayer() {
		return layer;
	}

	// Método para definir se é transposível ou não
	public void setTransposable(boolean isTransposable) {
		this.isTransposable = isTransposable;
	}

	// Método para obter se é transposível
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
			return new RoletaEnergia(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating GameElement element");
		}
	}

	@Override
	public String toString() {
		return getPosition() + ":" + getName() + ", isTransposable:" + getTransposable();
	}

	// Saber se um certo objeto é igual a este
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