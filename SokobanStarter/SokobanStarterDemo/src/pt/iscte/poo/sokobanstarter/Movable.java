package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);

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

	public static Movable createMovable(String name, Point2D position) {

		switch (name) {
		case Caixote.imageName:
			return new Caixote(position);
		case Empilhadora.initialImageName:
			return new Empilhadora(position);
		case Palete.imageName:
			return new Palete(position);
		default:
			throw new IllegalArgumentException("Unrecognized name " + "name" + " for creating Movable element");
		}
	}

	public void move(Direction direction) {
		System.out.println("Last position:" + this);

		Point2D newPosition = super.getPosition().plus(direction.asVector());

		// Verifica através da função isValidMove se o objeto se pode mover
		if (isValidMove(super.getPosition(), direction)) {

			// Calcular a nova posição

			System.out.println("New Position:" + newPosition);
			// Atualizar o map do gameEngine
			super.gameEngine.gameMap.updateElementPosition(this, newPosition);

			// Verificar se realmente atualizou
//			System.out.println("Verificação:" + this);
//			System.out.println("Mexer!");
//			System.out.println(gameEngine.gameMap.toString());
//			System.out.println(gameEngine.getTile());
//			System.out.println();
		} else {
			System.err.println("It was not possible to move  " + getName() + " to position" + newPosition);
		}
	}

	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
		// Verifica se existe algum obstáculo na direção que pretende que seja uma
		// parede ou então dois caixotes seguidos
		Point2D nextPosition = calculateFinalPosition(initialPosition, direction);
		
		boolean isValidMove=false;
		
		// Obtém todos os gameElements que existem no nextPosition
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);


		
		for(GameElement atual : elements) {
			if(atual.getTransposable() && isOnBoard(nextPosition)) {
				isValidMove=true;
			}
		}
		System.out.println("isValidMove:"+isValidMove);


		return isValidMove;

	}
	public boolean isOnBoard(Point2D nextPosition) {
		if (nextPosition.getX() < 0 && nextPosition.getX() >= 10 && nextPosition.getY() < 0
				&& nextPosition.getY() >= 10) {
			return false;
		}
		return true;
	}
	

	// Calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

	// Verifica se existe algum elemento numa certa posição, chamando a função do
	// GameMap
	public boolean checkElementAtPosition(Point2D position, String imageName) {
		return super.gameEngine.gameMap.existsOnPosition(position, imageName);
	}

}
