package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
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

	// Para os objetos Movable de modo a atualizar a sua posição no mapa
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
	
	public void removeElement() {
//		System.out.println("GameElement to remove:"+this);
		gameEngine.gameMap.removeElement(this);
//		System.out.println("GameMap after remove:");
//		System.out.println(gameEngine.gameMap);
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
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating GameElement element");
		}
	}

	// Criação de Objetos através do seu nome
//	public static GameElement createElement(GameElement gameElement) {
//
//		switch (gameElement.getName()) {
//		case "Parede":
//			return new Parede(gameElement.getPosition());
//		case "Chao":
//			return new Chao(gameElement.getPosition());
//		case "Vazio":
//			return new Vazio(gameElement.getPosition());
//		case "Caixote":
//			return new Caixote(gameElement.getPosition());
//		case "Alvo":
//			return new Alvo(gameElement.getPosition());
//		case "Empilhadora_R":
//			return new Empilhadora(gameElement.getPosition());
//		case "Empilhadora_L":
//			return new Empilhadora(gameElement.getPosition());
//		case "Empilhadora_U":
//			return new Empilhadora(gameElement.getPosition());
//		case "Empilhadora_D":
//			return new Empilhadora(gameElement.getPosition());
//		case "Bateria":
//			return new Bateria(gameElement.getPosition());
//		case "Buraco":
//			return new Buraco(gameElement.getPosition());
//		case "Palete":
//			return new Palete(gameElement.getPosition());
//		case "ParedeRachada":
//			return new ParedeRachada(gameElement.getPosition());
//		case "Teleporte":
//			return new Teleporte(gameElement.getPosition());
//		default:
//			throw new IllegalArgumentException("Unrecognized name " + gameElement.getName() + " for creating GameElement element");
//		}
//	}
	
	

	@Override
	public String toString() {
		return getPosition() + ":" + getName()+ ", isTransposable:"+getTransposable();
	}

	

}
