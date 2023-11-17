package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

//	private Point2D position;
//	private String name;
//	private int layer;

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);
//		this.position=position;

	}

	public static Movable createMovable(char key, Point2D position) {

		switch (key) {
		case 'C':
			return new Caixote(position);
		case 'E':
			return new Empilhadora(position);
		case 'P':
			return new Palete(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating Movable element");
		}
	}

	public void updatePosition(GameElement gameElement, Point2D newPosition) {
		List<GameElement> map = gameEngine.getMap();
//		List<GameElement> updatedMap = new ArrayList<>();

		Point2D position = gameElement.getPosition();
		String name = gameElement.getName();

		for (GameElement actual : map) {
			if (actual.getPosition().equals(position) && actual.getName().equals(name)) {
				actual.setPosition(newPosition);
				break;
			}
		}
		gameEngine.setMap(map);
	}

	public void move(Direction direction) {
		System.out.println("Last position:" + this);
		Point2D newPosition = calculateFinalPosition(super.getPosition(), direction);
		if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10
		/* && isValidMove(super.getPosition(), direction) */) {
			// position = newPosition;
			System.out.println("New Position:" + newPosition);
			// Atualizar o map do gameEngine
			updatePosition(this, newPosition);
			// Atualizar a posição
			super.setPosition(newPosition);
			// Verificar se realmente atualizou
			System.out.println("Verificação" + this);
			System.out.println("Estou a andar!");
			System.out.println(gameEngine.getMap());
			System.out.println(gameEngine.getTile());
			System.out.println();
		}
	}

	public boolean isValidMove(Point2D initialPosition, Direction direction) {
		// TODO Auto-generated method stub
		// Verifica se existe algum obstáculo na direção que pretende que seja uma
		// parede ou então dois caixotes seguidos
		Point2D nextPosition = calculateFinalPosition(initialPosition, direction);

		// Verifica se existe uma parede na posição seguinte
		if (containsGameElement(nextPosition, "Parede")) {
			return false;
		}

		// Caso a nextPosition seja um caixote também é necessário calcular se a posição
		// seguinte a esse caixote é
		// também um caixote. Se for, então não é permitido o movimento
		// ArraYlist que guarda o ArrayList da posição a seguir a nextPosition
//		List<GameElement> nextPosition=elementsInPosition(finalPosition);
//		List<GameElement> afterNextPosition = elementsInPosition(calculateFinalPosition(finalPosition, direction));
		if (containsGameElement(nextPosition, "Caixote")
				&& containsGameElement(calculateFinalPosition(nextPosition, direction), "Caixote")
				|| containsGameElement(nextPosition, "Caixote")
						&& containsGameElement(calculateFinalPosition(nextPosition, direction), "Caixote")) {
			return false;
		}

		return true;

	}
	
	
	// Calcula a posição final de um movimento

	public boolean existsInList(String className, List<ImageTile> list) {
		for (ImageTile atual : list) {
			if (atual.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}

	public List<GameElement> getElementsInPosition(Point2D position) {
		List<GameElement> result = new ArrayList<>();
		List<GameElement> map = gameEngine.getMap();

		for (GameElement actual : map) {
			if (actual.getPosition().equals(position)) {
				result.add(actual);
			}
		}
		return result;
	}

	public boolean containsGameElement(Point2D position, String gameElementName) {
		List<GameElement> elements = getElementsInPosition(position);

		for (GameElement actual : elements) {
			if (actual.equals(gameElementName)) {
				return true;
			}
		}
		return false;
	}
	
	

	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

}
