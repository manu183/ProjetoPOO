package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}

	public static boolean isMovable(GameElement gameElement) {
		return gameElement instanceof Movable;
	}

	protected boolean isValidMove(Point2D finalPosition) {
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(finalPosition);
		System.out.println("THIS:"+this);
		for (GameElement current : elements) {
			if (!current.getTransposable() || !isOnBoard(finalPosition) || super.getTransposable()) {
				System.out.println("isValidMove:false");
				return false;
			}
		}
		System.out.println("isValidMove:true");
		return true;
	}

	protected void move(Direction direction) {
		// Calcular a nova posição
		Point2D nextPosition = calculateFinalPosition(getPosition(), direction);

		System.out.println("Movimento válido");
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);
		System.out.println(elements);
		GameElement next = null;
		for (GameElement actual : elements) {
			System.out.println("a");
			if (actual instanceof Interectable) {
				next = actual;
				break;
			}
		}
		if (next != null) {
			if (next instanceof Interectable) {
				System.out.println("Interectable detected!!!!!");
				((Interectable) next).interact(this);
				
			}

		}else if(isValidMove(nextPosition)) {
			System.out.println("Mover");
			super.gameEngine.gameMap.updateElementPosition(this, nextPosition);
		}

	}

	public boolean isOnBoard(Point2D nextPosition) {
		if (nextPosition.getX() >= 0 && nextPosition.getX() < 10 && nextPosition.getY() >= 0
				&& nextPosition.getY() < 10) {
			return true;
		}
		return false;
	}

	// Calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

}